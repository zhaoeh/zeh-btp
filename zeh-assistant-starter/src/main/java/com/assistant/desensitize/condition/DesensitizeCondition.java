package com.assistant.desensitize.condition;

/**
 * 脱敏开关接口：是否跳过response body序列化脱敏，true：跳过-不脱敏 false：不跳过-脱敏
 */
@FunctionalInterface
public interface DesensitizeCondition {

    /**
     * 是否应该跳过脱敏
     * @return true：跳过脱敏 false：不跳过脱敏
     */
    boolean shouldSkip();
}
