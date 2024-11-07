package ft.btp.scope.annotation;

import ft.btp.scope.hook.BeanScopeManager;
import ft.btp.scope.scope.CustomizedRefreshScopeProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

/**
 * @description: 自定义的bean作用域注解
 * @author: ErHu.Zhao
 * @create: 2024-08-20
 **/
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
// 为该作用域的bean创建一个代理
@Scope(value = CustomizedRefreshScopeProcessor.CUSTOMIZED_REFRESH_SCOPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public @interface CustomizedRefreshScope {

    /**
     * 惰性异步刷新周期，单位为 minutes
     *
     * @return 必须大于0，小于0表示不进行刷新
     */
    long lazyAsyncRefreshPeriodMinutes() default 0;

    /**
     * 自定义的bean作用域管理器
     *
     * @return
     */
    Class<? extends BeanScopeManager> manager();
}
