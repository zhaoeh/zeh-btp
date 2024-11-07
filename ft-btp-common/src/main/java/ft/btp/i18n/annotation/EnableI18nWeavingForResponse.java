package ft.btp.i18n.annotation;

import ft.btp.i18n.api.DefaultI18nCodeMapper;
import ft.btp.i18n.api.I18nCodeMapper;
import ft.btp.i18n.api.I18nHandler;

import java.lang.annotation.*;

/**
 * @description: 注解，标识于method上，标识为一个controller method开启国际化织入器，目的是为国际化织入自定义逻辑
 * @author: ErHu.Zhao
 * @create: 2024-07-11
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableI18nWeavingForResponse {

    /**
     * 国际化消息message - code 映射器配置
     *
     * @return
     */
    Class<? extends I18nCodeMapper> mapperConfigure() default DefaultI18nCodeMapper.class;

    /**
     * 国际化处理器
     *
     * @return
     */
    Class<? extends I18nHandler> i18nHandler() default I18nHandler.class;
}
