package ft.btp.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: map工具类
 * @author: ErHu.Zhao
 * @create: 2024-10-11
 **/
public class MapUtil {


    public static Map<String, Object> of(String k, Object v) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(k, v);
        return map;
    }

    public static Map<String, Object> of(String k1, Object v1, String k2, Object v2) {
        Map<String, Object> map = new HashMap<>(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static Map<String, Object> of(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static Map<String, Object> of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        Map<String, Object> map = new HashMap<>(4);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static Map<String, Object> of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
        Map<String, Object> map = new HashMap<>(5);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }

    public static Map<String, Object> of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6, Object v6) {
        Map<String, Object> map = new HashMap<>(6);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        return map;
    }
}
