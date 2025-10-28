package com.snowflake.cache.config;

import com.alicp.jetcache.Cache;
import com.snowflake.cache.factory.CacheFactory;
import com.snowflake.cache.key.tuple.HierarchicalCodeBook;
import com.snowflake.cache.key.tuple.LotteryPlayKeyTuple;
import com.snowflake.cache.loader.LotteryPlayKeyTupleCacheLoader;
import com.snowflake.cache.loader.SingleIssueLimitCacheLoader;
import com.snowflake.cache.view.construction.NodeDimensionCacheView;
import com.snowflake.cache.view.construction.Tuple3CacheView;
import com.snowflake.cache.view.play.AllPlayView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.List;

/**
 * 二级双缓存实例自动注册
 */
@AutoConfiguration
@AutoConfigureAfter(CacheComponentAutoConfiguration.class)
public class CacheInstanceAutoConfiguration {

    @Bean
    @ConditionalOnBean(SingleIssueLimitCacheLoader.class)
    public Cache<String, Tuple3CacheView> allPlayersSingleIssueLimitModeByPlayTypeCache(CacheFactory factory, SingleIssueLimitCacheLoader loader) {
        return factory.buildWithLoader("ALL_PLAYER_LIMIT:", Duration.ofMinutes(24 * 60), Duration.ofMinutes(60), loader::load);
    }

    @Bean
    @ConditionalOnBean(CacheFactory.class)
    public Cache<String, NodeDimensionCacheView> allPlayersSingleIssueLimitModeByPlayTypeVersionCache(CacheFactory factory) {
        return factory.buildWithoutExpire("ALL_PLAYER_LIMIT_VERSION:");
    }

    @Bean
    @ConditionalOnBean(LotteryPlayKeyTupleCacheLoader.class)
    public Cache<String, AllPlayView> allPlayViewCache(CacheFactory factory, LotteryPlayKeyTupleCacheLoader loader) {
        return factory.buildWithoutExpire("ALL_PLAY_DATA:", k -> {
            List<LotteryPlayKeyTuple> data = StringUtils.equalsIgnoreCase("true", k) ? loader.loadCache(true) : loader.loadCache(false);
            if (!CollectionUtils.isEmpty(data)) {
                return HierarchicalCodeBook.build(data);
            }
            return null;
        });
    }

}
