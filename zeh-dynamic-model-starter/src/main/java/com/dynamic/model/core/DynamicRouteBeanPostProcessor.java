package com.dynamic.model.core;

import com.dynamic.model.annotation.Model;
import com.dynamic.model.context.BlendModelContextHolder;
import com.dynamic.model.context.BlendModelRoutedContextHolder;
import com.dynamic.model.context.ModelContextHolder;
import com.dynamic.model.enums.BizModel;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicRouteBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

    private final List<BizModel> validModels = BizModel.getValidModels();

    private ApplicationContext applicationContext;

    // 收集容器中带有@Mode注解的bean实例
    private final Map<Class<?>, Map<BizModel, Object>> modeComponentRegistry = new ConcurrentHashMap<>();

    // 代理对象缓存，key：
    private final Map<Class<?>, Object> proxyCache = new ConcurrentHashMap<>();


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> targetBeanClass = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean.getClass();
        Model model = targetBeanClass.getAnnotation(Model.class);
        if (Objects.isNull(model)) {
            // 不存在Model直接的bean实例，直接返回原始bean
            return bean;
        }
        Class<?>[] targetInterfaces = targetBeanClass.getInterfaces();

        Assert.isTrue(targetInterfaces.length == 1, "目标bean能且只能实现一个接口");
        Class<?> targetType = targetInterfaces[0];
        applicationContext.getBeansOfType(targetType);
        // 收集原始bean到缓存容器中，原始bean的所有接口以及自身的class作为key
        Map<BizModel, Object> innerMap = modeComponentRegistry.computeIfAbsent(targetType, k -> new ConcurrentHashMap<>());
        BizModel modelValue = model.value();
        if (Objects.equals(modelValue, BizModel.BLEND)) {
            throw new IllegalStateException("接口 " + targetType.getName() + " 实现类不允许标注 @Model(BizModel.BLEND)");
        }
        if (innerMap.containsKey(modelValue)) {
            throw new IllegalStateException("接口 " + targetType.getName() + " 所有实现类标注 @Model 注解存在重复模式");
        }
        innerMap.put(modelValue, bean);
        return proxyCache.computeIfAbsent(targetBeanClass, cls ->
                Proxy.newProxyInstance(targetBeanClass.getClassLoader(), targetInterfaces, (proxyObj, method, args) -> {
                    BizModel requestModel = getModelFromContext(targetType);

                    Object result = null;
                    if (Objects.equals(requestModel, BizModel.BLEND)) {
                        // 混合模式，分别迭代遍历调度对应的模式
                        BlendModelRoutedContextHolder.setRouted(true, true);
                        try {
                            for (BizModel m : validModels) {
                                // 多模式分别设置上下文标记循环调度，返回最后一次调度的结果即可
                                ModelContextHolder.set(m);
                                Object target = resolveTarget(targetType, m);
                                result = invokeTarget(target, method, args);
                            }
                            // 还原上下文模式
                            ModelContextHolder.set(BizModel.BLEND);
                        } finally {
                            // 清理
                            BlendModelRoutedContextHolder.clear();
                        }
                    } else {
                        Object target = resolveTarget(targetType, requestModel);
                        result = invokeTarget(target, method, args);
                    }
                    return result;
                }));
    }


    /**
     * 根据上下文请求模式，获取对应接口类型实现类中标注的model，获取之前进行校验
     * @param apiType 接口类型
     * @return 接口类型实现组件中匹配到请求上下文中模式的路由实现
     */
    private BizModel getModelFromContext(Class<?> apiType) {
        // 按照接口类型查找
        Map<BizModel, Object> candidates = modeComponentRegistry.get(apiType);
        if (CollectionUtils.isEmpty(candidates)) {
            throw new IllegalStateException("未找到实现：" + apiType.getName());
        }
        String[] beanNames = applicationContext.getBeanNamesForType(apiType);
        if (beanNames.length != candidates.size()) {
            throw new IllegalStateException("接口 " + apiType.getName() + " 所有实现类必须同时标注 @Model 注解");
        }

        if(ModelContextHolder.isIgnore()){
            throw new IllegalStateException("接口 " + apiType.getName() + " 不能应用于一个忽略动态路由的请求中，请检查你的设计逻辑以及 @Model 注解的标注是否合理");
        }
        return Optional.ofNullable(ModelContextHolder.get()).orElse(BizModel.CA);
    }

    /**
     * 根据上下文模式和接口类型，获取对应的路由bean实例
     * @param apiType 接口类型
     * @param requestModel 上下文请求模式
     * @return 匹配到的路由bean实例
     */
    private Object resolveTarget(Class<?> apiType, BizModel requestModel) {
        // 按照接口类型查找
        Map<BizModel, Object> candidates = modeComponentRegistry.get(apiType);
        Object target = candidates.get(requestModel);
        if (Objects.isNull(target)) {
            throw new IllegalStateException("未找到路由实现：" + requestModel + " for type " + apiType.getName());
        }
        return target;
    }

    /**
     * 调度执行路由bean的目标方法
     * @param target 路由bean实例
     * @param method 目标方法
     * @param args 目标方法参数
     * @return 调度执行结果
     * @throws InvocationTargetException 异常
     * @throws IllegalAccessException 异常
     */
    private Object invokeTarget(Object target, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 该bean post processor的order排序
     * @return 对应的排序
     */
    @Override
    public int getOrder() {
        return Integer.MAX_VALUE - 100;
    }
}
