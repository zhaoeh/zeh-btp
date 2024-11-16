package zeh.btp.scope.core;

import zeh.btp.scope.event.ScopedProxyBeanTriggerEvent;
import zeh.btp.scope.holder.BeanScopeManagerHolder;
import zeh.btp.scope.hook.BeanScopeManager;
import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-08-26
 **/
public class CustomizedRefreshScopeSmart implements SmartInitializingSingleton, BeanFactoryAware, ApplicationContextAware, ApplicationEventPublisherAware {

    @Nullable
    private ConfigurableListableBeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private ApplicationEventPublisher applicationEventPublisher;

    private final List<BeanScopeManager> beanScopeManagers;

    private final CustomizedRefreshScopeCacheTrigger customizedRefreshScopeCacheTrigger;

    private final BeanScopeManagerHolder beanScopeManagerHolder;

    private final ScopedProxyBeanContainer scopedProxyBeanContainer;

    public CustomizedRefreshScopeSmart(List<BeanScopeManager> beanScopeManagers,
                                       CustomizedRefreshScopeCacheTrigger customizedRefreshScopeCacheTrigger,
                                       BeanScopeManagerHolder beanScopeManagerHolder,
                                       ScopedProxyBeanContainer scopedProxyBeanContainer) {
        this.beanScopeManagers = beanScopeManagers;
        this.customizedRefreshScopeCacheTrigger = customizedRefreshScopeCacheTrigger;
        this.beanScopeManagerHolder = beanScopeManagerHolder;
        this.scopedProxyBeanContainer = scopedProxyBeanContainer;
    }

    @Override
    public void afterSingletonsInstantiated() {
        transferLocalCacheToHolder();
        doInitCaches();
        publishScopedProxyBeanTrigger();
    }

    /**
     * 转移本地缓存到内存对象
     */
    private void transferLocalCacheToHolder() {
        Assert.notNull(beanScopeManagerHolder, "beanScopeManagerHolder cannot be null");
        beanScopeManagerHolder.cacheManagerContainer(LocalCaches.getFinallyManagerContainer());
    }

    /**
     * 初始化作用域缓存
     */
    private void doInitCaches() {
        Iterator<String> beanNameIterator = beanFactory.getBeanNamesIterator();
        while (beanNameIterator.hasNext()) {

            String beanName = beanNameIterator.next();
            if (isBeanOfCustomizedRefreshScopeAnnotations(beanName)) {
                Object bean = beanFactory.getBean(beanName);
                handleScopedProxyFactoryBean(bean, beanName);
                if (!CollectionUtils.isEmpty(beanScopeManagers)) {
                    customizedRefreshScopeCacheTrigger.initCachesByBeanScopeManagers(bean, beanName);
                }
            }


        }
    }

    /**
     * 发布自定义scope代理对象主动触发事件
     */
    private void publishScopedProxyBeanTrigger() {
        applicationEventPublisher.publishEvent(new ScopedProxyBeanTriggerEvent(this.applicationContext));
    }

    /**
     * 处理scoped代理机制管理的代理bean
     *
     * @param bean
     * @param beanName
     */
    private void handleScopedProxyFactoryBean(Object bean, String beanName) {
        if (scopedProxyBeanContainer.containsKey(beanName)) {
            return;
        }
        if (isBeanOfCustomizedRefreshScopeAnnotations(beanName)) {
            if (ScopedProxyFactoryBean.class.equals(bean.getClass())) {
                ScopedProxyFactoryBean scopedProxyFactoryBean = (ScopedProxyFactoryBean) bean;
                if (scopedProxyFactoryBean.isProxyTargetClass()) {
                    cacheScopedProxyBeans(scopedProxyFactoryBean.getObject(), beanName);
                }
            } else if (AopUtils.isAopProxy(bean)) {
                cacheScopedProxyBeans(bean, beanName);
            }
        }
    }

    /**
     * 缓存所有scoped proxy的代理bean，用于后续在容器启动完毕后立即执行真实bean的创建逻辑
     *
     * @param bean
     * @param beanName
     */
    private void cacheScopedProxyBeans(Object bean, String beanName) {
        scopedProxyBeanContainer.put(beanName, bean);
    }

    /**
     * 是否是需要处理的bean
     *
     * @param beanName
     * @return
     */
    private boolean isBeanOfCustomizedRefreshScopeAnnotations(String beanName) {
        return ScopedProxyUtils.isScopedTarget(beanName) && beanScopeManagerHolder.existBeanScopeManagerDefinition(beanName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            ConfigurableListableBeanFactory clbf = (ConfigurableListableBeanFactory) beanFactory;
            this.beanFactory = clbf;
        } else {
            throw new IllegalArgumentException("CustomizedRefreshScopeSmart requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
