package com.snowflake.cache.factory;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheLoader;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.support.JacksonKeyConvertor;
import com.alicp.jetcache.template.QuickConfig;
import com.snowflake.cache.support.Jackson2ValueDecoder;
import com.snowflake.cache.support.Jackson2ValueEncoder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class CacheFactory {

    private final CacheManager cacheManager;

    public CacheFactory(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <K, V> Cache<K, V> buildWithLoader(String name, Duration remoteTtl, Duration localTtl, CacheLoader<K, V> loader) {
        Objects.requireNonNull(loader);
        RefreshPolicy refreshPolicy = RefreshPolicy.newPolicy(30, TimeUnit.MINUTES).stopRefreshAfterLastAccess(12, TimeUnit.HOURS);
        return cacheManager.getOrCreateCache(defaultBuilder(name).
                expire(remoteTtl).
                localExpire(localTtl).
                loader(loader).
                refreshPolicy(refreshPolicy).build()
        );
    }

    public <K, V> Cache<K, V> buildDefaultExpireWithLoader(String name, CacheLoader<K, V> loader) {
        Objects.requireNonNull(loader);
        RefreshPolicy refreshPolicy = RefreshPolicy.newPolicy(30, TimeUnit.MINUTES).stopRefreshAfterLastAccess(12, TimeUnit.HOURS);
        return cacheManager.getOrCreateCache(defaultBuilder(name).
                expire(Duration.ofMinutes(400)).
                localExpire(Duration.ofSeconds(30)).
                loader(loader).
                refreshPolicy(refreshPolicy).build()
        );
    }

    public <K, V> Cache<K, V> buildWithoutExpire(String name) {
        return cacheManager.getOrCreateCache(defaultBuilder(name).build());
    }


    /**
     * 构建永不过期的缓存对象
     * @param name 缓存名称
     * @param loader 加载器
     * @return 缓存对象
     * @param <K> 加载器k
     * @param <V> 加载器V
     */
    public <K, V> Cache<K, V> buildWithoutExpire(String name, CacheLoader<K, V> loader) {
        Objects.requireNonNull(loader);
        return cacheManager.getOrCreateCache(defaultBuilder(name).loader(loader).build());
    }

    /**
     * 默认快捷配置builder
     * @param name
     * @return
     */
    private QuickConfig.Builder defaultBuilder(String name) {
        return QuickConfig.
                newBuilder(name).
                localLimit(10000).
                cacheNullValue(false).
                keyConvertor(new JacksonKeyConvertor()).
                valueEncoder(Jackson2ValueEncoder.INSTANCE).
                valueDecoder(Jackson2ValueDecoder.INSTANCE).
                syncLocal(true).
                cacheType(CacheType.BOTH);
    }
}
