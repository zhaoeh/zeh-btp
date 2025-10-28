package com.snowflake.cache.operator;

import com.alicp.jetcache.Cache;
import com.snowflake.cache.key.tuple.LotteryPlayKeyTuple;
import com.snowflake.cache.view.play.AllPlayView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 获取全量玩法五元组key对应的配置编码映射
     * @return
     */
    public Map<String, String> queryAllLotteryTypeMappingWithTuple5() {
        AllPlayView allPlayView = queryAllLotteryType();
        if (Objects.isNull(allPlayView)) {
            return Collections.emptyMap();
        }
        Map<String, LotteryPlayKeyTuple> tuple5ConfigMapping = allPlayView.getBase();
        if (CollectionUtils.isEmpty(tuple5ConfigMapping)) {
            return Collections.emptyMap();
        }
        Map<String, String> result = new HashMap<>();
        tuple5ConfigMapping.forEach((k, v) -> {
            if (StringUtils.isNotBlank(k) && Objects.nonNull(v) && StringUtils.isNotBlank(v.getId())) {
                result.put(k, v.getId());
            }
        });
        return result;
    }
}
