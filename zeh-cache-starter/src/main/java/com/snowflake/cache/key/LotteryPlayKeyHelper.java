package com.snowflake.cache.key;

import java.util.Objects;

/**
 * 各个缓存key构建工具类
 */
public class LotteryPlayKeyHelper {

    /**
     * 构建限额规则代理节点版本号缓存key
     * @param proxyId 代理id
     * @return 代理节点对应的版本号缓存key
     */
    public static String buildAllPlayersSingleIssueLimitNodeVersionKey(Long proxyId) {
        Objects.requireNonNull(proxyId, "proxyId 不能为null");
        return "NODE:VERSION:ALL:PLAY:LIMIT" + proxyId;
    }

//    public static String buildAllPlayersSingleIssueLimitKey(){
//
//    }

}
