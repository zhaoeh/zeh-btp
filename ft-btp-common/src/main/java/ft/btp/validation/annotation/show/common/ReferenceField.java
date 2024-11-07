package ft.btp.validation.annotation.show.common;

import java.lang.annotation.*;

/**
 * @description: 依赖字段注解
 * @author: ErHu.Zhao
 * @create: 2024-07-02
 **/
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ReferenceField {

    /**
     * 依赖的字段名称
     *
     * @return
     */
    String name();

    /**
     * 依赖的字段取值
     *
     * @return
     */
    String when();
}
