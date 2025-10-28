package com.snowflake.cache.config;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.SimpleCacheManager;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.support.*;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.external.ExternalCacheBuilder;
import com.alicp.jetcache.redisson.RedissonCacheBuilder;
import com.alicp.jetcache.support.JacksonKeyConvertor;
import com.alicp.jetcache.support.StatInfo;
import com.snowflake.cache.factory.CacheFactory;
import com.snowflake.cache.support.Jackson2ValueDecoder;
import com.snowflake.cache.support.Jackson2ValueEncoder;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@AutoConfiguration
@AutoConfigureBefore()
@ConditionalOnClass(CacheManager.class)
public class CacheComponentAutoConfiguration {

    @Bean("globalCacheConfig")
    public GlobalCacheConfig globalCacheConfig(@Qualifier("jacksonRedisson") RedissonClient redissonClient) {
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        // 自动刷新缓存策略
        RefreshPolicy refreshPolicy = RefreshPolicy.newPolicy(30, TimeUnit.MINUTES).stopRefreshAfterLastAccess(12, TimeUnit.HOURS);

        // 一级缓存：本地
        Map<String, CacheBuilder> localBuilders = new HashMap<>();
        CacheBuilder caffeineBuilder = CaffeineCacheBuilder.createCaffeineCacheBuilder().
                limit(50000).
                refreshPolicy(refreshPolicy);
        localBuilders.put(CacheConsts.DEFAULT_AREA, caffeineBuilder);

        // 二级缓存：redisson
        Map<String, CacheBuilder> remoteBuilders = new HashMap<>();
        ExternalCacheBuilder<?> redissonBuilder = RedissonCacheBuilder.createBuilder().
                redissonClient(redissonClient).
                valueEncoder(Jackson2ValueEncoder.INSTANCE).
                valueDecoder(Jackson2ValueDecoder.INSTANCE).
                keyConvertor(new JacksonKeyConvertor()).
                cacheNullValue(false).
                broadcastChannel("snowflake-channel").
                refreshPolicy(refreshPolicy);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, redissonBuilder);

        // 封装
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        globalCacheConfig.setStatIntervalMinutes(15);
        globalCacheConfig.setEnableMethodCache(false);
        return globalCacheConfig;
    }



    @Bean(
            destroyMethod = "shutdown"
    )
    public SpringConfigProvider springConfigProvider(@Autowired ApplicationContext applicationContext, @Autowired GlobalCacheConfig globalCacheConfig, @Autowired(required = false) EncoderParser encoderParser, @Autowired(required = false) KeyConvertorParser keyConvertorParser, @Autowired(required = false) Consumer<StatInfo> metricsCallback) {
        SpringConfigProvider cp = this.createConfigProvider();
        cp.setApplicationContext(applicationContext);
        cp.setGlobalCacheConfig(globalCacheConfig);
        if (encoderParser != null) {
            cp.setEncoderParser(encoderParser);
        }

        if (keyConvertorParser != null) {
            cp.setKeyConvertorParser(keyConvertorParser);
        }

        if (metricsCallback != null) {
            cp.setMetricsCallback(metricsCallback);
        }

        cp.init();
        return cp;
    }


    @Bean(
            name = {"jcCacheManager"},
            destroyMethod = "close"
    )
    @ConditionalOnMissingBean
    public SimpleCacheManager cacheManager(@Autowired SpringConfigProvider springConfigProvider) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCacheBuilderTemplate(springConfigProvider.getCacheBuilderTemplate());
        return cacheManager;
    }


    /**
     * 注册缓存工厂对象
     * @param cacheManager 缓存管理器
     * @return 缓存工厂
     */
    @Bean
    public CacheFactory cacheFactory(CacheManager cacheManager) {
        return new CacheFactory(cacheManager);
    }

    protected SpringConfigProvider createConfigProvider() {
        return new SpringConfigProvider();
    }

}
