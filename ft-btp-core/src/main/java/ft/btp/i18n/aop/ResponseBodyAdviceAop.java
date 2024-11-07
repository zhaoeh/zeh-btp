package ft.btp.i18n.aop;

import ft.btp.i18n.annotation.EnableI18nWeavingForResponse;
import ft.btp.i18n.api.DefaultI18nCodeMapper;
import ft.btp.i18n.processor.I18nProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.MethodParameter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @description: ResponseBodyAdvice拦截器
 * @author: ErHu.Zhao
 * @create: 2024-08-07
 **/
@Aspect
@Slf4j
public class ResponseBodyAdviceAop {

    private final I18nProcessor i18nProcessor;

    public ResponseBodyAdviceAop(I18nProcessor i18nProcessor) {
        this.i18nProcessor = i18nProcessor;
    }

    /**
     * aop拦截容器中所有 ResponseBodyAdvice 的  beforeBodyWrite 方法
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("target(org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodParameter methodParameter;
        Object responseBody;
        boolean needProcess = ResponseBodyAdvice.class.isAssignableFrom(joinPoint.getTarget().getClass()) &&
                joinPoint.getSignature().getName().equals("beforeBodyWrite");
        if (needProcess) {
            responseBody = joinPoint.getArgs()[0];
            methodParameter = (MethodParameter) joinPoint.getArgs()[1];
            if (Objects.nonNull(i18nProcessor) && Objects.nonNull(responseBody)) {
                EnableI18nWeavingForResponse weaving = methodParameter.getMethod().getAnnotation(EnableI18nWeavingForResponse.class);
                i18nProcessor.processI18nResult(responseBody, weaving);
                i18nProcessor.processI18nField(responseBody, Objects.isNull(weaving) ? DefaultI18nCodeMapper.class :
                        weaving.mapperConfigure());
            }
        }

        return joinPoint.proceed();
    }
}
