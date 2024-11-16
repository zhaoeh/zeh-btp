package zeh.btp.aop;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 * @description: 一个注解，基于aop执行你想做的拦截器功能
 * @author: Erhu.Zhao
 * @create: 2023-10-20 11:23
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface InterceptDoWant {

    Class<?> value();

    CommonAspectType type() default CommonAspectType.AFTER;
}
