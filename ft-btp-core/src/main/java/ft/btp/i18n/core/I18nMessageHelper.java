package ft.btp.i18n.core;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Locale;

/**
 * @description: i18n国际化消息帮助器
 * @author: ErHu.Zhao
 * @create: 2024-07-04
 **/
public class I18nMessageHelper {

    private static I18nMessageWrapper i18nMessageWrapperToUse;

    public static void setI(I18nMessageWrapper wrapper) {
        Assert.notNull(wrapper, "i18nMessageWrapper cannot be null");
        i18nMessageWrapperToUse = wrapper;
    }

    /**
     * 根据当前local获取国际化消息
     *
     * @param key
     * @return
     */
    public static String getMessageWithCurrentLocale(String key) {
        return i18nMessageWrapperToUse.getMessage(key, I18nLocaleWrapper.obtainLocale());
    }

    public static String getMessage(String key) {
        return i18nMessageWrapperToUse.getMessage(key);
    }

    public static String getMessage(String key, Locale locale) {
        return i18nMessageWrapperToUse.getMessage(key, locale);
    }

    public static String getMessage(String key, String defaultMessage) {
        return i18nMessageWrapperToUse.getMessage(key, defaultMessage);
    }

    public static String getMessage(String key, String defaultMessage, Locale locale) {
        return i18nMessageWrapperToUse.getMessage(key, defaultMessage, locale);
    }

    public static String getMessage(String key, @Nullable Object[] places) {
        return i18nMessageWrapperToUse.getMessage(key, places);
    }

    public static String getMessage(String key, @Nullable Object[] places, String defaultMessage) {
        return i18nMessageWrapperToUse.getMessage(key, places, defaultMessage);
    }

    public static String getMessage(String key, @Nullable Object[] places, Locale locale) {
        return i18nMessageWrapperToUse.getMessage(key, places, locale);
    }

    public static String getMessage(String key, @Nullable Object[] places, String defaultMessage, Locale locale) {
        return i18nMessageWrapperToUse.getMessage(key, places, defaultMessage, locale);
    }
}
