package zeh.btp.scope.holder;

import zeh.btp.scope.annotation.CustomizedRefreshScope;
import zeh.btp.scope.hook.BeanScopeManager;
import zeh.btp.scope.po.BeanScopeManagerDefinition;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @description: BeanScopeManager 持有器
 * @author: ErHu.Zhao
 * @create: 2024-08-21
 **/
public class BeanScopeManagerHolder {

    private Map<String, BeanScopeManagerDefinition> scopeManagers;

    public void cacheManagerContainer(Map<String, BeanScopeManagerDefinition> scopeManagers) {
        this.scopeManagers = scopeManagers;
    }

    /**
     * 查找 BeanScopeManagerDefinition 实例
     *
     * @param beanName                 目标bean名称
     * @param beanScopeManagerSupplier BeanScopeManager实例集合供应器
     * @return BeanScopeManagerDefinition 实例
     */
    public BeanScopeManagerDefinition findBeanScopeManager(String beanName, Supplier<List<BeanScopeManager>> beanScopeManagerSupplier) {
        Assert.hasText(beanName, "beanName cannot be empty");
        BeanScopeManagerDefinition definition = findBeanScopeManagerDefinition(beanName);
        if (Objects.isNull(definition)) {
            return null;
        }
        // 目标bean存在 @BeanScopeManager 注解，且显式指定了实现类的class
        return definition.setManager(handleBeanScopeManager(definition.getBeanScopeManagerClass(), beanScopeManagerSupplier));
    }

    /**
     * 查找 BeanScopeManagerDefinition 实例
     *
     * @param bean                      目标bean
     * @param beanScopeManagersProvider BeanScopeManager实例集合供应器
     * @return BeanScopeManagerDefinition 实例
     */
    public BeanScopeManagerDefinition findBeanScopeManager(Object bean,
                                                           Supplier<List<BeanScopeManager>> beanScopeManagersProvider) {
        Assert.notNull(bean, "bean cannot be null");
        Class<?> beanClass = getTargetClass(bean);
        BeanScopeManagerDefinition definition = findBeanScopeManagerDefinition(beanClass);
        if (Objects.isNull(definition)) {
            return null;
        }
        return definition.setManager(handleBeanScopeManager(definition.getBeanScopeManagerClass(), beanScopeManagersProvider));
    }

    private BeanScopeManager handleBeanScopeManager(Class<? extends BeanScopeManager> beanScopeManagerClass,
                                                    Supplier<List<BeanScopeManager>> beanScopeManagersProvider) {
        if (Objects.nonNull(beanScopeManagerClass) && !beanScopeManagerClass.equals(BeanScopeManager.class)) {
            BeanScopeManager manager = searchBeanScopeManager(beanScopeManagerClass, beanScopeManagersProvider);
            Assert.notNull(manager, "cannot find " + beanScopeManagerClass.getSimpleName() + " instance from spring container");
            return manager;
        }
        return null;
    }

    /**
     * 查找 BeanScopeManager 实例
     *
     * @param clazz
     * @return
     */
    private BeanScopeManager searchBeanScopeManager(Class<? extends BeanScopeManager> clazz,
                                                    Supplier<List<BeanScopeManager>> beanScopeManagerProvider) {
        if (Objects.nonNull(clazz) && Objects.nonNull(beanScopeManagerProvider)) {
            List<BeanScopeManager> beanScopeManagers = beanScopeManagerProvider.get();
            if (Objects.nonNull(beanScopeManagers)) {
                return beanScopeManagers.stream().filter(e -> clazz.isInstance(e)).findFirst().orElse(null);
            }
        }
        return null;
    }


    /**
     * 获取目标对象真实class
     *
     * @param bean
     * @return
     */
    private Class<?> getTargetClass(Object bean) {
        if (AopUtils.isAopProxy(bean)) {
            return AopProxyUtils.ultimateTargetClass(bean);
        }
        return bean.getClass();
    }

    /**
     * 查找目标Bean的 CustomizedRefreshScope 注解中的 BeanScopeManager 管理器 class 和 超时配置 ，组装为BeanScopeManagerDefinition返回
     *
     * @param beanType 目标bean class
     * @return BeanScopeManagerDefinition 实例
     */
    private BeanScopeManagerDefinition findBeanScopeManagerDefinition(Class<?> beanType) {
        if (Objects.nonNull(beanType) && beanType.isAnnotationPresent(CustomizedRefreshScope.class)) {
            CustomizedRefreshScope scope = AnnotationUtils.findAnnotation(beanType, CustomizedRefreshScope.class);
            if (Objects.nonNull(scope)) {
                return BeanScopeManagerDefinition.builder().beanScopeManagerClass(scope.manager()).
                        lazyAsyncRefreshPeriodMinutesFromAnnotation(scope.lazyAsyncRefreshPeriodMinutes()).build();
            }
        }
        return null;
    }

    /**
     * 查找 BeanScopeManagerDefinition
     *
     * @param beanName 目标bean名称
     * @return 对应的BeanScopeManagerDefinition实例
     */
    public BeanScopeManagerDefinition findBeanScopeManagerDefinition(String beanName) {
        if (Objects.nonNull(scopeManagers)) {
            return scopeManagers.get(beanName);
        }
        return null;
    }

    /**
     * 判断是否存在 BeanScopeManagerDefinition
     *
     * @param beanName
     * @return
     */
    public boolean existBeanScopeManagerDefinition(String beanName) {
        return Objects.nonNull(findBeanScopeManagerDefinition(beanName));
    }
}
