package jp.co.futech.framework.web.core.holder;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @description: 可跨线程透传的localeContext
 * @author: ErHu.Zhao
 * @create: 2024-09-04
 **/
public class TransmittableLocaleHolder {

    private static final ThreadLocal<String> LOCALE = new TransmittableThreadLocal<>();

    public static void setLocale(String locale) {
        LOCALE.set(locale);
    }

    public static String getLocale() {
        return LOCALE.get();
    }

    public static void clear() {
        LOCALE.remove();
    }
}
