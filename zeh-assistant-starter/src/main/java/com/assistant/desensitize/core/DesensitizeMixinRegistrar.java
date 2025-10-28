package com.assistant.desensitize.core;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于jackson的脱敏注册表
 */
public class DesensitizeMixinRegistrar {

    private static final Map<Class<?>, Class<?>> REGISTRY = new ConcurrentHashMap<>();

    /**
     * 注册 MixIn
     * @param target 原始类
     * @param mixIn  MixIn 类
     */
    public static synchronized void register(Class<?> target, Class<?> mixIn) {
        Class<?> existing = REGISTRY.get(target);
        if (existing != null && !existing.equals(mixIn)) {
            throw new IllegalStateException(String.format(
                    "重复注册 MixIn: %s 已绑定 %s，不能再绑定 %s",
                    target.getName(), existing.getName(), mixIn.getName()
            ));
        }
        REGISTRY.put(target, mixIn);
    }

    /**
     * 获取只读映射表
     * @return
     */
    public static Map<Class<?>, Class<?>> getRegistry() {
        return Collections.unmodifiableMap(REGISTRY);
    }
}
