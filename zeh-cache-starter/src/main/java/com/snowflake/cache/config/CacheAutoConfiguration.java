package com.snowflake.cache.config;//package com.snowflake.proxy.cache.config;

import com.alicp.jetcache.Cache;
import com.snowflake.cache.factory.CacheFactory;
import com.snowflake.cache.operator.AllTypeDimensionsCacheOperator;
import com.snowflake.cache.operator.SingleIssueLimitCacheOperator;
import com.snowflake.cache.view.construction.Tuple3CacheView;
import com.snowflake.cache.view.play.AllPlayView;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@AutoConfigureAfter(CacheInstanceAutoConfiguration.class)
public class CacheAutoConfiguration {


    /**
     * 注册限额缓存操作器
     * @param allPlayersSingleIssueLimitModeByPlayTypeCache
     * @param allPlayViewCache
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"allPlayersSingleIssueLimitModeByPlayTypeCache","allPlayViewCache"})
    public SingleIssueLimitCacheOperator singleIssueLimitCacheOperator(Cache<String, Tuple3CacheView> allPlayersSingleIssueLimitModeByPlayTypeCache,
                                                                       Cache<String, AllPlayView> allPlayViewCache) {
        return new SingleIssueLimitCacheOperator(allPlayersSingleIssueLimitModeByPlayTypeCache, allPlayViewCache);
    }

    /**
     * 注册玩法缓存操作器
     * @param allPlayViewCache
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"allPlayViewCache"})
    public AllTypeDimensionsCacheOperator allTypeDimensionsCacheOperator(Cache<String, AllPlayView> allPlayViewCache){
        return new AllTypeDimensionsCacheOperator(allPlayViewCache);
    }

}
