package ft.btp.validation.annotation;

import ft.btp.validation.annotation.show.common.ReferenceField;
import org.hibernate.validator.constraints.Range;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ReferenceRange {

    ReferenceField referenceField();

    Range range();
}
