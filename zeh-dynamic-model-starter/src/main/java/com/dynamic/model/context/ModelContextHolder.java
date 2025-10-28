package com.dynamic.model.context;

import com.dynamic.model.enums.BizModel;
import org.springframework.core.NamedThreadLocal;

/**
 * 多模式上下文持有器
 */
public class ModelContextHolder {

    private static final ThreadLocal<BizModel> CONTEXT = new NamedThreadLocal<>("request-biz-model");

    private static final ThreadLocal<Boolean> IGNORE = new NamedThreadLocal<>("ignore-model");

    public static void set(BizModel model) {
        CONTEXT.set(model);
    }

    public static BizModel get() {
        return CONTEXT.get();
    }

    /**
     * 设置是否忽略请求模式路由
     * @param ignore 是否忽略 true：忽略 false：不忽略
     */
    public static void setIgnore(Boolean ignore) {
        IGNORE.set(ignore);
    }

    /**
     * 获取是否忽略请求路由标记
     * @return true：忽略请求模式路由 false：不忽略
     */
    public static boolean isIgnore() {
        return Boolean.TRUE.equals(IGNORE.get());
    }

    public static void clear() {
        CONTEXT.remove();
        IGNORE.remove();
    }
}
