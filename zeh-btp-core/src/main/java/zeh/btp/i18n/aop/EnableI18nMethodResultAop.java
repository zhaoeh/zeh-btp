package zeh.btp.i18n.aop;

import zeh.btp.i18n.annotation.EnableI18nFunctionForMethod;
import zeh.btp.i18n.processor.I18nProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Objects;

/**
 * @description: EnableI18nMethodResult注解拦截器
 * @author: ErHu.Zhao
 * @create: 2024-08-07
 **/
@Aspect
@Slf4j
public class EnableI18nMethodResultAop {

    private final I18nProcessor i18nProcessor;

    public EnableI18nMethodResultAop(I18nProcessor i18nProcessor) {
        this.i18nProcessor = i18nProcessor;
    }

    @Pointcut("@annotation(method)")
    public void methodResult(EnableI18nFunctionForMethod method) {
    }

    /**
     * aop拦截容器中所有 ResponseBodyAdvice 的  beforeBodyWrite 方法
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("methodResult(method)")
    public Object around(ProceedingJoinPoint joinPoint, EnableI18nFunctionForMethod method) throws Throwable {
        Object result = joinPoint.proceed();
        if (Objects.nonNull(i18nProcessor) && Objects.nonNull(result)) {
            i18nProcessor.processI18nField(result, method.mapperConfigure());
        }
        return result;
    }
}
