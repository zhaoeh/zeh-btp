package zeh.btp.scope.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.lang.Nullable;
import zeh.btp.scope.constants.ScopeConstants;

import java.util.Objects;

/**
 * @description: BeanScopeManager 管理器收集策略
 * @author: ErHu.Zhao
 * @create: 2024-08-21
 **/
public class CustomizedRefreshScopePostProcessor implements BeanPostProcessor, BeanFactoryAware {

    @Nullable
    private ConfigurableListableBeanFactory beanFactory;

    private CustomizedRefreshScopeCacheTrigger customizedRefreshScopeCacheTrigger;

    public CustomizedRefreshScopePostProcessor() {
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        instanceCacheTrigger();
        if (Objects.nonNull(customizedRefreshScopeCacheTrigger)) {
            customizedRefreshScopeCacheTrigger.initCachesByBeanScopeManagers(bean, beanName);
        }
        return bean;
    }

    /**
     * 实例化trigger
     */
    private void instanceCacheTrigger() {
        if (Objects.isNull(customizedRefreshScopeCacheTrigger) && beanFactory.containsSingleton(ScopeConstants.CACHE_TRIGGER_BEAN_NAME)) {
            customizedRefreshScopeCacheTrigger = beanFactory.getBean(ScopeConstants.CACHE_TRIGGER_BEAN_NAME, CustomizedRefreshScopeCacheTrigger.class);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            ConfigurableListableBeanFactory clbf = (ConfigurableListableBeanFactory) beanFactory;
            this.beanFactory = clbf;
        } else {
            throw new IllegalArgumentException("CustomizedRefreshScopePostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
    }
}
