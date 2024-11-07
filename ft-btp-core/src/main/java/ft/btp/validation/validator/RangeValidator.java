package ft.btp.validation.validator;

import ft.btp.validation.po.MessagesPlace;
import io.micrometer.common.util.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 校验 @Range
 * @author: ErHu.Zhao
 * @create: 2024-07-02
 **/
public class RangeValidator {

    private static final String defaultMessage = "{org.hibernate.validator.constraints.Range.message}";

    public static void isReject(Field field, Object target, List<MessagesPlace> result, Range ran, String targetFieldName, String targetFieldValue) {
        if (Objects.isNull(ran)) {
            return;
        }
        long min = ran.min();
        long max = ran.max();
        ReflectionUtils.makeAccessible(field);
        // 当前字段的值
        Object currentFieldValue = ReflectionUtils.getField(field, target);
        if (Objects.nonNull(currentFieldValue)) {
            long currentValue = Long.valueOf(currentFieldValue.toString());
            if (currentValue < min || currentValue > max) {
                result.add(handleRangeMessage(ran, field, min, max, targetFieldName, targetFieldValue));
                return;
            }
        }
    }

    /**
     * 处理@Range注解的message
     *
     * @param ran
     * @param field
     * @param min
     * @param max
     * @return
     */
    private static MessagesPlace handleRangeMessage(Range ran, Field field, long min, long max, String targetFieldName, String targetFieldValue) {
        String message = ran.message();
        message = StringUtils.isNotBlank(message) && !defaultMessage.equals(message) ? message : "{000000002}";
        String fieldName = field.getName();
        return MessagesPlace.builder().message(message).places(
                Map.of("name", targetFieldName, "value", targetFieldValue, "current", fieldName, "min", min, "max", max)).build();

    }
}
