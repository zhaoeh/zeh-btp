package com.assistant.desensitize.core.strategy.impl;

import com.assistant.desensitize.core.DesensitizeContext;
import com.assistant.desensitize.core.strategy.DesensitizeStrategy;

public class MobileStrategy implements DesensitizeStrategy {
    @Override
    public String apply(String origin) {
        if (DesensitizeContext.shouldSkip()) {
            return origin;
        }
        return manualApply(origin);
    }

    @Override
    public String manualApply(String origin) {
        if (origin == null || origin.length() < 7) return origin;
        return origin.substring(0, 3) + "****" + origin.substring(origin.length() - 4);
    }

}