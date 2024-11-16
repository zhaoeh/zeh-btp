package zeh.btp.validation.annotation.spel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: SpEL表达式描述注解
 * @author: ErHu.Zhao
 * @create: 2024-10-25
 **/
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpELExpression {

    // 子注解，实现一级嵌套，当自注解满足条件时，继续校验当前主注解
    SpELConditionExpression[] children() default {};

    // SpEL表达式，要求返回值必须是Boolean
    String expression();

    String message() default "SpEL expression evaluation failed";
}
