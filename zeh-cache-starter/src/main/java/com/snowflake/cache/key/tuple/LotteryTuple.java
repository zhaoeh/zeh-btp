package com.snowflake.cache.key.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;

public class LotteryTuple {

    public static Tuple1 tuple1(String merchantId) {
        return new Tuple1(merchantId);
    }

    public static Tuple2 tuple2(String merchantId, String lotteryCode) {
        return new Tuple2(merchantId, lotteryCode);
    }

    public static Tuple3 tuple3(String merchantId, String lotteryCode, String playTypeCode) {
        return new Tuple3(merchantId, lotteryCode, playTypeCode);
    }

    public static Tuple4 tuple4(String merchantId, String lotteryCode, String playTypeCode, String playCode) {
        return new Tuple4(merchantId, lotteryCode, playTypeCode, playCode);
    }

    public static Tuple5 tuple5(String merchantId, String lotteryCode, String playTypeCode, String playCode, String handicapCode) {
        return new Tuple5(merchantId, lotteryCode, playTypeCode, playCode, handicapCode);
    }


    @AllArgsConstructor
    @Data
    public static class Tuple1 {
        private String merchantId;
    }


    @AllArgsConstructor
    @Data
    public static class Tuple2 {
        private String merchantId;
        private String lotteryCode;
    }

    @AllArgsConstructor
    @Data
    public static class Tuple3 {
        private String merchantId;
        private String lotteryCode;
        private String playTypeCode;
    }

    @AllArgsConstructor
    @Data
    public static class Tuple4 {
        private String merchantId;
        private String lotteryCode;
        private String playTypeCode;
        private String playCode;
    }

    @AllArgsConstructor
    @Data
    public static class Tuple5 {
        private String merchantId;
        private String lotteryCode;
        private String playTypeCode;
        private String playCode;
        private String handicapCode;
    }


}
