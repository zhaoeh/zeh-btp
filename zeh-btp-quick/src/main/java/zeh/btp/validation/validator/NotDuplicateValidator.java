package zeh.btp.validation.validator;

import zeh.btp.validation.annotation.NotDuplicate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

/**
 * @description: 关联校验指定枚举
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
public class NotDuplicateValidator implements ConstraintValidator<NotDuplicate, List<?>> {

    private NotDuplicate enumValid;

    @Override
    public void initialize(NotDuplicate constraintAnnotation) {
        enumValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(List<?> objects, ConstraintValidatorContext context) {
        // 空list认为合法
        if (CollectionUtils.isEmpty(objects)) {
            return true;
        }
        HashSet<Object> set = new HashSet<>(objects);
        if (set.size() != objects.size()) {
            String message = StringUtils.isNotBlank(enumValid.message()) ? enumValid.message() : "current collection not allowed duplicate";
            // 禁用默认message
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }

}
