package zeh.btp.validation.annotation.show;

import zeh.btp.validation.annotation.show.common.ReferenceField;
import zeh.btp.validation.annotation.validate.EnumValid;

import java.lang.annotation.*;

@Target({}) //不允许标注在任何目标元素上
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ReferenceEnums {

    ReferenceField referenceField();

    EnumValid enumVa();
}
