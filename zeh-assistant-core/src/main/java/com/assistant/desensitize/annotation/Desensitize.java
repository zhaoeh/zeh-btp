package com.assistant.desensitize.annotation;

import com.assistant.desensitize.core.DesensitizeJsonSerializer;
import com.assistant.desensitize.core.strategy.DesensitizeStrategy;
import com.assistant.desensitize.core.strategy.impl.DefaultStrategy;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeJsonSerializer.class)
public @interface Desensitize {

    Class<? extends DesensitizeStrategy> strategy() default DefaultStrategy.class;
}
