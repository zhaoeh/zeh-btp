package com.snowflake.cache.view.construction;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 节点维度的缓存结构外层视图
 *
 * 本包中，主要封装了彩票代理系统中，针对玩法缓存视图的统一抽象结构
 *
 * 1.一个代理节点，作为一个key，维护一套结构，即当前类所描述的结构：有一个版本号，表示当前结构的版本；有一个内部map结构
 * 2.一个内部结构，是三元组组合key的分组结构(商户id+lotteryCode+playType)，key是三元组的组合key对应的映射编码；value是三元组对应的结构
 * 3.三元组中，每个三元组key对应的结构是一个map，其中key是五元组映射编码（最细粒度的唯一编码），value是具体的五元组对应的数据实例
 *
 */
@Data
public class NodeDimensionCacheView implements Serializable {

    /**
     * 单个节点维度的版本号，即某个代理节点的所有玩家限额配置对应的版本号
     */
    private String version;

    /**
     * 每个代理节点维度，对应的缓存值结构
     * key：该节点内部根据三元组分组后，三元组映射的编码key
     * value：三元组对应的结构
     */
    private Map<String, Tuple3CacheView> values;

}
