package com.dynamic.model.context;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class BlendModelContextHolder {

    private static final ThreadLocal<String> blendModelContextHolder = new NamedThreadLocal<>("blend-model-context");

    private static final ThreadLocal<String> inheritableBlendModelContextHolder = new NamedInheritableThreadLocal<>("inheritable-blend-model-context");

    public static void resetBlend() {
        blendModelContextHolder.remove();
        inheritableBlendModelContextHolder.remove();
    }

    public static boolean isReset() {
        return Objects.isNull(getBlend());
    }

    public static void setBlend(@Nullable String flag) {
        setBlend(flag, false);
    }

    public static void setBlend(@Nullable String flag, boolean inheritable) {
        if (!StringUtils.hasText(flag)) {
            resetBlend();
        } else if (inheritable) {
            inheritableBlendModelContextHolder.set(flag);
            blendModelContextHolder.remove();
        } else {
            blendModelContextHolder.set(flag);
            inheritableBlendModelContextHolder.remove();
        }

    }

    @Nullable
    public static String getBlend() {
        String blend = blendModelContextHolder.get();
        if (!StringUtils.hasText(blend)) {
            blend = inheritableBlendModelContextHolder.get();
        }
        return blend;
    }

}
