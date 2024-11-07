package jp.co.futech.framework.tenant.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IgnoreTenantRequest {
}
