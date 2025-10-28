package com.snowflake.cache.key.tuple;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LotteryPlayKeyTupleHelper {

    public static LotteryPlayKeyTuple sortedTuple(String merchantId) {
        Assert.isTrue(StringUtils.isNotBlank(merchantId), "merchantId 不能为空");
        LotteryPlayKeyTuple tuple = new LotteryPlayKeyTuple();
        tuple.setMerchantId(merchantId);
        return tuple;
    }

    public static LotteryPlayKeyTuple sortedTuple(String merchantId, String lotteryCode) {
        Assert.isTrue(StringUtils.isNotBlank(merchantId), "merchantId 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(lotteryCode), "lotteryCode 不能为空");
        LotteryPlayKeyTuple tuple = sortedTuple(merchantId);
        tuple.setLotteryCode(lotteryCode);
        return tuple;
    }

    public static LotteryPlayKeyTuple sortedTuple(String merchantId, String lotteryCode, String playTypeCode) {
        Assert.isTrue(StringUtils.isNotBlank(merchantId), "merchantId 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(lotteryCode), "lotteryCode 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(playTypeCode), "playTypeCode 不能为空");
        LotteryPlayKeyTuple tuple = sortedTuple(merchantId, lotteryCode);
        tuple.setPlayTypeCode(playTypeCode);
        return tuple;
    }

    public static LotteryPlayKeyTuple sortedTuple(String merchantId, String lotteryCode, String playTypeCode, String playCode) {
        Assert.isTrue(StringUtils.isNotBlank(merchantId), "merchantId 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(lotteryCode), "lotteryCode 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(playTypeCode), "playTypeCode 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(playCode), "playCode 不能为空");
        LotteryPlayKeyTuple tuple = sortedTuple(merchantId, lotteryCode, playTypeCode);
        tuple.setPlayCode(playCode);
        return tuple;
    }


    public static LotteryPlayKeyTuple sortedTuple(String merchantId, String lotteryCode, String playTypeCode, String playCode, String handicapCode) {
        Assert.isTrue(StringUtils.isNotBlank(merchantId), "merchantId 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(lotteryCode), "lotteryCode 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(playTypeCode), "playTypeCode 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(playCode), "playCode 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(handicapCode), "handicapCode 不能为空");
        LotteryPlayKeyTuple tuple = sortedTuple(merchantId, lotteryCode, playTypeCode, playCode);
        tuple.setHandicapCode(handicapCode);
        return tuple;
    }

    public static String toAnyKey(LotteryPlayKeyTuple keyTuple) {
        Objects.requireNonNull(keyTuple);
        List<String> parts = new ArrayList<>();
        if (Objects.nonNull(keyTuple.getMerchantId())) {
            parts.add(keyTuple.getMerchantId());
        }
        if (StringUtils.isNotBlank(keyTuple.getLotteryCode())) {
            parts.add(keyTuple.getLotteryCode());
        }
        if (StringUtils.isNotBlank(keyTuple.getPlayTypeCode())) {
            parts.add(keyTuple.getPlayTypeCode());
        }
        if (StringUtils.isNotBlank(keyTuple.getPlayCode())) {
            parts.add(keyTuple.getPlayCode());
        }
        if (StringUtils.isNotBlank(keyTuple.getHandicapCode())) {
            parts.add(keyTuple.getHandicapCode());
        }
        Assert.notEmpty(parts, "当前LotteryPlayKeyTuple对象不存在任何有效字段，无法构建key");
        return String.join(":", parts);
    }

    /**
     * 以:拼接多个key
     * @param keys
     * @return
     */
    public static String joinKeys(String... keys) {
        return String.join(":", keys);
    }

    /**
     * 传入指定索引，构建对应的前缀path
     * @param index
     * @return
     */
    public static String toKey(LotteryPlayKeyTuple keyTuple, KeyTupleIndex... index) {
        Objects.requireNonNull(keyTuple);
        Objects.requireNonNull(index);
        List<String> parts = new ArrayList<>(index.length);
        for (KeyTupleIndex tupleIndex : index) {
            if (KeyTupleIndex.MERCHANT_ID.equals(tupleIndex)) {
                String merchantId = keyTuple.getMerchantId();

                Objects.requireNonNull(merchantId, "merchantId 不能为空");
                parts.add(merchantId);
            }
            if (KeyTupleIndex.LOTTERY_CODE.equals(tupleIndex)) {
                String lotteryCode = keyTuple.getLotteryCode();
                Assert.isTrue(StringUtils.isNotBlank(lotteryCode), "lotteryCode 不能为空");
                parts.add(lotteryCode);
            }
            if (KeyTupleIndex.PLAY_TYPE_CODE.equals(tupleIndex)) {
                String playTypeCode = keyTuple.getPlayTypeCode();
                Assert.isTrue(StringUtils.isNotBlank(playTypeCode), "playTypeCode 不能为空");
                parts.add(playTypeCode);
            }
            if (KeyTupleIndex.PLAY_CODE.equals(tupleIndex)) {
                String playCode = keyTuple.getPlayCode();
                Assert.isTrue(StringUtils.isNotBlank(playCode), "playCode 不能为空");
                parts.add(playCode);
            }
            if (KeyTupleIndex.HANDICAP_CODE.equals(tupleIndex)) {
                String handicapCode = keyTuple.getHandicapCode();
                Assert.isTrue(StringUtils.isNotBlank(handicapCode), "handicapCode 不能为空");
                parts.add(handicapCode);
            }
        }
        return String.join(":", parts);
    }

    /**
     * 全路径（五元组）
     * @return
     */
    public static String toFullKey(LotteryPlayKeyTuple keyTuple) {
        Objects.requireNonNull(keyTuple);
        return toKey(keyTuple, KeyTupleIndex.values());
    }
}
