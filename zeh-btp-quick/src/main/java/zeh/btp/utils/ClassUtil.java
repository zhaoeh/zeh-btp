package zeh.btp.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;

public class ClassUtil {

    /**
     * 判断字段名是否与实体类的字段名一致
     *
     * @param fieldName 字段名(入参)
     * @param clazz     数据库对应实体类
     * @param <T>       数据库对应实体类
     * @return true:一致/false:不一致
     */
    public static <T> boolean fieldInClass(String fieldName, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        // 校验父类
        if (ObjectUtils.isNotEmpty(clazz.getSuperclass())) {
            return fieldInClass(fieldName, clazz.getSuperclass());
        }
        return false;
    }
}