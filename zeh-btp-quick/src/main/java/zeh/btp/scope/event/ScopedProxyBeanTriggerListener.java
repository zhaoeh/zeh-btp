package zeh.btp.scope.event;

import zeh.btp.scope.core.ScopedProxyBeanContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * @description: scoped proxy代理bean触发器，触发真实bean的创建执行
 * @author: ErHu.Zhao
 * @create: 2024-08-22
 **/
@Slf4j
public class ScopedProxyBeanTriggerListener implements ApplicationListener<ScopedProxyBeanTriggerEvent> {

    private final ScopedProxyBeanContainer scopedProxyBeanContainer;

    public ScopedProxyBeanTriggerListener(ScopedProxyBeanContainer scopedProxyBeanContainer) {
        this.scopedProxyBeanContainer = scopedProxyBeanContainer;
    }

    @Override
    public void onApplicationEvent(ScopedProxyBeanTriggerEvent event) {
        scopedProxyBeanContainer.triggerTargetBeansCreation();
    }
}
