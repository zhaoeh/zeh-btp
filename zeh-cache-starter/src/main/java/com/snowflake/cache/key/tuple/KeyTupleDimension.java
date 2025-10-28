package com.snowflake.cache.key.tuple;

import lombok.Getter;

@Getter
public enum KeyTupleDimension {

    // 按照粒度树按需组合的三元组维度
    SORTED_TUPLE3(new KeyTupleIndex[]{KeyTupleIndex.MERCHANT_ID, KeyTupleIndex.LOTTERY_CODE, KeyTupleIndex.PLAY_TYPE_CODE}),

    // 全量的五元组维度
    SORTED_TUPLE5(KeyTupleIndex.values()),
    ;

    private final KeyTupleIndex[] indices;

    KeyTupleDimension(KeyTupleIndex[] indices) {
        this.indices = indices;
    }

}
