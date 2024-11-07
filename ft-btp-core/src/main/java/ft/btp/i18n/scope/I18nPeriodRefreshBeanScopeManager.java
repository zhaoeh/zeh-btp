package ft.btp.i18n.scope;

import ft.btp.scope.builder.IocBeanBuilder;
import ft.btp.scope.hook.PeriodRefreshBeanScopeManager;
import org.springframework.lang.Nullable;

/**
 * @description: 国际化定时刷新的bean scope管理器
 * @author: ErHu.Zhao
 * @create: 2024-08-23
 **/
public interface I18nPeriodRefreshBeanScopeManager extends PeriodRefreshBeanScopeManager {

    /**
     * 从自定义作用域中获取目标bean
     *
     * @param name           目标bean name
     * @param iocBeanBuilder IOC bean 构建器
     * @return 目标bean实例
     */
    @Override
    default Object get(String name, IocBeanBuilder iocBeanBuilder) {
        return null;
    }

    /**
     * 从自定义作用域中删除bean
     *
     * @param name 目标bean name
     * @return 目标bean实例
     */
    @Override
    @Nullable
    default Object remove(String name) {
        return null;
    }
}
