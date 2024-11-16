package zeh.btp.scope.core;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @description: 所有被scopedProxy代理的bean容器
 * @author: ErHu.Zhao
 * @create: 2024-08-22
 **/
@Slf4j
public class ScopedProxyBeanContainer extends HashMap<String, Object> {

    /**
     * 触发所有被scopedProxy代理的bean的真实创建，确保spring容器启动线程提前创建真实的scope bean
     * <p>
     * 在容器启动期间提前触发scoped bean的真实bean创建，目的是为了能够在 CustomizedRefreshScopePostProcessor 中，对所有BeanScopeManager进行创建并入缓存，防止在业务线程中产生并发
     */
    public void triggerTargetBeansCreation() {
        this.forEach((k, v) -> {
            log.info("begin to trigger scoped proxy object to create real bean instance,beanName is {}", k);
            // 调用代理对象任意方法，触发scoped作用域的get方法真实创建执行bean的真实创建
            v.toString();
            log.info("end to trigger scoped proxy object to create real bean instance");
        });
    }
}
