package zeh.btp.scope.hook;

/**
 * @description: 支持定时刷新的bean scope管理器
 * @author: ErHu.Zhao
 * @create: 2024-08-23
 **/
public interface PeriodRefreshBeanScopeManager extends BeanScopeManager {

    /**
     * 惰性异步刷新周期，单位为 minutes
     *
     * @return 必须大于0，小于等于0认为无效
     */
    default long lazyAsyncRefreshPeriodMinutes() {
        return 0;
    }
}
