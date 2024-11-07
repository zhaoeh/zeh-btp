package ft.btp.common;

import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Thread local helper
 * @author: ErHu.Zhao
 * @create: 2024-09-03
 **/
public class ThreadLocalHelper<T> {

    private static final ThreadLocal<Map> threadLocal = NamedThreadLocal.withInitial(() -> new HashMap(256));

    public static <T> T get(String key) {
        Map map = (Map) threadLocal.get();
        return (T) map.get(key);
    }

    public static <T> T get(String key, T defaultValue) {
        Map map = (Map) threadLocal.get();
        return (T) map.get(key) == null ? defaultValue : (T) map.get(key);
    }

    public static <T> void set(String key, T value) {
        Map map = (Map) threadLocal.get();
        map.put(key, value);
    }

    public static void clear() {
        threadLocal.remove();
    }

    public static void remove(String key) {
        Map map = (Map) threadLocal.get();
        map.remove(key);
    }

}
