package com.dynamic.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum BizModel {
    BLEND(0, "混合模式"), CA(1, "现金模式"), CR(2, "信用模式"),
    ;
    final Integer code;
    final String desc;

    BizModel(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<BizModel> getValidModels() {
        return Arrays.stream(BizModel.values()).filter(m -> !Objects.equals(BLEND, m)).toList();
    }


    /**
     * 安全解析 header 字符串为枚举
     */
    public static Optional<BizModel> fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            // header 没传
            return Optional.empty();
        }
        try {
            return Optional.of(BizModel.valueOf(value.trim().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            // header 值不合法
            return Optional.empty();
        }
    }

}
