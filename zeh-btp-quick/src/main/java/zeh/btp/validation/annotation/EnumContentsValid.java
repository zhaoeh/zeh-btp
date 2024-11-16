package zeh.btp.validation.annotation;

import zeh.btp.enums.CustomizedValidationContents;
import zeh.btp.validation.annotation.validate.EnumValid;
import zeh.btp.validation.validator.EnumContentsValidtor;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @ClassName EnumValid
 * @Description 校验入参是否为指定enum的值的注解
 * @Author TJSAustin
 * @Date 2023/5/20 10:00
 * @Version 1.0
 **/
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumContentsValidtor.class})
@Documented
public @interface EnumContentsValid {

    //默认错误消息
    String message() default "必须为指定值";

    //使用指定枚举，1、使用属性命名code。2、枚举上使用QsmSpecifiedEnumValue
    Class<?> enumValue() default Class.class;

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    // 内容枚举
    CustomizedValidationContents contents();

    //指定多个时使用
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        EnumValid[] value();
    }

}
