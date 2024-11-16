package zeh.btp.scope.core;

import zeh.btp.scope.annotation.CustomizedRefreshScope;
import zeh.btp.scope.hook.BeanScopeManager;
import zeh.btp.scope.po.BeanScopeManagerDefinition;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @description: BeanScopeManager 配置器收集处理
 * @author: ErHu.Zhao
 * @create: 2024-08-21
 **/
public class CustomizedRefreshScopeBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private final Map<String, BeanScopeManagerDefinition> methodContainer = new HashMap<>();
    private final Map<String, String> beanName2FactoryMethodNameMapping = new HashMap<>();
    // 最终缓存：beanName -> BeanScopeManagerDefinition，注意，beanName前缀是 scopedTarget.
    private final Map<String, BeanScopeManagerDefinition> finallyManagerContainer = new HashMap<>();

    @SneakyThrows
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        doCollectBeanManagersOfFactoryMethod(beanFactory);
        doCollectBeanManagersOfBeanName(beanFactory);
        LocalCaches.bindFinallyManagerContainer(finallyManagerContainer);
    }

    /**
     * 收集以FactoryMethod作为Key的 BeanScopeManager 集合
     *
     * @param beanFactory
     * @throws ClassNotFoundException
     */
    private void doCollectBeanManagersOfFactoryMethod(ConfigurableListableBeanFactory beanFactory) throws ClassNotFoundException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            // 当前bean是普通方式注册，检查其中的@Bean方法，查找自定义注解
            if (beanDefinition.getFactoryMethodName() == null) {
                String beanClassName = beanDefinition.getBeanClassName();
                collectBeanScopeManager(beanDefinitionName, beanClassName);
            }
            if (beanDefinition.getFactoryMethodName() != null) {
                beanName2FactoryMethodNameMapping.put(beanDefinitionName, beanDefinition.getFactoryMethodName());
            }
        }
    }

    /**
     * 收集以beanName作为Key的 BeanScopeManager 集合
     *
     * @param beanFactory
     */
    private void doCollectBeanManagersOfBeanName(ConfigurableListableBeanFactory beanFactory) {
        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(beanName ->
                getBeanScopeManager(beanName).ifPresent(d -> finallyManagerContainer.put(beanName, d)));
    }

    /**
     * 根据 beanClassName 聚合目标bean中标注自定义注解 CustomizedRefreshScope 的BeanScopeManagerDefinition
     *
     * @param beanName
     * @param beanClassName
     * @return
     * @throws ClassNotFoundException
     */
    private void collectBeanScopeManager(String beanName, String beanClassName) throws ClassNotFoundException {
        if (Objects.nonNull(beanClassName)) {
            // beanClassName不为null说明是普通方式注册的bean
            Class<?> beanClass = Class.forName(beanClassName);
            // 聚合普通方式注册的bean
            if (beanClass.isAnnotationPresent(CustomizedRefreshScope.class)) {
                CustomizedRefreshScope scope = beanClass.getAnnotation(CustomizedRefreshScope.class);
                buildBeanScopeManagerDefinition(scope).ifPresent(d -> finallyManagerContainer.put(beanName, d));
            }
            // 聚合@Bean方式注册的bean
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(CustomizedRefreshScope.class)) {
                    CustomizedRefreshScope scope = method.getAnnotation(CustomizedRefreshScope.class);
                    buildBeanScopeManagerDefinition(scope).ifPresent(d -> methodContainer.put(method.getName(), d));
                }
            }
        }
    }

    /**
     * 根据scope注解构建 BeanScopeManagerDefinition 实例
     *
     * @param scope
     * @return
     */
    private Optional<BeanScopeManagerDefinition> buildBeanScopeManagerDefinition(CustomizedRefreshScope scope) {
        BeanScopeManagerDefinition definition = null;
        if (Objects.nonNull(scope)) {
            Class<? extends BeanScopeManager> managerClass = scope.manager();
            long period = scope.lazyAsyncRefreshPeriodMinutes();
            definition = BeanScopeManagerDefinition.builder().beanScopeManagerClass(managerClass).
                    lazyAsyncRefreshPeriodMinutesFromAnnotation(period).build();
        }
        return Optional.ofNullable(definition);
    }

    /**
     * 根据beanName查找 BeanScopeManager class
     *
     * @param beanName
     * @return
     */
    private Optional<BeanScopeManagerDefinition> getBeanScopeManager(String beanName) {
        BeanScopeManagerDefinition definition = null;
        String factoryMethodName = beanName2FactoryMethodNameMapping.get(beanName);
        if (StringUtils.hasText(factoryMethodName)) {
            definition = methodContainer.get(factoryMethodName);
        }
        return Optional.ofNullable(definition);
    }

}
