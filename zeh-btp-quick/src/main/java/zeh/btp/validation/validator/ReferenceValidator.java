package zeh.btp.validation.validator;

import zeh.btp.validation.annotation.ReferenceManager;
import zeh.btp.validation.annotation.ReferenceRange;
import zeh.btp.validation.annotation.ReferenceValid;
import zeh.btp.validation.annotation.show.ReferenceEnums;
import zeh.btp.validation.annotation.show.common.ReferenceField;
import zeh.btp.validation.annotation.validate.EnumValid;
import zeh.btp.validation.messages.MessageSourceHolder;
import zeh.btp.validation.po.MessagesPlace;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @description: 关联字段校验器
 * @author: ErHu.Zhao
 * @create: 2024-07-02
 **/
@Slf4j
public class ReferenceValidator implements ConstraintValidator<ReferenceManager, Object> {

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    @Override
    public void initialize(ReferenceManager constraintAnnotation) {
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
        if (Objects.isNull(target)) {
            return true;
        }
        final List<MessagesPlace> result = new ArrayList<>();
        Class<?> targetClazz = target.getClass();
        ReflectionUtils.doWithFields(targetClazz, field -> valid(targetClazz, field, target, result));
        return isValid(context, result);
    }

    private boolean isValid(ConstraintValidatorContext context, final List<MessagesPlace> result) {
        if (result.size() > 0) {
            MessagesPlace message = result.get(0);
            if (Objects.nonNull(message)) {
                String originalMsg = placeholderHelper.replacePlaceholders(message.getMessage(), s -> s);
                String afterMsg = MessageSourceHolder.getMessage(originalMsg);
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

    private ReferenceValid obtainReferenceValid(Field field) {
        // 遍历所有字段，获取标注ReferenceValid注解的字段
        ReferenceValid referenceValid = AnnotatedElementUtils.findMergedAnnotation(field, ReferenceValid.class);
        if (Objects.nonNull(referenceValid)) {
            return referenceValid;
        }
        return null;
    }

    private ReferenceRange[] obtainReferenceRanges(ReferenceValid referenceValid) {
        if (Objects.nonNull(referenceValid)) {
            ReferenceRange[] referenceRanges = referenceValid.ranges();
            return referenceRanges;
        }
        return null;
    }

    private ReferenceEnums[] obtainReferenceEnums(ReferenceValid referenceValid) {
        if (Objects.nonNull(referenceValid)) {
            ReferenceEnums[] referenceEnums = referenceValid.enums();
            return referenceEnums;
        }
        return null;
    }

    private void valid(Class<?> targetClazz, Field field, Object target, List<MessagesPlace> result) {
        ReferenceValid referenceValid = obtainReferenceValid(field);
        ReferenceRange[] referenceRanges = obtainReferenceRanges(referenceValid);
        if (Objects.nonNull(referenceRanges)) {
            Arrays.stream(referenceRanges).forEach(range -> {
                ReferenceField referenceField = range.referenceField();
                Range ran = range.range();
                doReference(targetClazz, field, target, result, ran, RangeValidator::isReject, referenceField);
            });
        }
        ReferenceEnums[] referenceEnums = obtainReferenceEnums(referenceValid);
        if (Objects.nonNull(referenceEnums)) {
            Arrays.stream(referenceEnums).forEach(enu -> {
                ReferenceField referenceField = enu.referenceField();
                EnumValid enums = enu.enumVa();
                doReference(targetClazz, field, target, result, enums, EnumsValidator::isReject, referenceField);
            });
        }
    }


    private <T extends Annotation> void doReference(Class<?> targetClazz,
                                                    Field field,
                                                    Object target,
                                                    List<MessagesPlace> result,
                                                    T annotation,
                                                    FunctionValidator<T> validator,
                                                    ReferenceField referenceField) {
        if (Objects.nonNull(referenceField)) {
            String fieldName = referenceField.name();
            String when = referenceField.when();
            if (StringUtils.isNotBlank(fieldName) && StringUtils.isNotBlank(when)) {
                try {
                    isReject(targetClazz, field, target, result, annotation, validator, fieldName, when);
                } catch (Exception e) {
                    log.error("an error occurred:", e);
                    return;
                }

            }
        }
    }

    private <T extends Annotation> void isReject(Class<?> targetClazz, Field field, Object target, List<MessagesPlace> result, T annotation, FunctionValidator<T> validator, String fieldName, String when) {
        Field targetField = ReflectionUtils.findField(targetClazz, fieldName);
        if (Objects.isNull(target)) {
            return;
        }
        ReflectionUtils.makeAccessible(targetField);
        String targetFieldName = targetField.getName();
        String targetFieldValue = Objects.toString(ReflectionUtils.getField(targetField, target));
        if (when.equals(targetFieldValue)) {
            validator.validate(field, target, result, annotation, targetFieldName, targetFieldValue);
        }
    }

}
