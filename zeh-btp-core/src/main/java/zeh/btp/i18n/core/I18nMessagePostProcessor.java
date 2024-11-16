package zeh.btp.i18n.core;

import java.util.Locale;

/**
 * @description: 国际化字段后置处理器
 * @author: ErHu.Zhao
 * @create: 2024-09-02
 **/
public interface I18nMessagePostProcessor {

    /**
     * 是否支持国际化后置处理
     *
     * @param currentLocale 当前处理国际化的locale对象
     * @return 是否支持国际化后置处理 true：支持 false：不支持
     */
    boolean supports(Locale currentLocale);

    /**
     * 字段国际化后置处理
     *
     * @param currentLocale  当前处理国际化的locale对象
     * @param code           当前国际化code
     * @param valueAfterI18n 处理后的国际化message
     * @return 自定义处理后的国际化message
     */
    String postProcessAfterI18nConvert(Locale currentLocale, String code, String valueAfterI18n);
}
