package com.snowflake.cache.operator;

import com.alicp.jetcache.Cache;
import com.snowflake.cache.view.play.AllPlayView;

public class AllTypeDimensionsCacheOperator {

    private final Cache<String, AllPlayView> allPlayViewCache;

    public AllTypeDimensionsCacheOperator(Cache<String, AllPlayView> allPlayViewCache) {
        this.allPlayViewCache = allPlayViewCache;
    }

    /**
     * 缓存全平台全量玩法维度映射信息
     * @param isPreCheck 缓存时是否执行预检 true：是 false：否 预检逻辑由业务侧提供
     * @return 全量玩法维度实例
     */
    public AllPlayView cacheAllLotteryType(boolean isPreCheck) {
        AllPlayView result;
        try {
            result = isPreCheck ? allPlayViewCache.config().getLoader().load("true") : allPlayViewCache.config().getLoader().load("false");
            allPlayViewCache.put("DIMENSIONS", result);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 查询全量彩种玩法
     * @return
     */
    public AllPlayView queryAllLotteryType() {
        return allPlayViewCache.get("DIMENSIONS");
    }
}
