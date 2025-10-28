package com.snowflake.cache.view.construction;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 限额内部三元组维度结构：某个代理玩法维度的限额配置结构，该结构是内部分组视图，将属于某类彩种的某类玩法的配置列表进行内部视图分组
 */
@Data
public class Tuple3CacheView implements Serializable {

    /**
     * 某个代理节点内部三元组（商户id+lotteryCode+playType）维度，即某个代理的某个大玩法维度的限额配置版本号
     *
     */
    private String version;

    /**
     * 每个代理节点内部三元组维度，对应的缓存值结构
     * key：该节点内部三元组分组后，每个三元组对应的map结构，key是五元组映射后的编码key
     * value：五元组key对应的实例结构-最细粒度，唯一
     */
    private Map<String, Tuple5CacheView> values;

}
