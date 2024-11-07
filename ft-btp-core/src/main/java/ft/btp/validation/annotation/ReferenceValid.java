package ft.btp.validation.annotation;

import ft.btp.validation.annotation.show.ReferenceEnums;

import java.lang.annotation.*;

/**
 * @description: 多字段依赖校验
 * @author: ErHu.Zhao
 * @create: 2024-07-02
 **/
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ReferenceValid {

    /**
     * 关联校验范围
     *
     * @return
     */
    ReferenceRange[] ranges() default {};

    /**
     * 关联校验枚举
     *
     * @return
     */
    ReferenceEnums[] enums() default {};

}
