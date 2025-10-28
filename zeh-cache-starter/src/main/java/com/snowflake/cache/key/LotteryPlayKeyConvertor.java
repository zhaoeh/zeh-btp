package com.snowflake.cache.key;

import com.alicp.jetcache.anno.KeyConvertor;

/**
 * 自定义玩法维度key转换器
 */
public class LotteryPlayKeyConvertor implements KeyConvertor {
    @Override
    public Object apply(Object key) {
        if (key instanceof LotteryPlayViewKey) {
            return ((LotteryPlayViewKey) key).buildKey();
        }
        return key.toString();
    }
}
