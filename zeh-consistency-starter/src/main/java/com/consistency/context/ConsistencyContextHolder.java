package com.consistency.context;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * 一致性上下文
 */
public class ConsistencyContextHolder {

    private static final ThreadLocal<ContextBean> contextBeanHolder = new NamedThreadLocal<>("contextBean");

    private static final ThreadLocal<ContextBean> inheritableContextBeanHolder = new NamedInheritableThreadLocal<>("inheritable-contextBean");

    public static void clean() {
        contextBeanHolder.remove();
        inheritableContextBeanHolder.remove();
    }

    public static void setContextBean(@Nullable ContextBean bean, boolean inheritable) {
        if (Objects.isNull(bean)) {
            clean();
        } else if (inheritable) {
            inheritableContextBeanHolder.set(bean);
            contextBeanHolder.remove();
        } else {
            contextBeanHolder.set(bean);
            inheritableContextBeanHolder.remove();
        }
    }

    @Nullable
    public static ContextBean getContextBean() {
        ContextBean contextBean = contextBeanHolder.get();
        if (Objects.isNull(contextBean)) {
            contextBean = inheritableContextBeanHolder.get();
        }
        return contextBean;
    }


}
