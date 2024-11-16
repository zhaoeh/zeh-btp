package zeh.btp.i18n.interpolator;

import jakarta.validation.ConstraintTarget;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.Payload;
import jakarta.validation.metadata.ConstraintDescriptor;
import jakarta.validation.metadata.ValidateUnwrappedValue;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: 约束上下文
 * @author: ErHu.Zhao
 * @create: 2024-07-12
 **/
public class CustomizedDefaultContext implements MessageInterpolator.Context {

    private Map<String, Object> attributes;

    private CustomizedDefaultContext(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public static CustomizedDefaultContext of() {
        return new CustomizedDefaultContext(null);
    }

    public static CustomizedDefaultContext of(Map<String, Object> attributes) {
        return new CustomizedDefaultContext(attributes);
    }

    @Override
    public ConstraintDescriptor<?> getConstraintDescriptor() {
        return new ConstraintDescriptor<Annotation>() {
            @Override
            public Annotation getAnnotation() {
                return null;
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public Set<Class<?>> getGroups() {
                return null;
            }

            @Override
            public Set<Class<? extends Payload>> getPayload() {
                return null;
            }

            @Override
            public ConstraintTarget getValidationAppliesTo() {
                return null;
            }

            @Override
            public List<Class<? extends ConstraintValidator<Annotation, ?>>> getConstraintValidatorClasses() {
                return null;
            }

            @Override
            public Map<String, Object> getAttributes() {
                return CollectionUtils.isEmpty(attributes) ? Collections.emptyMap() : attributes;
            }

            @Override
            public Set<ConstraintDescriptor<?>> getComposingConstraints() {
                return null;
            }

            @Override
            public boolean isReportAsSingleViolation() {
                return false;
            }

            @Override
            public ValidateUnwrappedValue getValueUnwrapping() {
                return null;
            }

            @Override
            public <U> U unwrap(Class<U> aClass) {
                return null;
            }
        };
    }

    @Override
    public Object getValidatedValue() {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
