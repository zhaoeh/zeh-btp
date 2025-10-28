package com.snowflake.cache.key.tuple;

import lombok.Getter;

/**
 * 五元组索引枚举
 */
@Getter
public enum KeyTupleIndex {

    MERCHANT_ID(1),
    LOTTERY_CODE(2),
    PLAY_TYPE_CODE(3),
    PLAY_CODE(4),
    HANDICAP_CODE(5);

    private final long code;

    KeyTupleIndex(long code) {
        this.code = code;
    }

}
