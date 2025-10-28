package com.snowflake.cache.factory;//package com.snowflake.proxy.cache.factory;

import groovy.lang.Tuple3;
import org.apache.commons.lang3.StringUtils;

public class KVFactory {

    /**
     * 为代理+厂商id+彩种编码维度的赔率缓存 构建缓存key
     * @param merchantId
     * @param lotteryCode
     * @param path
     * @return
     */
    public static String buildAgentOddsCacheByLotteryKey(Long merchantId, String lotteryCode, String path) {
        return merchantId + "$" + lotteryCode + "$" + path;
    }

    /**
     * 为 代理+厂商id+彩种编码维度的赔率缓存 根据缓存key还原
     * @param key
     * @return
     */
    public static Tuple3<Long, String, Long> reductionAgentOddsCacheByLotteryKey(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String[] keys = key.split("\\$");
        if (keys.length != 3) {
            return null;
        }
        String path = keys[2];
        if (StringUtils.isBlank(path)) {
            return null;
        }
        String[] ancestors = path.split("/+");
        if (ancestors.length == 0) {
            return null;
        }
        String targetAgent = ancestors[0];
        return Tuple3.tuple(Long.valueOf(StringUtils.trim(keys[0])), keys[1], Long.valueOf(StringUtils.trim(targetAgent)));
    }
}
