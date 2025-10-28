package com.snowflake.cache.key;

import com.snowflake.cache.key.tuple.LotteryPlayKeyTuple;
import com.snowflake.cache.key.tuple.LotteryPlayKeyTupleHelper;

import java.util.Objects;

public class LotteryPlayViewKey {

    private final String merchantId;
    private final String lotteryCode;
    private final String playTypeCode;

    private LotteryPlayViewKey(String merchantId,
                               String lotteryCode,
                               String playTypeCode) {
        this.merchantId = merchantId;
        this.lotteryCode = lotteryCode;
        this.playTypeCode = playTypeCode;
    }

    /**
     * 构建缓存key
     * @return 玩法维度的缓存key
     */
    public String buildKey() {
        // 构建三元组，拼接成缓存 Key，字段顺序固定
        LotteryPlayKeyTuple tuple = LotteryPlayKeyTupleHelper.sortedTuple(merchantId, lotteryCode, playTypeCode);
        return LotteryPlayKeyTupleHelper.toAnyKey(tuple);
    }

    public static MerchantStep builder() {
        return new Builder();
    }

    public interface MerchantStep {
        LotteryStep merchantId(String merchantId);
    }

    public interface LotteryStep {
        PlayTypeStep lotteryCode(String lotteryCode);
    }

    public interface PlayTypeStep {
        BuildStep playTypeCode(String playTypeCode);
    }

    public interface BuildStep {
        LotteryPlayViewKey build();
    }

    // ---------------- Builder 实现类 ----------------
    private static class Builder implements MerchantStep, LotteryStep, PlayTypeStep, BuildStep {
        private String merchantId;
        private String lotteryCode;
        private String playTypeCode;

        @Override
        public LotteryStep merchantId(String merchantId) {
            if (merchantId == null) {
                throw new IllegalArgumentException("merchantId must not be null");
            }
            this.merchantId = merchantId;
            return this;
        }

        @Override
        public PlayTypeStep lotteryCode(String lotteryCode) {
            if (lotteryCode == null || lotteryCode.trim().isEmpty()) {
                throw new IllegalArgumentException("lotteryCode must not be blank");
            }
            this.lotteryCode = lotteryCode;
            return this;
        }

        @Override
        public BuildStep playTypeCode(String playTypeCode) {
            if (playTypeCode == null || playTypeCode.trim().isEmpty()) {
                throw new IllegalArgumentException("playTypeCode must not be blank");
            }
            this.playTypeCode = playTypeCode;
            return this;
        }

        @Override
        public LotteryPlayViewKey build() {
            return new LotteryPlayViewKey(merchantId, lotteryCode, playTypeCode);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryPlayViewKey that = (LotteryPlayViewKey) o;
        return Objects.equals(merchantId, that.merchantId) && Objects.equals(lotteryCode, that.lotteryCode) && Objects.equals(playTypeCode, that.playTypeCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, lotteryCode, playTypeCode);
    }
}
