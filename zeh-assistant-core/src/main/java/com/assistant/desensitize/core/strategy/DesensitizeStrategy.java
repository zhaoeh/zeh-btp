package com.assistant.desensitize.core.strategy;

/**
 * 脱敏策略
 */
@FunctionalInterface
public interface DesensitizeStrategy {

    /**
     * 对原始String内容应用脱敏规则
     * @param origin 原始字段
     * @return 应用脱敏规则后的字段
     */
    String apply(String origin);
}


