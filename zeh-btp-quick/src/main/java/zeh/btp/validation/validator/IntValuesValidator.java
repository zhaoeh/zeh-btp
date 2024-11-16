package zeh.btp.validation.validator;

import zeh.btp.validation.annotation.IntValuesValid;
import zeh.btp.validation.po.MessagesPlace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.*;

/**
 * @description: Int字段枚举值校验
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
public class IntValuesValidator implements ConstraintValidator<IntValuesValid, Integer> {

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    private IntValuesValid enumValid;

    @Override
    public void initialize(IntValuesValid constraintAnnotation) {
        enumValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(Integer target, ConstraintValidatorContext context) {
        if (Objects.isNull(target)) {
            return true;
        }
        final List<MessagesPlace> result = new ArrayList<>();
        valid(target, result);
        return isValid(context, result);
    }

    private MessagesPlace handleRangeMessage(IntValuesValid enums, int[] expectValues) {
        String message = StringUtils.isNotBlank(enums.message()) ? enums.message() : "current value must be {target}";
        return MessagesPlace.builder().message(message).places(Map.of("target", Objects.isNull(expectValues) ? Collections.emptyList() : Arrays.asList(expectValues))).build();
    }


    private boolean isValid(ConstraintValidatorContext context, final List<MessagesPlace> result) {
        if (result.size() > 0) {
            MessagesPlace message = result.get(0);
            if (Objects.nonNull(message)) {
                // 禁用默认message
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(placeholderHelper.replacePlaceholders(message.getMessage(),
                        s -> {
                            Map<Object, Object> places = message.getPlaces();
                            if (CollectionUtils.isEmpty(places)) {
                                return s;
                            } else {
                                return Objects.toString(places.get(s));
                            }
                        })).addConstraintViolation();
            }
            return false;
        }
        return true;
    }

    private void valid(Integer target, List<MessagesPlace> result) {
        isReject(target, result, enumValid);
    }

    public void isReject(Integer target, List<MessagesPlace> result, IntValuesValid values) {
        Optional.ofNullable(values).ifPresent(ens -> {
            int[] expectValues = ens.values();
            if (Objects.isNull(expectValues)) {
                return;
            }
            if (!Arrays.stream(expectValues).distinct().anyMatch(v -> v == target)) {
                result.add(handleRangeMessage(values, expectValues));
            }
        });
    }
}
