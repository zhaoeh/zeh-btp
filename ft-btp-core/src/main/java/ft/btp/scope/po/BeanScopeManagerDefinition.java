package ft.btp.scope.po;

import ft.btp.scope.hook.BeanScopeManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @description: BeanScopeManager 封装实体
 * @author: ErHu.Zhao
 * @create: 2024-08-23
 **/
@Builder
@AllArgsConstructor
@Data
public class BeanScopeManagerDefinition {

    /**
     * BeanScopeManager  class
     */
    private Class<? extends BeanScopeManager> beanScopeManagerClass;

    /**
     * BeanScopeManager 实例
     */
    private BeanScopeManager manager;

    /**
     * 惰性异步刷新周期，单位为 minutes
     */
    private long lazyAsyncRefreshPeriodMinutesFromAnnotation;
}
