package zeh.btp.i18n.tools;

import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @description: 类型工具类
 * @author: ErHu.Zhao
 * @create: 2024-07-11
 **/
public class TypeUtils {

    public static boolean isSimple(@NotNull Class<?> clazz) {
        Assert.notNull(clazz, "class instance cannot be null");
        return isPrimitive(clazz) || clazz.equals(String.class);
    }

    public static boolean isPrimitive(@NotNull Class<?> clazz) {
        Assert.notNull(clazz, "class instance cannot be null");
        return clazz.isPrimitive() || isWrapPrimitive(clazz);
    }

    private static boolean isWrapPrimitive(Class<?> clazz) {
        try {
            return ((Class) (clazz.getField("TYPE").get(null))).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isArray(@NotNull Class<?> clazz) {
        Assert.notNull(clazz, "class instance cannot be null");
        return clazz.isArray();
    }

    public static boolean isList(@NotNull Class<?> clazz) {
        Assert.notNull(clazz, "class instance cannot be null");
        return List.class.isAssignableFrom(clazz);
    }


}
