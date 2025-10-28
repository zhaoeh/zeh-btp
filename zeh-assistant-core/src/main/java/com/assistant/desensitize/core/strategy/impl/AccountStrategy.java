package com.assistant.desensitize.core.strategy.impl;

import com.assistant.desensitize.core.DesensitizeContext;
import com.assistant.desensitize.core.strategy.DesensitizeStrategy;
import org.apache.commons.lang3.StringUtils;

public class AccountStrategy implements DesensitizeStrategy {

    @Override
    public String apply(String origin) {
        if (DesensitizeContext.shouldSkip()) {
            return origin;
        }
        return manualApply(origin);
    }

    @Override
    public String manualApply(String origin) {
        if (StringUtils.isBlank(origin)) {
            return origin;
        }
        if (origin.length() == 1) {
            return origin;
        }
        if (origin.length() == 2) {
            return origin.replaceAll("(?<=^.)(.*)(?=.{1}$)", "***");
        }
        // 保留首字母 + "***" + 末尾两位
        return origin.replaceAll("(?<=^.)(.*)(?=.{2}$)", "***");
    }

}