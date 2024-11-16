package zeh.btp.validation.validator;

import zeh.btp.validation.annotation.StringValuesValid;
import zeh.btp.validation.po.MessagesPlace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.*;

/**
 * @description: String字段枚举值校验
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
public class StringValuesValidator implements ConstraintValidator<StringValuesValid, String> {

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    private StringValuesValid enumValid;

    @Override
    public void initialize(StringValuesValid constraintAnnotation) {
        enumValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(String target, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(target)) {
            return true;
        }
        final List<MessagesPlace> result = new ArrayList<>();
        valid(target, result);
        return isValid(context, result);
    }

    private MessagesPlace handleRangeMessage(StringValuesValid enums, String[] expectValues) {
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

    private void valid(String target, List<MessagesPlace> result) {
        isReject(target, result, enumValid);
    }

    public void isReject(String target, List<MessagesPlace> result, StringValuesValid values) {
        Optional.ofNullable(values).ifPresent(ens -> {
            String[] expectValues = ens.values();
            if (Objects.isNull(expectValues)) {
                return;
            }
            if (!Arrays.stream(expectValues).distinct().anyMatch(v -> v.equals(target))) {
                result.add(handleRangeMessage(values, expectValues));
            }
        });
    }
}
