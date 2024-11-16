package zeh.btp.validation.annotation.spel;

import zeh.btp.validation.validator.spel.SpELValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @description: SpEL校验器
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SpELValidator.class})
@Documented
public @interface SpELValid {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // SpEL表达式注解集
    SpELExpression[] expressions();

    String message() default "SpEL expression evaluation failed";
}
