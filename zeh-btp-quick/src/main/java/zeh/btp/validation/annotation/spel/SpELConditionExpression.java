package zeh.btp.validation.annotation.spel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: SpEL条件注解，只有当所有条件注解都满足时，才对依赖的表达式执行校验，否则直接放行
 * @author: ErHu.Zhao
 * @create: 2024-10-25
 **/
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpELConditionExpression {

    // SpEL表达式，要求返回值必须是Boolean
    String expression();
}
