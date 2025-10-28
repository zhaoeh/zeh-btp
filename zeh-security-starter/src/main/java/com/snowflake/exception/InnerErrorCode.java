package com.snowflake.exception;

import lombok.Data;

/**
 * 内部异常码
 */
@Data
public class InnerErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;

    public InnerErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

}
