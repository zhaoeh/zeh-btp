package com.assistant.desensitize.core;

/**
 * 脱敏上下文
 */
public class DesensitizeContext {

    private static final ThreadLocal<Boolean> skipDesensitize = new ThreadLocal<>();

    public static void setSkip(boolean skip) {
        skipDesensitize.set(skip);
    }

    public static boolean shouldSkip() {
        return Boolean.TRUE.equals(skipDesensitize.get());
    }

    public static void clear() {
        skipDesensitize.remove();
    }
}
