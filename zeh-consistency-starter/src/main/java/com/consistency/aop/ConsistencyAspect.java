package com.consistency.aop;

import com.consistency.annotation.EnableConsistency;
import com.consistency.config.ConsistencyProperties;
import com.consistency.config.ConverterConfigure;
import com.consistency.context.ConsistencyContextHolder;
import com.consistency.context.ContextBean;
import com.consistency.hook.ConsistencyConfigure;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Objects;

import static com.consistency.constant.ConsistencyConstant.*;

@Slf4j
@Component
@Order(200)
public class ConsistencyAspect {

    private final ConsistencyProperties consistencyProperties;

    private final RedissonClient redissonClient;

    private final ConsistencyConfigure consistencyConfigure;

    public ConsistencyAspect(ConsistencyProperties consistencyProperties, RedissonClient redissonClient,
                             ObjectProvider<ConsistencyConfigure> consistencyConfigurerObjectProvider) {
        this.consistencyProperties = consistencyProperties;
        this.redissonClient = redissonClient;
        this.consistencyConfigure = consistencyConfigurerObjectProvider.getIfAvailable();
    }

    /**
     * 定义切点
     *
     * @param ec 注解对象
     */
    @Pointcut("@annotation(enableConsistency)")
    public void enableConsistency(EnableConsistency ec) {
    }

    @Around("enableConsistency(ec)")
    public Object around(ProceedingJoinPoint joinPoint, EnableConsistency ec) throws Throwable {
        if (BooleanUtils.isFalse(consistencyProperties.getEnable())) {
            return joinPoint.proceed();
        }
        MessageExt messageExt = convert(joinPoint, ec);
        if (Objects.isNull(messageExt)) {
            // 不符合拦截规则，直接调度原始逻辑
            return joinPoint.proceed();
        }

        String messageBody = new String(messageExt.getBody());
        String topic = messageExt.getTopic();
        String tag = messageExt.getTags();
        ConverterConfigure converterConfigure = obtainConverterConfigure(topic, tag, messageBody);
        if (Objects.isNull(converterConfigure)) {
            // 不存在转换器或者获取转换器报错，不侵入，直接走原始逻辑
            return joinPoint.proceed();
        }

        // 被代理对象的全限定名称
        String targetClassName = joinPoint.getTarget().getClass().getName();

        // 业务侧定义的业务幂等key，由业务侧保障
        String key = converterConfigure.getKey();

        // 业务侧提供的消息体转换后的实例bean
        Object body = converterConfigure.getBody();
        if (StringUtils.isBlank(key) || Objects.isNull(body)) {
            return joinPoint.proceed();
        }

        // 幂等key：topic，flag，消费者全限定名，以及消费端业务key共同组合唯一幂等key
        String idempotenceKey = topic + FLAG + tag + FLAG + targetClassName + key;

//        RLock lock = redissonClient.getLock(idempotenceKey);
//        lock.tryLock();

        RBucket<String> bucket = redissonClient.getBucket(idempotenceKey);
        boolean acquired = bucket.setIfAbsent(IDEMPOTENCE_PROCESSING, Duration.ofSeconds(60));

        if (!acquired) {
            // 未原子设置成功，说明其他消费者正在消费同笔消息，获取当前状态
            String status = bucket.get();
            if (IDEMPOTENCE_DONE.equals(status)) {
                // 已经处理，跳过:空消费，结束
                return null;
            } else {
                // 状态不是DONE，或者不存在（临界区间恰逢失效或者同笔消息消费失败），抛出异常继续让MQ重放消息
                throw new RuntimeException("消息正在被其他消费者处理，稍后MQ将自动重试");
            }
        }

        Object result = null;
        int retryCount = messageExt.getReconsumeTimes();
        String msgId = messageExt.getMsgId();
        log.info("MQ消息拦截, msgId {} 第 {} 次消费,消息体：{}", msgId, retryCount, messageBody);
        try {
            ContextBean contextBean = new ContextBean(key, body, messageExt);
            ConsistencyContextHolder.setContextBean(contextBean, true);
            result = joinPoint.proceed();
            // 业务调度成功后的回调，给业务侧一个回调入口
            consistencyConfigure.doAfterSuccess(messageExt, converterConfigure);

            // 业务真正调度完毕，设置状态为DONE，并延长TTL至 110分钟
            bucket.set(IDEMPOTENCE_DONE, Duration.ofSeconds(60 * 70));
        } catch (Exception e) {
            // 任何异常，要么消息重放，要么终止，不论如何都需要统一删除幂等key
            bucket.delete();
            if (consistencyConfigure.skipThrowAndDoFinallyFail(messageExt, converterConfigure, e)) {
                // 针对业务异常，重试也没用，直接做兜底记录操作转人工处理
                log.info("MQ消息拦截消费失败,跳过异常,直接回调doFinallyFail, msgId {} 第 {} 次消费,消息体：{} error:", msgId, retryCount, messageBody, e);
                consistencyConfigure.doFinallyFail(messageExt, converterConfigure, e);
            } else {
                int maxRetryThreshold = ec.maxRetryThreshold();
                // 真正重试调度的次数，已经超过重试阈值（阈值最大为14），此时评估消息遭受到重大阻塞，因此为了避免MQ发生重试风暴，此处转存消息到数据表存储，消息确认消费即可
                if (retryCount > maxRetryThreshold) {
                    log.info("MQ消息拦截消费失败,超出最大重试阈值，跳过异常,直接回调doFinallyFail, msgId {} 第 {} 次消费,消息体：{} error:", msgId, retryCount, messageBody, e);
                    // todo:设计一张手动重试兜底表，用于转存失败消息，通过定时任务或者人工进行兜底补偿
                    consistencyConfigure.doFinallyFail(messageExt, converterConfigure, e);
                } else {
                    log.info("MQ消息拦截消费失败,抛出异常等待MQ消息重放, msgId {} 第 {} 次消费,消息体：{} error:", msgId, retryCount, messageBody, e);
                    throw e;
                }
            }
        } finally {
            ConsistencyContextHolder.clean();
        }

        return result;
    }

    private MessageExt convert(ProceedingJoinPoint joinPoint, EnableConsistency ec) {
        if (Objects.isNull(consistencyConfigure)) {
            return null;
        }

        // 拦截条件：目标方法返回值void+方法只有一个参数且类型为 MessageExt
        int maxRetryThreshold = ec.maxRetryThreshold();
        if (maxRetryThreshold > 0 && maxRetryThreshold <= 12) {
            // 最大重试阈值必须在0-12之间，即在MQ默认16次重放的范围内，最多只做12次消息重放，共计第12次重放累计延迟耗时将达到65分钟
            Object[] args = joinPoint.getArgs();
            if (args.length == 1) {
                Object firstArg = args[0];
                if (firstArg instanceof MessageExt) {
                    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                    if (void.class.equals(method.getReturnType())) {
                        return (MessageExt) firstArg;
                    }
                }
            }
        }
        return null;
    }

    private ConverterConfigure obtainConverterConfigure(String topic, String tag, String messageBody) {
        try {
            return consistencyConfigure.converter(topic, tag, messageBody);
        } catch (Exception e) {
            log.error("调用 ConsistencyConfigure converter 获取 ConverterConfigure 失败");
            return null;
        }
    }

}
