package zeh.btp.simple;

import zeh.btp.simple.api.PriorityLimitConfigurer;

import java.lang.annotation.*;

/**
 * @description: api流量限制注解
 * @author: Erhu.Zhao
 * @create: 2024-08-19
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequestLimit {

    /**
     * 限制时间 单位：秒 （默认值一分钟）
     *
     * @return
     */
    long period() default 60;

    /**
     * 允许请求的次数（默认5次）
     *
     * @return
     */
    long count() default 5;

    /**
     * 被拦截目标方法第一个参数对象中的那个String类型的字段作为校验key
     *
     * @return
     */
    String keyField() default "";

    /**
     * 目标方法触发流量拦截后的拒绝策略
     *
     * @return ACCESS_EXCEPTION：目标方法直接抛出异常 RETURN_NULL：目标方法直接返回NULL
     */
    HandlerPolicy handlerPolicy() default HandlerPolicy.ACCESS_EXCEPTION;

    enum HandlerPolicy {
        REFUSE_RUN, ACCESS_EXCEPTION;
    }

    /**
     * 限流配置器
     *
     * @return 限流配置类
     */
    Class<? extends PriorityLimitConfigurer> limitConfigurer() default PriorityLimitConfigurer.class;
}
