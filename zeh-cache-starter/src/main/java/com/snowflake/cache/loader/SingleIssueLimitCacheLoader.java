package com.snowflake.cache.loader;

import com.snowflake.cache.key.type.Tuple3OfSite;
import com.snowflake.cache.view.construction.Tuple3CacheView;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
@FunctionalInterface
public interface SingleIssueLimitCacheLoader {

    /**
     * 当未命中缓存时，指定缓存数据加载器
     * @param tuple3OfSite 目标节点与三元组key组合的缓存key
     * @return 根据key提供的源数据
     */
    Tuple3CacheView loadCache(Tuple3OfSite tuple3OfSite);

    default Tuple3CacheView load(String key) {
        if (StringUtils.isBlank(key)) {
            return new Tuple3CacheView();
        }
        String[] keys = key.split(":");
        if (keys.length < 4) {
            throw new IllegalArgumentException("AllPlayersSingleIssueLimitCacheLoader 回调key错误");
        }
        Tuple3OfSite tuple3OfSite = new Tuple3OfSite(keys[0], keys[1], keys[2], keys[3]);
        return loadCache(tuple3OfSite);
    }
}
