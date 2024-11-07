package jp.co.futech.framework.common.validation;

import jakarta.validation.ConstraintValidatorContext;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

/**
 * @description: 向spring validation中动态添加国际化参数
 * @author: ErHu.Zhao
 * @create: 2024-07-08
 **/
public class AddParameters {

    public static void addParameter(ConstraintValidatorContext context, MessageParameter... messageParameters) {
        if (ConstraintValidatorContextImpl.class.isInstance(context)) {
            ConstraintValidatorContextImpl validatorContext = (ConstraintValidatorContextImpl) context;
            for (int i = 0; i < messageParameters.length; i++) {
                validatorContext.addMessageParameter(messageParameters[i].key, messageParameters[i].value);
            }
        }
    }

    @Data
    @Builder
    static class MessageParameter {
        private String key;
        private Object value;
    }
}
