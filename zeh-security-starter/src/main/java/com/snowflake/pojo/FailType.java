package com.snowflake.pojo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FailType {

    AUTH_FAIL(0, "认证失败"), ACCESS_FAIL(1, "授权失败"), OTHER_FAIL(2, "其他错误");

    Integer code;

    String Type;

    public String getType() {
        return Type;
    }

    public Integer getCode() {
        return code;
    }
}
