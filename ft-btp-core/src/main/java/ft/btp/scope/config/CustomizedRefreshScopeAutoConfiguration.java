package ft.btp.scope.config;

import ft.btp.scope.core.*;
import ft.btp.scope.event.ScopedProxyBeanTriggerListener;
import ft.btp.scope.holder.BeanScopeManagerHolder;
import ft.btp.scope.hook.BeanScopeManager;
import ft.btp.scope.scope.CustomizedRefreshScopeProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static ft.btp.scope.constants.ScopeConstants.CACHE_TRIGGER_BEAN_NAME;

/**
 * @description: bean刷新作用域自动配置
 * @author: ErHu.Zhao
 * @create: 2024-08-20
 **/
@AutoConfiguration
public class CustomizedRefreshScopeAutoConfiguration {

    @Bean
    public CustomizedRefreshScopeCache customizedRefreshScopeCache() {
        return new CustomizedRefreshScopeCache();
    }

    @Bean
    public BeanScopeManagerHolder scopeManagerHolder() {
        return new BeanScopeManagerHolder();
    }

    @Bean
    public ScopedProxyBeanContainer scopedProxyBeanContainer() {
        return new ScopedProxyBeanContainer();
    }

    @Bean
    public ScopedProxyBeanTriggerListener scopedProxyBeanTriggerListener(ScopedProxyBeanContainer scopedProxyBeanContainer) {
        return new ScopedProxyBeanTriggerListener(scopedProxyBeanContainer);
    }

    @Bean
    public CustomizedRefreshScopeBeanFactoryPostProcessor customizedRefreshScopeBeanFactoryPostProcessor() {
        return new CustomizedRefreshScopeBeanFactoryPostProcessor();
    }

    /**
     * 注册bean刷新作用域对象
     *
     * @param customizedRefreshScopeProcessor
     * @return
     */
    @Bean
    public CustomScopeConfigurer customScopeConfigurer(CustomizedRefreshScopeProcessor customizedRefreshScopeProcessor) {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope(CustomizedRefreshScopeProcessor.CUSTOMIZED_REFRESH_SCOPE, customizedRefreshScopeProcessor);
        return customScopeConfigurer;
    }

    @Bean
    public BeanPostProcessor customizedRefreshScopePostProcessor() {
        return new CustomizedRefreshScopePostProcessor();
    }

    @Bean(CACHE_TRIGGER_BEAN_NAME)
    public CustomizedRefreshScopeCacheTrigger customizedRefreshScopeCacheTrigger(CustomizedRefreshScopeCache customizedRefreshScopeCache,
                                                                                 BeanScopeManagerHolder beanScopeManagerHolder,
                                                                                 List<BeanScopeManager> beanScopeManagers) {
        return new CustomizedRefreshScopeCacheTrigger(customizedRefreshScopeCache, beanScopeManagerHolder, beanScopeManagers);
    }

    @Bean
    public CustomizedRefreshScopeSmart customizedRefreshScopeSmart(List<BeanScopeManager> beanScopeManagers,
                                                                   CustomizedRefreshScopeCacheTrigger customizedRefreshScopeCacheTrigger,
                                                                   BeanScopeManagerHolder beanScopeManagerHolder,
                                                                   ScopedProxyBeanContainer scopedProxyBeanContainer) {
        return new CustomizedRefreshScopeSmart(beanScopeManagers, customizedRefreshScopeCacheTrigger, beanScopeManagerHolder, scopedProxyBeanContainer);
    }

    /**
     * 实例化自定义的对象刷新作用域scope
     *
     * @return
     */
    @Bean
    public CustomizedRefreshScopeProcessor objectRefreshScopeProcessor(CustomizedRefreshScopeCache refreshScopeCache) {
        return new CustomizedRefreshScopeProcessor(refreshScopeCache);
    }

}
