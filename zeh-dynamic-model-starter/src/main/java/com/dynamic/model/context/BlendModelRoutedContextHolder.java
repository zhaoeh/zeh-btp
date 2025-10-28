package com.dynamic.model.context;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * 已经执行路由的上下文容器
 */
public class BlendModelRoutedContextHolder {

    private static final ThreadLocal<Boolean> blendModelRoutedContextHolder = new NamedThreadLocal<>("blend-model-context");

    private static final ThreadLocal<Boolean> inheritableBlendRoutedModelContextHolder = new NamedInheritableThreadLocal<>("inheritable-blend-model-context");

    public static void clear() {
        blendModelRoutedContextHolder.remove();
        inheritableBlendRoutedModelContextHolder.remove();
    }

    public static void setRouted(@Nullable Boolean flag) {
        setRouted(flag, false);
    }

    public static void setRouted(@Nullable Boolean flag, boolean inheritable) {
        if (Objects.isNull(flag)) {
            clear();
        } else if (inheritable) {
            inheritableBlendRoutedModelContextHolder.set(flag);
            blendModelRoutedContextHolder.remove();
        } else {
            blendModelRoutedContextHolder.set(flag);
            inheritableBlendRoutedModelContextHolder.remove();
        }
    }

    @Nullable
    public static Boolean isRouted() {
        Boolean isRouted = blendModelRoutedContextHolder.get();
        if (Objects.isNull(isRouted)) {
            isRouted = inheritableBlendRoutedModelContextHolder.get();
        }
        return isRouted;
    }

}
