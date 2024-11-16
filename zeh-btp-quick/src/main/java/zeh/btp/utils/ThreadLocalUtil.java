package zeh.btp.utils;

import com.riskcontrol.common.constants.Constant;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地线程工具类
 */
public class ThreadLocalUtil {


    private static final String current_caller = "currentCaller";

    private static final ThreadLocal threadLocal = ThreadLocal.withInitial(() -> new HashMap(4));


    public static <T> T get(String key) {
        Map map = (Map) threadLocal.get();
        return (T) map.get(key);
    }

    public static <T> T get(String key, T defaultValue) {
        Map map = (Map) threadLocal.get();
        return (T) map.get(key) == null ? defaultValue : (T) map.get(key);
    }

    public static void set(String key, Object value) {
        Map map = (Map) threadLocal.get();
        map.put(key, value);
    }

    /**
     * 保存初始的当前语言标识（包括服务调用者和语言）
     */
    public static void setRawCurrentLocale(String value) {
        if (StringUtils.isNotBlank(value)) {
            int num = value.indexOf(Constant.SPLIT_SYMBOL);
            set(current_caller, value.substring(0, num));
            set(Constant.CURRENT_LOCALE_MARK, value.substring(num + 1));
        }
    }

    /**
     * 获取服务调用者
     *
     * @return
     */
    public static String getCurrentCaller() {
        return get(current_caller);
    }

    /**
     * 获取当前本地语言
     *
     * @return
     */
    public static String getCurrentLocale() {
        return get(Constant.CURRENT_LOCALE_MARK);
    }

    public static void clear() {
        threadLocal.remove();
    }

}
