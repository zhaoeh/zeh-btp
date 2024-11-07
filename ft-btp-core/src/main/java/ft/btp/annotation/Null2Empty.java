package ft.btp.annotation;

import java.lang.annotation.*;


/**
 * @description: null转空字符注解
 * @author: ErHu.Zhao
 * @create: 2024-10-07
 **/
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Null2Empty {

}
