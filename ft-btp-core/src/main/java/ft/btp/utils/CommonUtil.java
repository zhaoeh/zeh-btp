package ft.btp.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import ft.btp.annotation.Null2Empty;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    /**
     * 将驼峰命名转换为数据库下划线格式
     *
     * @param camelCase 驼峰命名的字符串
     * @return 转换为数据库下划线格式的字符串
     */
    public static String toUnderlineCase(String camelCase) {
        Matcher matcher = HUMP_PATTERN.matcher(camelCase);
        StringBuilder builder = new StringBuilder(camelCase);
        int offset = 0;
        while (matcher.find()) {
            builder.replace(matcher.start() + offset, matcher.end() + offset, "_" + matcher.group().toLowerCase());
            offset++;
        }
        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 将数据库下划线格式转换为驼峰命名
     *
     * @param underlineCase 数据库下划线格式的字符串
     * @return 转换为驼峰命名的字符串
     */
    public static String toCamelCase(String underlineCase) {
        StringBuilder builder = new StringBuilder();
        String[] words = underlineCase.toLowerCase().split("_");
        for (String word : words) {
            builder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        return builder.toString();
    }

    /**
     * 指定注解的null String转换为空串
     *
     * @param object
     * @param <T>
     */
    @SneakyThrows
    public static <T> T null2Empty(T object) {
        if (Objects.nonNull(object)) {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.getType() == String.class && field.isAnnotationPresent(Null2Empty.class)) {
                    field.setAccessible(true);
                    if (Objects.isNull(field.get(object))) {
                        field.set(object, "");
                    }
                }
            }
        }
        return object;
    }

    public static <T> List<T> null2Emptys(List<T> objects) {
        if (!CollectionUtils.isEmpty(objects)) {
            List<T> targets = new ArrayList<>();
            for (T object : objects) {
                targets.add(null2Empty(object));
            }
            return targets;
        }
        return objects;
    }

    /**
     * 执行动作*
     *
     * @param original     原始对象
     * @param action       操作器
     * @param actionTarget 操作目标对象
     * @param <R>          原始对象泛型
     * @return 原始对象
     */
    public static <R> R doAction(@Nullable R original, @Nullable BiConsumer<Map<String, Object>, String> action, @Nullable Map<String, Object> actionTarget) {
        if (Objects.nonNull(action)) {
            action.accept(actionTarget, JSONObject.toJSONString(actionTarget, SerializerFeature.WriteMapNullValue));
        }
        return original;
    }
}
