package zeh.btp.i18n.annotation;

import zeh.btp.i18n.api.DefaultI18nCodeMapper;
import zeh.btp.i18n.api.I18nCodeMapper;

import java.lang.annotation.*;

/**
 * @description: 注解，标识于method上，用于表示一个方法开启国际化功能
 * @author: ErHu.Zhao
 * @create: 2024-07-11
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableI18nFunctionForMethod {

    /**
     * 国际化消息message - code 映射器配置
     *
     * @return
     */
    Class<? extends I18nCodeMapper> mapperConfigure() default DefaultI18nCodeMapper.class;
}
