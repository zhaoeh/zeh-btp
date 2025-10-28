package com.assistant.desensitize.core.strategy;

/**
 * 脱敏策略
 */
public interface DesensitizeStrategy {

    /**
     * 对原始String内容应用脱敏规则
     * @param origin 原始字段
     * @return 应用脱敏规则后的字段
     */
    String apply(String origin);

    /**
     * 手动应用脱敏策略
     * @param origin 原始值
     * @return
     */
    default String manualApply(String origin) {
        return origin;
    }
}


