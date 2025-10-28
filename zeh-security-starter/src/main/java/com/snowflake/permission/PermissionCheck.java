package com.snowflake.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：用于接口级别授权，针对那些不需要认证，但业务层又需要授权的接口（因为security内部默认授权逻辑是必须经过认证）
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {

    /**
     * spel表达式
     *
     * @return
     */
    String value();

}
