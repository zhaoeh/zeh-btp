package zeh.btp.cache.caffeine;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import zeh.btp.common.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 使用Caffeine作为本地缓存
 *
 * @description: 本地缓存配置
 * @author: Erhu.Zhao
 * @create: 2023-10-17 16:45
 **/
@Configuration
@Slf4j
public class CaffeineCacheConfig {

    /**
     * 本地缓存加载器容器
     */
    private final Map<String, CaffeineCacheLoader> localCacheLoaderMap = new HashMap<>();

    /**
     * 宽松注入map of CaffeineCacheLoader
     *
     * @param localCacheLoaderProvider 交给容器管理的 CaffeineCacheLoader instances集合
     */
    public CaffeineCacheConfig(ObjectProvider<Map<String, CaffeineCacheLoader>> localCacheLoaderProvider) {
        Map<String, CaffeineCacheLoader> loaderMap = localCacheLoaderProvider.getIfAvailable();
        if (!CollectionUtils.isEmpty(loaderMap)) {
            loaderMap.forEach((k, v) -> this.localCacheLoaderMap.put(v.getDataCategory(), v));
        }
    }

    /**
     * 注册缓存管理器
     *
     * @return CacheManager 是spring中对缓存管理的高度抽象接口，由具体的三方缓存提供实现
     */
    @Bean
    public CacheManager localCacheManager() {
        // 创建一个Caffeine实现的缓存管理器
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setAllowNullValues(true);
        cacheManager.setCacheLoader(buildCacheLoader());
        // 注册caffeine实例
        cacheManager.setCaffeine(buildCaffeineCache());
        // 创建缓存实例
        Cache caffeineCache = this.createCaffeineCache(cacheManager);
        this.initLocalCacheData(caffeineCache);
        CaffeineCacheHelper.setCacheManager(cacheManager);
        return cacheManager;
    }

    /**
     * 创建cacheLoader实例
     *
     * @return cacheLoader实例
     */
    @Bean
    public CacheLoader<Object, Object> buildCacheLoader() {
        return this::getValueSupplier;
    }

    /**
     * 构建 caffeine 实例，用于构建caffeine cache实例
     *
     * @return caffeine instance
     */
    private Caffeine<Object, Object> buildCaffeineCache() {
        return Caffeine.newBuilder().initialCapacity(100).maximumSize(200000L).recordStats();
    }

    /**
     * 创建caffeine cache实例
     *
     * @param cacheManager caffeine缓存管理器
     */
    private Cache createCaffeineCache(CaffeineCacheManager cacheManager) {
        log.info("create caffeine cache begin.");
        // 创建名称为"DEFAULT"的Caffeine cache实例
        Cache cache = cacheManager.getCache(CommonConstants.CAFFEINE_CACHE_NAME);
        Objects.requireNonNull(cache, "Default caffeine cache can't be null");
        log.info("create caffeine cache end.");
        return cache;
    }

    /**
     * 遍历初始化本地缓存，当缓存不存在时，使用CacheLoader动态刷新
     *
     * @param caffeineCache caffeineCache
     */
    private void initLocalCacheData(Cache caffeineCache) {
        this.localCacheLoaderMap.keySet().forEach((key) -> {
            log.info("初始化本地caffeine缓存{}:", key);
            caffeineCache.get(key);
        });
    }

    /**
     * 根据bean名称获取LocalCacheLoader instance
     *
     * @param dataCategory 数据类别
     * @return LocalCacheLoader实例
     */
    private CaffeineCacheLoader getLocalCacheLoader(Object dataCategory) {
        CaffeineCacheLoader caffeineCacheLoader = localCacheLoaderMap.get(dataCategory);
        if (Objects.isNull(caffeineCacheLoader)) {
            throw new RuntimeException("根据key值" + dataCategory + "找不到对应的LocalCacheLoader");
        }
        return caffeineCacheLoader;
    }

    /**
     * 根据类别获取缓存数据供应者
     *
     * @param dataCategory 类别
     * @return 缓存数据供应者
     */
    private Object getValueSupplier(Object dataCategory) {
        CaffeineCacheLoader caffeineCacheLoader = getLocalCacheLoader(dataCategory);
        return caffeineCacheLoader.getValueSupplier().get();
    }
}
