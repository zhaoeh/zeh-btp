package ft.btp.i18n.core;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Locale;

/**
 * @description: i18n Locale包装器
 * @author: ErHu.Zhao
 * @create: 2024-06-21
 **/
public class I18nLocaleWrapper {

    public static Locale obtainLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public String toString() {
        Locale locale = obtainLocale();
        if (Arrays.stream(Locale.getAvailableLocales()).anyMatch(l -> l.equals(locale))) {
            return locale.toString();
        }
        return "";
    }

}
