package com.assistant.desensitize.core.strategy.impl;

import com.assistant.desensitize.core.strategy.DesensitizeStrategy;

public class IdCardStrategy implements DesensitizeStrategy {

    @Override
    public String apply(String origin) {
        if (origin == null || origin.length() < 8) return origin;
        return origin.substring(0,4) + "************" + origin.substring(origin.length()-4);
    }

}