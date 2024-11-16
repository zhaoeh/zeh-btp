package zeh.btp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 通用aop拦截逻辑
 * @author: Erhu.Zhao
 * @create: 2023-10-20 11:20
 **/
@Slf4j
@Aspect
public class CommonAspect {
    /**
     * 通用AOP切面对象收集器
     */
    private final Map<String, CommonAspectRunner> runners = new HashMap<>();

    /**
     * 通过ObjectProvider进行目标Bean实例的宽松注入，注入容器中的所有CommonAspectRunner实例，每个CommonAspectRunner表示一个AOP拦截器的逻辑实例，提供两种拦截策略，前置拦截策略和后置拦截策略
     *
     * @param runnerProvider
     */
    public CommonAspect(ObjectProvider<Map<String, CommonAspectRunner>> runnerProvider) {
        // 在容器构建该实例时，即初始化 runners 属性集合，将拦截器实例缓存起来
        Map<String, CommonAspectRunner> runnerMap = runnerProvider.getIfAvailable();
        if (!CollectionUtils.isEmpty(runnerMap)) {
            runnerMap.forEach((k, v) -> runners.put(v.getClass().getName(), v));
        }

    }

    /**
     * 定义切点，拦截标注有 @InterceptDoWant 注解的方法
     */
    @Pointcut(value = "@annotation(zeh.btp.aop.InterceptDoWant)")
    public void doWant() {
    }

    /**
     * 核心拦截逻辑
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("doWant()")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        InterceptDoWant interceptDoWant = methodSignature.getMethod().getAnnotation(InterceptDoWant.class);

        Object obj;
        try {
            obj = doProcess(interceptDoWant, proceedingJoinPoint);
        } catch (Throwable throwable) {
            log.error("an error occurred when process target method[" + proceedingJoinPoint.getSignature().getName() + "] by AOP!");
            throw throwable;
        }
        return obj;
    }


    private Object doProcess(InterceptDoWant interceptDoWant, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Class<?> clazz = interceptDoWant.value();

        CommonAspectType type = interceptDoWant.type();
        CommonAspectRunner runner = runners.get(clazz.getName());
        Object obj = null;
        if (type.equals(CommonAspectType.BEFORE)) {
            obj = doBefore(runner, proceedingJoinPoint);
        }
        if (type.equals(CommonAspectType.AFTER)) {
            obj = doAfter(runner, proceedingJoinPoint);
        }
        return obj;
    }

    private Object doBefore(CommonAspectRunner runner, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = runner.before(BeforeParams.builder().params(proceedingJoinPoint.getArgs()).build());
        return doProceeding(result, proceedingJoinPoint);
    }

    private Object doAfter(CommonAspectRunner runner, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = doProceeding(null, proceedingJoinPoint);
        Object afterResult = runner.after(AfterParams.builder().params(proceedingJoinPoint.getArgs()).returnValue(result).build());
        return Objects.isNull(afterResult) ? result : afterResult;
    }

    private Object doProceeding(Object result, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return Objects.isNull(result) ? proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs()) : proceedingJoinPoint.proceed(new Object[]{result});
    }

}
