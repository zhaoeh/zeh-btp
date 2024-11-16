package zeh.btp.i18n.core;

import jakarta.annotation.PostConstruct;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.lang.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * @description: I18nMessageWrapper 国际化包装器，对MessageSource对象进行包装
 * @author: ErHu.Zhao
 * @create: 2024-06-21
 **/
public class I18nMessageWrapper implements MessageSourceAware {

    private MessageSource messageSource;

    private Locale defaultLocale = new Locale("en", "US");

    @PostConstruct
    public void init() {
        I18nMessageHelper.setI(this);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, I18nLocaleWrapper.obtainLocale());
    }

    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, key, Objects.isNull(locale) ? defaultLocale : locale);
    }

    public String getMessage(String key, String defaultMessage) {
        return messageSource.getMessage(key, null, Objects.isNull(defaultMessage) ? key : defaultMessage, defaultLocale);
    }

    public String getMessage(String key, String defaultMessage, Locale locale) {
        return messageSource.getMessage(key, null, Objects.isNull(defaultMessage) ? key : defaultMessage, Objects.isNull(locale) ? defaultLocale : locale);
    }

    public String getMessage(String key, @Nullable Object[] places) {
        return messageSource.getMessage(key, places, defaultLocale);
    }

    public String getMessage(String key, @Nullable Object[] places, String defaultMessage) {
        return messageSource.getMessage(key, places, Objects.isNull(defaultMessage) ? key : defaultMessage, defaultLocale);
    }

    public String getMessage(String key, @Nullable Object[] places, Locale locale) {
        return messageSource.getMessage(key, places, key, Objects.isNull(locale) ? defaultLocale : locale);
    }

    public String getMessage(String key, @Nullable Object[] places, String defaultMessage, Locale locale) {
        return messageSource.getMessage(key, places, Objects.isNull(defaultMessage) ? key : defaultMessage, Objects.isNull(locale) ? defaultLocale : locale);
    }
}
