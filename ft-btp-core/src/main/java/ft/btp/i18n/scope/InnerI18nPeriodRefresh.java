package ft.btp.i18n.scope;

import ft.btp.scope.builder.IocBeanBuilder;
import ft.btp.scope.hook.PeriodRefreshBeanScopeManager;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Optional;

/**
 * @description: 国际化过期时间配置类
 * @author: ErHu.Zhao
 * @create: 2024-08-20
 **/
public class InnerI18nPeriodRefresh implements PeriodRefreshBeanScopeManager {

    private final I18nPeriodRefreshBeanScopeManager scopeManager;

    public InnerI18nPeriodRefresh(ObjectProvider<I18nPeriodRefreshBeanScopeManager> provider) {
        this.scopeManager = provider.getIfAvailable();
    }

    @Override
    public long lazyAsyncRefreshPeriodMinutes() {
        return Optional.ofNullable(scopeManager).map(PeriodRefreshBeanScopeManager::lazyAsyncRefreshPeriodMinutes).orElse(0L);
    }

    @Override
    public Object get(String name, IocBeanBuilder iocBeanBuilder) {
        return Optional.ofNullable(scopeManager).map(t -> t.get(name, iocBeanBuilder)).orElse(null);
    }

    @Override
    public Object remove(String name) {
        return Optional.ofNullable(scopeManager).map(t -> t.remove(name)).orElse(null);
    }
}
