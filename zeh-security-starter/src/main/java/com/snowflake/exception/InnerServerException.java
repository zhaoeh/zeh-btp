package com.snowflake.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内部自定义服务器异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class InnerServerException extends RuntimeException {

    /**
     * 全局错误码
     *
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public InnerServerException() {
    }

    public InnerServerException(InnerErrorCode innerErrorCode) {
        this.code = innerErrorCode.getCode();
        this.message = innerErrorCode.getMsg();
    }

    public InnerServerException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public InnerServerException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public InnerServerException setMessage(String message) {
        this.message = message;
        return this;
    }

}
