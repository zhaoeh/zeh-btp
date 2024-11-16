package zeh.btp.validation.annotation;

import zeh.btp.validation.validator.StringValuesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @description: 枚举值校验注解
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StringValuesValidator.class})
@Documented
public @interface StringValuesValid {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values() default {};

    String message() default "必须为指定值";
}
