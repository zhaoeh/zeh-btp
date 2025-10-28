package com.snowflake.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.snowflake.enums.GlobalErrorCodeConstants;
import com.snowflake.exception.InnerErrorCode;
import com.snowflake.exception.InnerServiceException;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * 分布式认证授权jar内部通用返回
 *
 */
@Data
public class CommonResult<T> implements Serializable {

    private Integer code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误提示，用户可阅读
     *
     */
    private String message;

    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMessage());
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> CommonResult<T> error(InnerErrorCode innerErrorCode) {
        return error(innerErrorCode.getCode(), innerErrorCode.getMsg());
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
        result.data = data;
        result.message = "";
        return result;
    }

    public static boolean isSuccess(Integer code) {
        return Objects.equals(code, GlobalErrorCodeConstants.SUCCESS.getCode());
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isSuccess() {
        return isSuccess(code);
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isError() {
        return !isSuccess();
    }

    // ========= 和 Exception 异常体系集成 =========

    /**
     * 判断是否有异常。如果有，则抛出 {@link InnerServiceException} 异常
     */
    public void checkError() throws InnerServiceException {
        if (isSuccess()) {
            return;
        }
        // 业务异常
        throw new InnerServiceException(code, message);
    }

    @JsonIgnore // 避免 jackson 序列化
    public T getCheckedData() {
        checkError();
        return data;
    }

    public static <T> CommonResult<T> error(InnerServiceException innerServiceException) {
        return error(innerServiceException.getCode(), innerServiceException.getMessage());
    }

}
