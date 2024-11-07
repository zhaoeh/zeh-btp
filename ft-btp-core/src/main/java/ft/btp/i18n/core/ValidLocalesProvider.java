package ft.btp.i18n.core;

import ft.btp.i18n.info.ValidLocales;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Locale;

/**
 * @description: 有效locale供应器
 * @author: ErHu.Zhao
 * @create: 2024-07-09
 **/
@FunctionalInterface
public interface ValidLocalesProvider {

    /**
     * 有效locale支持器，同时指定当locale非法时的exception
     *
     * @return ValidLocales对象
     */
    ValidLocales localeSupports();

    /**
     * 静态方法默认实现以供通用
     *
     * @return 默认locales为：美式英语、日语、简体中文
     */
    static ValidLocales defaultLocaleSupports(@Nullable Exception exception) {
        List<Locale> validLocales = List.of(Locale.US, Locale.JAPAN, Locale.SIMPLIFIED_CHINESE);
        return ValidLocales.builder().exceptionOfCheckFailed(exception).defaultLocale(Locale.JAPAN).validLocales(validLocales).build();
    }

    /**
     * 静态方法默认实现以供通用
     *
     * @return
     */
    static ValidLocales defaultLocaleSupports() {
        List<Locale> validLocales = List.of(Locale.US, Locale.JAPAN, Locale.SIMPLIFIED_CHINESE);
        return ValidLocales.builder().defaultLocale(Locale.JAPAN).validLocales(validLocales).build();
    }
}
