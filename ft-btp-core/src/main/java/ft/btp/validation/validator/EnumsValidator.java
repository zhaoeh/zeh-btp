package ft.btp.validation.validator;

import ft.btp.validation.annotation.validate.EnumValid;
import ft.btp.validation.po.MessagesPlace;
import io.micrometer.common.util.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 关联校验指定枚举
 * @author: ErHu.Zhao
 * @create: 2024-07-02
 **/
public class EnumsValidator {

    public static void isReject(Field field, Object target, List<MessagesPlace> result, EnumValid enums, String targetFieldName, String targetFieldValue) {
        Optional.ofNullable(enums).ifPresent(ens -> {
            Class<?> enumClass = ens.enumClass();
            String enumFiled = ens.enumField();
            if (enumClass.isEnum()) {
                Field f = ReflectionUtils.findField(enumClass, enumFiled);
                ReflectionUtils.makeAccessible(f);
                // 获取所有枚举常量对象
                Object[] objects = enumClass.getEnumConstants();
                List<String> enumsValues = Arrays.stream(objects).map(ev -> Objects.toString(ReflectionUtils.getField(f, ev))).collect(Collectors.toList());
                ReflectionUtils.makeAccessible(field);
                // 当前字段的值
                Object currentFieldValue = ReflectionUtils.getField(field, target);
                if (!enumsValues.contains(Objects.toString(currentFieldValue))) {
                    result.add(handleRangeMessage(enums, field, enumsValues, targetFieldName, targetFieldValue));
                }
            }
        });
    }

    private static MessagesPlace handleRangeMessage(EnumValid enums, Field field, List<String> enumsValues, String targetFieldName, String targetFieldValue) {
        String message = StringUtils.isNotBlank(enums.message()) ? enums.message() : "{000000011}";
        String fieldName = field.getName();
        return MessagesPlace.builder().message(message).places(Map.of("name", targetFieldName, "value", targetFieldValue, "current", fieldName, "target", enumsValues)).build();
    }
}
