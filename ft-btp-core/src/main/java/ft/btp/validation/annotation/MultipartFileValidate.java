package ft.btp.validation.annotation;

import ft.btp.validation.validator.MultipartFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @description: MultipartFileValidate
 * @author: ErHu.Zhao
 * @create: 2024-09-25
 **/
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MultipartFileValidator.class})
@Documented
public @interface MultipartFileValidate {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] extensions() default {};

    // 匹配时是否忽略扩展名大小写
    boolean ignoreExtensionsCase() default false;

    // 最大限制，单位 字节
    long maxSize() default 0L;

    String message() default "扩展名必须符合要求";
}
