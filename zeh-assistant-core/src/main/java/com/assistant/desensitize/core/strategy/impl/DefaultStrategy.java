package com.assistant.desensitize.core.strategy.impl;

import com.assistant.desensitize.core.strategy.DesensitizeStrategy;

/**
 * 默认脱敏策略器
 */
public class DefaultStrategy implements DesensitizeStrategy {
    @Override
    public String apply(String origin) {
        // 默认不处理
        return origin;
    }
}