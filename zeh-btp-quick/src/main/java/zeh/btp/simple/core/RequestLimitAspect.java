package zeh.btp.simple.core;

import zeh.btp.simple.RequestLimit;
import zeh.btp.simple.api.PriorityLimitConfigurer;
import zeh.btp.utils.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description: API限流切面
 * @author: ErHu.Zhao
 * @create: 2024-08-19
 **/
@Aspect
@Slf4j
public class RequestLimitAspect {

    private final RedisTemplate redisTemplate;

    private final List<PriorityLimitConfigurer> priorityLimitConfigurers;

    public RequestLimitAspect(RedisTemplate redisTemplate, List<PriorityLimitConfigurer> priorityLimitConfigurers) {
        this.redisTemplate = redisTemplate;
        this.priorityLimitConfigurers = priorityLimitConfigurers;
    }

    @Pointcut("@annotation(requestLimit)")
    public void limit(RequestLimit requestLimit) {
    }

    @Around("limit(requestLimit)")
    public Object doLimit(ProceedingJoinPoint proceedingJoinPoint, RequestLimit requestLimit) throws Throwable {
        String methodName = obtainTargetMethodName(proceedingJoinPoint);
        String limitKey = null;
        Long limitPeriod = null;
        Long limitCount = null;

        // 获取限流配置器
        PriorityLimitConfigurer limitConfigurer = findPriorityLimitConfigurer(requestLimit.limitConfigurer());
        // 优先使用自定义的配置器去配置限流
        boolean forceLimit = false;
        if (Objects.nonNull(limitConfigurer)) {
            limitKey = limitConfigurer.limitKeyConfigurer(proceedingJoinPoint.getTarget());
            limitPeriod = limitConfigurer.limitPeriodConfigurer(proceedingJoinPoint.getTarget());
            limitCount = limitConfigurer.limitCountConfigurer(proceedingJoinPoint.getTarget());
            forceLimit = limitConfigurer.forceLimit();
            if (forceLimit) {
                log.info("forceLimit is {},禁止访问Method: {}", forceLimit, methodName);
                return null;
            }
        }
        limitKey = StringUtils.isNotBlank(limitKey) ? limitKey : generateKey(proceedingJoinPoint, requestLimit, methodName);
        limitPeriod = Objects.nonNull(limitPeriod) && limitPeriod > 0L ? limitPeriod : requestLimit.period();
        limitCount = Objects.nonNull(limitCount) && limitCount > 0L ? limitCount : requestLimit.count();
        log.info("PriorityLimitConfigurer exists,forceLimit is {},limitKey is {},limitPeriod is {},limitCount is {}", forceLimit, limitKey, limitPeriod, limitCount);
        boolean ifTriggerLimit = ifTriggerLimit(limitKey, limitPeriod, limitCount);
        if (ifTriggerLimit) {
            RequestLimit.HandlerPolicy handlerPolicy = requestLimit.handlerPolicy();
            log.error("Method 拦截：{} 拦截key是： {} 请求超过限制频率 [{}次/{}秒]", methodName, limitKey, limitCount, limitPeriod);
            if (RequestLimit.HandlerPolicy.ACCESS_EXCEPTION.equals(handlerPolicy)) {
                throw new IllegalAccessException("请求超过限制，已被限制访问");
            }
            if (RequestLimit.HandlerPolicy.REFUSE_RUN.equals(handlerPolicy)) {
                return null;
            }
        }
        cacheLimit(limitKey, limitPeriod);
        return proceedingJoinPoint.proceed();
    }

    /**
     * 根据注解配置生成限流key
     *
     * @param proceedingJoinPoint
     * @param requestLimit
     * @param methodName
     * @return
     */
    private String generateKey(ProceedingJoinPoint proceedingJoinPoint, RequestLimit requestLimit, String methodName) {
        // 否则，使用keyField指定的字段值、拼接当前uri+目标方法名称作为key
        String keyField = requestLimit.keyField();
        // 获取第一个参数作为key
        String key;
        Object[] args = proceedingJoinPoint.getArgs();
        if (StringUtils.isNotBlank(keyField) && Objects.nonNull(args) && args.length > 0) {
            try {
                key = generateKeyFromRequestParam(args[0], keyField, methodName);
            } catch (Exception e) {
                // 如果反射keyField失败，则使用当前url+目标方法名称作为key
                key = generateKeyFromUrlAndMethod(methodName);
            }
        } else {
            // 如果当前注解未指定keyField，或者目标方法不存在参数，则默认使用当前请求的ip+url作为key
            key = generateKeyFromHttpRequest();
        }
        return key;
    }

