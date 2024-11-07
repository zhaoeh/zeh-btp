package ft.btp.mfa.annotation;

import java.lang.annotation.*;

/**
 * @description: mfa 二次认证注解
 * @author: ErHu.Zhao
 * @create: 2024-02-28
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MfaAuth {
}
