//package com.snowflake.cache.core;
//
//import com.alicp.jetcache.Cache;
//import com.snowflake.custom.cache.factory.CacheFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 版本号管理器
// */
//public class VersionedCacheManager {
//
//    private final CacheFactory cacheFactory;
//    private final RedisTemplate<String,Object> redisTemplate;
//
//    public VersionedCacheManager(CacheFactory cacheFactory, RedisTemplate<String,Object>  redisTemplate) {
//        this.cacheFactory = cacheFactory;
//        this.redisTemplate = redisTemplate;
//    }
//
//    private String versionKey(Long nodeId) {
//        return "version:node:" + nodeId;
//    }
//
//    /** 递增版本号（节点变更时调用） */
//    public void bumpVersion(Long nodeId) {
//        redisTemplate.opsForValue().increment(versionKey(nodeId));
//    }
//
//    /** 组合祖先链版本号 */
//    public String composeVersion(List<Long> nodeIds) {
//        List<String> versions = redisTemplate.opsForValue().multiGet(
//                nodeIds.stream().map(this::versionKey).collect(Collectors.toList())
//        );
//        return versions.stream().map(v -> v == null ? "0" : v).collect(Collectors.joining("-"));
//    }
//
//    /** 包装缓存 key（带版本号） */
//    public String buildCacheKey(String baseKey, List<Long> ancestors) {
//        String version = composeVersion(ancestors);
//        return baseKey + "::" + version;
//    }
//
//    /** 创建带版本感知的缓存 */
//    public <V> Cache<String, V> getVersionedCache(String name, long ttlSeconds, int localLimit) {
//        return cacheFactory.create(name, ttlSeconds, localLimit);
//    }
//}