    /**
     * 获取目标方法名称
     *
     * @param proceedingJoinPoint aop
     * @return 目标方法名称
     */
    private String obtainTargetMethodName(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        return targetMethod.getName();
    }

    /**
     * 是否触发限流操作
     *
     * @param key
     * @param period
     * @param limitCount
     * @return
     */
    private boolean ifTriggerLimit(String key, long period, long limitCount) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        long currentTimeMillis = System.currentTimeMillis();
        zSetOperations.removeRangeByScore(key, 0, currentTimeMillis - period * 1000);
        Long count = zSetOperations.zCard(key);
        return count >= limitCount;
    }

    /**
     * 请求被放行，则缓存key
     *
     * @param key
     * @param period
     */
    private void cacheLimit(String key, long period) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        long currentTimeMillis = System.currentTimeMillis();
        zSetOperations.add(key, currentTimeMillis, currentTimeMillis);
        redisTemplate.expire(key, period, TimeUnit.SECONDS);
    }

    /**
     * 获取当前http请求
     *
     * @return 当前的http请求
     */
    private HttpServletRequest obtainCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 如果注解配置的缓存key无法正常获取，则默认使用ip+uri组合作为缓存key进行限流校验
     *
     * @return 兜底限流key
     */
    private String generateKeyFromHttpRequest() {
        HttpServletRequest request = obtainCurrentRequest();
        String requestIP = IPUtils.getRequestIP(request);
        String path = request.getServletPath();
        return buildKey(path, requestIP);
    }

    /**
     * 根据methodName生成限流缓存key
     *
     * @param methodName
     * @return
     */
    private String generateKeyFromUrlAndMethod(String methodName) {
        return buildKey(buildPrefix(methodName));
    }

    /**
     * 根据请求参数生成限流缓存key
     *
     * @param args0
     * @param keyField
     * @param methodName
     * @return
     */
    private String generateKeyFromRequestParam(Object args0, String keyField, String methodName) {
        String keyFieldValue = findFirstParamWithKeyField(args0, keyField);
        return buildKey(keyFieldValue, buildPrefix(methodName));
    }

    /**
     * 构建缓存前缀key
     *
     * @param methodName
     * @return
     */
    private String buildPrefix(String methodName) {
        HttpServletRequest request = obtainCurrentRequest();
        String prefix = "";
        // 当前请求 business url
        String path = request.getServletPath();
        if (StringUtils.isNotBlank(path)) {
            prefix = path.replace("/", "_");
        }
        if (StringUtils.isNotBlank(methodName)) {
            prefix = prefix + "_" + methodName;
        }
        return prefix;
    }

    /**
     * 根据注解配置参数keyField获取对应的参数值
     *
     * @param args0
     * @param keyField
     * @return
     */
    private String findFirstParamWithKeyField(Object args0, String keyField) {
        Object keyHolder = args0;
        String field = keyField;
        Field stringField = ReflectionUtils.findField(keyHolder.getClass(), field, String.class);
        ReflectionUtils.makeAccessible(stringField);
        String keyFieldValue = (String) ReflectionUtils.getField(stringField, keyHolder);
        return keyFieldValue;
    }

    private String buildKey(String... args) {
        StringBuilder builder = new StringBuilder("Method_Limit_");
        Arrays.stream(args).forEach(e -> {
            builder.append(e);
        });
        return builder.toString();
    }

    /**
     * 查询限流配置器
     *
     * @param clazz
     * @return
     */
    private PriorityLimitConfigurer findPriorityLimitConfigurer(Class<?> clazz) {
        if (Objects.nonNull(clazz) && Objects.nonNull(priorityLimitConfigurers)) {
            return priorityLimitConfigurers.stream().filter(e -> clazz.isInstance(e)).findFirst().orElse(null);
        }
        return null;
    }

}