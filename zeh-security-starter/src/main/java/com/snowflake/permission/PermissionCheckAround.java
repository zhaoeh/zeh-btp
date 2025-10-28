package com.snowflake.permission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.AccessDeniedException;

@Aspect
public class PermissionCheckAround {

    private final ExpressionParser parser = new SpelExpressionParser();

    private final ApplicationContext applicationContext;

    public PermissionCheckAround(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 定义切点，拦截标注了DSWeaver注解的方法*
     *
     * @param pc 注解对象
     */
    @Pointcut("@annotation(pc)")
    public void pcPoint(PermissionCheck pc) {
    }
    @Around("pcPoint(pc)")
    public Object around(ProceedingJoinPoint joinPoint, PermissionCheck pc) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法参数名和值
        String[] paramNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        // 解析表达式
        Expression expression = parser.parseExpression(pc.value());
        Boolean result = expression.getValue(context, Boolean.class);
        if (Boolean.FALSE.equals(result)) {
            throw new AccessDeniedException("越权访问，禁止");
        }
        return joinPoint.proceed();
    }

}
