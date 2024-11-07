package ft.btp.i18n.annotation;

import java.lang.annotation.*;

/**
 * @description: 注解，标识于class上，用于标识一个目标对象是否开启字段i18n处理，请注意，只有当目标对象存在该注解时才会解析其中的所有成员
 * @author: ErHu.Zhao
 * @create: 2024-07-11
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableI18nField {

}
