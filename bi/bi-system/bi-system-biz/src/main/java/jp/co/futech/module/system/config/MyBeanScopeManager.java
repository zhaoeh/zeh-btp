package jp.co.futech.module.system.config;

import ft.btp.scope.builder.IocBeanBuilder;
import ft.btp.scope.hook.BeanScopeManager;
import org.springframework.stereotype.Component;

/**
 * @description: 测试自定义scope
 * @author: ErHu.Zhao
 * @create: 2024-08-23
 **/
@Component
public class MyBeanScopeManager implements BeanScopeManager {
    @Override
    public Object get(String name, IocBeanBuilder iocBeanBuilder) {
        return iocBeanBuilder.buildBean();
    }

    @Override
    public Object remove(String name) {
        return null;
    }
}
