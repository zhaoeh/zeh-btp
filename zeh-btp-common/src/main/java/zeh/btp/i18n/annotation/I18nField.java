package zeh.btp.i18n.annotation;

import java.lang.annotation.*;

/**
 * @description: 标识注解，自动将标记该注解的字段值进行国际化转换，如果找不到国际化消息，则对原始值不做处理
 * @author: ErHu.Zhao
 * @create: 2024-07-11
 **/
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nField {

    /**
     * 指定目标实体中目标国际化字段的名称
     *
     * @return
     */
    String i18nField() default "";

}
