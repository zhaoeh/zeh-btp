package ft.btp.validation.annotation;

import ft.btp.validation.validator.NotDuplicateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @description: list重复元素校验注解
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotDuplicateValidator.class})
@Documented
public @interface NotDuplicate {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "不允许重复";
}
