package com.snowflake.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内部自定义服务异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class InnerServiceException extends RuntimeException {

    /**
     * 业务错误码
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
    public InnerServiceException() {
    }

    public InnerServiceException(InnerErrorCode innerErrorCode) {
        this.code = innerErrorCode.getCode();
        this.message = innerErrorCode.getMsg();
    }

    public InnerServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public InnerServiceException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public InnerServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

}
