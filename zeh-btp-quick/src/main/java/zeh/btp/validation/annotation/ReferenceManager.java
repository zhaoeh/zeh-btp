package zeh.btp.validation.annotation;

import zeh.btp.validation.validator.ReferenceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @description: 依赖校验管理注解
 * @author: ErHu.Zhao
 * @create: 2024-07-02
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ReferenceValidator.class})
@Documented
public @interface ReferenceManager {

    //默认错误消息
    String message() default "{000000010}";

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};
}
