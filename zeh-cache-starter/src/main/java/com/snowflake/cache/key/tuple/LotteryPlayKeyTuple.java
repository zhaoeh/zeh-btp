package com.snowflake.cache.key.tuple;

import lombok.Data;

/**
 * 彩票和代理系统，表示一个最细粒度玩法的五元组key对象
 */
@Data
public final class LotteryPlayKeyTuple {
    private String id;
    private String merchantId;
    private String lotteryCode;
    private String lotteryName;
    private String playTypeCode;
    private String playTypeName;
    private String playCode;
    private String playName;
    private String handicapCode;
}
