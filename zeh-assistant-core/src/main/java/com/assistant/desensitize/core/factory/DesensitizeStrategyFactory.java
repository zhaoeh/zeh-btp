package com.assistant.desensitize.core.factory;

import com.assistant.desensitize.core.strategy.DesensitizeStrategy;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesensitizeStrategyFactory {

    private static final Map<Class<? extends DesensitizeStrategy>, DesensitizeStrategy> CLASS_STRATEGY_MAP = new HashMap<>();

    public DesensitizeStrategyFactory() {
    }

    /**
     * 注册容器中所有的策略实现
     * @param strategies 所有策略实现实例集合
     */
    public DesensitizeStrategyFactory(List<DesensitizeStrategy> strategies) {
        if (CollectionUtils.isEmpty(strategies)) {
            return;
        }
        strategies.forEach(strategy -> CLASS_STRATEGY_MAP.put(strategy.getClass(), strategy));
    }

    public static DesensitizeStrategy getByClass(Class<? extends DesensitizeStrategy> clazz) {
        return CLASS_STRATEGY_MAP.get(clazz);
    }

}
