package com.snowflake.cache.loader;

import com.snowflake.cache.key.tuple.LotteryPlayKeyTuple;

import java.util.List;

@FunctionalInterface
public interface LotteryPlayKeyTupleCacheLoader {

    /**
     * 多层级基础维度缓存源供应器
     * @param isPreCheck
     * @return
     */
    List<LotteryPlayKeyTuple> loadCache(Boolean isPreCheck);
}
