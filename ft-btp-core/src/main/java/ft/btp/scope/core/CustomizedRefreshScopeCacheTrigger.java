package ft.btp.scope.core;

import ft.btp.scope.holder.BeanScopeManagerHolder;
import ft.btp.scope.hook.BeanScopeManager;
import ft.btp.scope.hook.PeriodRefreshBeanScopeManager;
import ft.btp.scope.po.BeanScopeManagerDefinition;
import org.springframework.aop.scope.ScopedProxyUtils;

import java.util.List;
import java.util.Objects;

/**
 * @description: 触发器
 * @author: ErHu.Zhao
 * @create: 2024-08-27
 **/
public class CustomizedRefreshScopeCacheTrigger {

    private final CustomizedRefreshScopeCache customizedRefreshScopeCache;

    private final BeanScopeManagerHolder beanScopeManagerHolder;

    private final List<BeanScopeManager> beanScopeManagers;

    public CustomizedRefreshScopeCacheTrigger(CustomizedRefreshScopeCache customizedRefreshScopeCache,
                                              BeanScopeManagerHolder beanScopeManagerHolder,
                                              List<BeanScopeManager> beanScopeManagers) {
        this.customizedRefreshScopeCache = customizedRefreshScopeCache;
        this.beanScopeManagerHolder = beanScopeManagerHolder;
        this.beanScopeManagers = beanScopeManagers;
    }

    /**
     * 解析标注了 CustomizedRefreshScope 注解的bean，根据配置的manager class初始化缓存
     *
     * @param bean
     * @param beanName
     */
    public void initCachesByBeanScopeManagers(Object bean, String beanName) {
        if (ScopedProxyUtils.isScopedTarget(beanName)) {
            // 根据 beanName 从缓存中获取对应的 BeanScopeManagerDefinition ，同时从现有BeanScopeManager集合中找到对应的BeanScopeManager对象进行设置
            BeanScopeManagerDefinition definition = beanScopeManagerHolder.findBeanScopeManager(beanName, () -> beanScopeManagers);
            if (Objects.isNull(definition) || Objects.isNull(definition.getManager())) {
                // 补救获取目标bean上的CustomizedRefreshScope注解并组装对应的BeanScopeManagerDefinition
                definition = beanScopeManagerHolder.findBeanScopeManager(bean, () -> beanScopeManagers);
            }
            if (Objects.nonNull(definition)) {
                long cacheMinutes = definition.getLazyAsyncRefreshPeriodMinutesFromAnnotation();
                BeanScopeManager manager = definition.getManager();
                if (manager instanceof PeriodRefreshBeanScopeManager) {
                    cacheMinutes = ((PeriodRefreshBeanScopeManager) manager).lazyAsyncRefreshPeriodMinutes();
                }
                customizedRefreshScopeCache.initCaches(beanName, cacheMinutes, manager);
            }
        }
    }

}
