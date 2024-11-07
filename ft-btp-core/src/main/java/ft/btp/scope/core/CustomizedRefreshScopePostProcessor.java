package ft.btp.scope.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.lang.Nullable;

import java.util.Objects;

import static ft.btp.scope.constants.ScopeConstants.CACHE_TRIGGER_BEAN_NAME;

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
        if (Objects.isNull(customizedRefreshScopeCacheTrigger) && beanFactory.containsSingleton(CACHE_TRIGGER_BEAN_NAME)) {
            customizedRefreshScopeCacheTrigger = beanFactory.getBean(CACHE_TRIGGER_BEAN_NAME, CustomizedRefreshScopeCacheTrigger.class);
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
