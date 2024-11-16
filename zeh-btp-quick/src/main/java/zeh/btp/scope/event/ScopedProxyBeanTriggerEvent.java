package zeh.btp.scope.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * @description: 自定义事件
 * @author: ErHu.Zhao
 * @create: 2024-08-26
 **/
public class ScopedProxyBeanTriggerEvent extends ApplicationContextEvent {

    public ScopedProxyBeanTriggerEvent(ApplicationContext source) {
        super(source);
    }
}
