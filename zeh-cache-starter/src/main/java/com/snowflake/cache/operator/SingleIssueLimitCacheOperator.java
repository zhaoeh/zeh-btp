package com.snowflake.cache.operator;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheLoader;
import com.snowflake.cache.key.tuple.KeyTupleDimension;
import com.snowflake.cache.key.tuple.LotteryPlayKeyTuple;
import com.snowflake.cache.key.tuple.LotteryPlayKeyTupleHelper;
import com.snowflake.cache.key.tuple.LotteryTuple;
import com.snowflake.cache.view.construction.Tuple3CacheView;
import com.snowflake.cache.view.construction.Tuple5CacheView;
import com.snowflake.cache.view.limit.AllPlayersSingleIssueLimitFiveCacheView;
import com.snowflake.cache.view.play.AllPlayView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class SingleIssueLimitCacheOperator {

    private final Cache<String, Tuple3CacheView> allPlayersSingleIssueLimitModeByPlayTypeCache;

    Cache<String, AllPlayView> allPlayViewCache;

    public SingleIssueLimitCacheOperator(Cache<String, Tuple3CacheView> allPlayersSingleIssueLimitModeByPlayTypeCache,
                                         Cache<String, AllPlayView> allPlayViewCache) {
        this.allPlayersSingleIssueLimitModeByPlayTypeCache = allPlayersSingleIssueLimitModeByPlayTypeCache;
        this.allPlayViewCache = allPlayViewCache;
    }

    /**
     * 查询站点所有玩家累计限额缓存（三元组粒度）
     * @param site
     * @param tuple3
     * @return
     */
    public Tuple3CacheView queryAllPlayersSingleIssueLimitView3(String site, LotteryTuple.Tuple3 tuple3) {
        Assert.isTrue(StringUtils.isNotBlank(site), "site不能为空");
        Objects.requireNonNull(tuple3);
        // 三元组拼接的原始key
        LotteryPlayKeyTuple queryParam = LotteryPlayKeyTupleHelper.sortedTuple(tuple3.getMerchantId(), tuple3.getLotteryCode(), tuple3.getPlayTypeCode());
        String dimensionKey = LotteryPlayKeyTupleHelper.toKey(queryParam, KeyTupleDimension.SORTED_TUPLE3.getIndices());
        dimensionKey = convertTupleKey(KeyTupleDimension.SORTED_TUPLE3, dimensionKey);
        // 拼接节点id作为前缀，构建单个节点三元组维度的缓存key
        String key = LotteryPlayKeyTupleHelper.joinKeys(site, dimensionKey);
        Tuple3CacheView result = this.allPlayersSingleIssueLimitModeByPlayTypeCache.get(key);
        if (Objects.isNull(result)) {
            result = reLoadAllPlayersSingleIssueLimitView(key);
        }

        return result;
    }


    /**
     * 查询站点所有玩家累计限额缓存（五元组粒度）
     * @param site
     * @param tuple5
     * @return
     */
    public AllPlayersSingleIssueLimitFiveCacheView queryAllPlayersSingleIssueLimitView5(String site, LotteryTuple.Tuple5 tuple5) {
        Assert.isTrue(StringUtils.isNotBlank(site), "site不能为空");
        Objects.requireNonNull(tuple5);
        boolean reLoad = false;
        // 三元组拼接的原始key
        LotteryPlayKeyTuple queryParam = LotteryPlayKeyTupleHelper.sortedTuple(tuple5.getMerchantId(), tuple5.getLotteryCode(),
                tuple5.getPlayTypeCode(), tuple5.getPlayCode(), tuple5.getHandicapCode());
        String tuple3Key = LotteryPlayKeyTupleHelper.toKey(queryParam, KeyTupleDimension.SORTED_TUPLE3.getIndices());
        tuple3Key = convertTupleKey(KeyTupleDimension.SORTED_TUPLE3, tuple3Key);
        // 拼接节点id作为前缀，构建单个节点三元组维度的缓存key
        String key = LotteryPlayKeyTupleHelper.joinKeys(site, tuple3Key);
        Tuple3CacheView result = this.allPlayersSingleIssueLimitModeByPlayTypeCache.get(key);
        if (Objects.isNull(result)) {
            result = reLoadAllPlayersSingleIssueLimitView(key);
            reLoad = true;
        }
        if (Objects.nonNull(result)) {
            Map<String, Tuple5CacheView> tuple5Values = result.getValues();
            if (CollectionUtils.isEmpty(tuple5Values)) {
                if (reLoad) {
                    return null;
                }
                result = reLoadAllPlayersSingleIssueLimitView(key);
                reLoad = true;
                if (Objects.isNull(result)) {
                    return null;
                }
                tuple5Values = result.getValues();
                if (CollectionUtils.isEmpty(tuple5Values)) {
                    return null;
                }
            }
            // 五元组key
            String dimensionKey = LotteryPlayKeyTupleHelper.toKey(queryParam, KeyTupleDimension.SORTED_TUPLE5.getIndices());
            Tuple5CacheView tuple5CacheView = tuple5Values.get(dimensionKey);
            if (Objects.isNull(tuple5CacheView)) {
                if (reLoad) {
                    return null;
                }
                result = reLoadAllPlayersSingleIssueLimitView(key);
                if (Objects.isNull(result)) {
                    return null;
                }
                tuple5Values = result.getValues();
                if (CollectionUtils.isEmpty(tuple5Values)) {
                    return null;
                }
                tuple5CacheView = tuple5Values.get(dimensionKey);
                if (Objects.isNull(tuple5CacheView)) {
                    return null;
                }
            }
            return (AllPlayersSingleIssueLimitFiveCacheView) tuple5CacheView;
        }
        return null;
    }


    /**
     * 重新从源加载数据
     * @param key
     * @return
     */
    private Tuple3CacheView reLoadAllPlayersSingleIssueLimitView(String key) {
        Tuple3CacheView result = null;
        CacheLoader<String, Tuple3CacheView> loader = allPlayersSingleIssueLimitModeByPlayTypeCache.config().getLoader();
        if (Objects.nonNull(loader)) {
            try {
                result = loader.load(key);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.nonNull(result)) {
            // 回填
            this.allPlayersSingleIssueLimitModeByPlayTypeCache.put(key, result);
        }
        return result;
    }

    public void cacheAllPlayersSingleIssueLimitView3(String site, LotteryTuple.Tuple3 tuple3, Supplier<Tuple3CacheView> dataSupplier) {
        Assert.isTrue(StringUtils.isNotBlank(site), "site不能为空");
        Objects.requireNonNull(tuple3);
        Objects.requireNonNull(dataSupplier);
        Tuple3CacheView data = dataSupplier.get();
        if (Objects.isNull(data)) {
            return;
        }
        // 三元组拼接的原始key
        LotteryPlayKeyTuple queryParam = LotteryPlayKeyTupleHelper.sortedTuple(tuple3.getMerchantId(), tuple3.getLotteryCode(), tuple3.getPlayTypeCode());
        String dimensionKey = LotteryPlayKeyTupleHelper.toKey(queryParam, KeyTupleDimension.SORTED_TUPLE3.getIndices());
        dimensionKey = convertTupleKey(KeyTupleDimension.SORTED_TUPLE3, dimensionKey);
        // 拼接节点id作为前缀，构建单个节点三元组维度的缓存key
        String key = LotteryPlayKeyTupleHelper.joinKeys(site, dimensionKey);
        this.allPlayersSingleIssueLimitModeByPlayTypeCache.put(key, data);
    }

    /**
     * 从层级映射key中心，转换原始的元组key为对应的映射编号
     * @param tupleDimension  元组类型
     * @param key 原始元组组合key
     * @return 其在层级映射中心中对应的唯一编号
     */
    private String convertTupleKey(KeyTupleDimension tupleDimension, String key) {
        return queryDimensionsKey(tupleDimension, key);
    }

    /**
     * 根据原始维度key，从映射中心获取其对应的唯一key编号
     * @param tupleDimension 元组类型
     * @param originalKey 原始维度key
     * @return 原始维度key对应的唯一编号，此映射目的是压缩原始key存储内存占用
     */
    public String queryDimensionsKey(KeyTupleDimension tupleDimension, String originalKey) {
        Objects.requireNonNull(tupleDimension);
        // 获取代理平台全量玩法维度
        AllPlayView all = allPlayViewCache.get("DIMENSIONS");
        if (Objects.isNull(all)) {
            try {
                // 重新load
                all = allPlayViewCache.config().getLoader().load("DIMENSIONS");
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.isNull(all)) {
            throw new IllegalStateException("缓存key[ALL_PLAY_DATA:" + originalKey + "]不存在数据，请检查");
        }

        if (Objects.equals(KeyTupleDimension.SORTED_TUPLE5, tupleDimension)) {
            Map<String, LotteryPlayKeyTuple> tupleMap = all.getBase();
            if (CollectionUtils.isEmpty(tupleMap)) {
                throw new IllegalStateException("缓存key[ALL_PLAY_DATA:" + originalKey + "]不存在五元组映射数据，请检查");
            }
            LotteryPlayKeyTuple tuple = tupleMap.get(originalKey);
            if (Objects.isNull(tuple)) {
                throw new IllegalArgumentException("缓存key[ALL_PLAY_DATA:" + originalKey + "]不存在五元组数据，请检查传入Key是否正确");
            }
            String tuple5UniqueKey = tuple.getId();
            if (StringUtils.isBlank(tuple5UniqueKey)) {
                throw new IllegalStateException("缓存key[ALL_PLAY_DATA:" + originalKey + "]五元组映射配置编号为空，请检查");
            }
            return tuple5UniqueKey;
        }

        if (Objects.equals(KeyTupleDimension.SORTED_TUPLE3, tupleDimension)) {
            Map<String, String> stringStringMap = all.getTupleMapping();
            if (CollectionUtils.isEmpty(stringStringMap)) {
                throw new IllegalStateException("缓存key[ALL_PLAY_DATA:" + originalKey + "]不存在三元组映射数据，请检查");
            }
            String tuple3UniqueKey = stringStringMap.get(originalKey);

            if (StringUtils.isBlank(tuple3UniqueKey)) {
                throw new IllegalStateException("缓存key[ALL_PLAY_DATA:" + originalKey + "]三元组映射配置编号为空，请检查");
            }
            return tuple3UniqueKey;
        }
        throw new IllegalArgumentException("KeyTupleDimension错误");
    }

}
