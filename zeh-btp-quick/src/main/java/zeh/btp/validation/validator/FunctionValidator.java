package zeh.btp.validation.validator;

import zeh.btp.validation.po.MessagesPlace;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

@FunctionalInterface
public interface FunctionValidator<T extends Annotation> {

    void validate(Field field, Object target, List<MessagesPlace> result, T annotation, String targetFieldName, String targetFieldValue);
}
