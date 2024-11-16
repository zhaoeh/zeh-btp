package zeh.btp.mfa.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求返回消息
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class MfaResult<T> {

    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    public boolean isSuccess() {
        return success != null && success;
    }

    public static <T> MfaResult success(Integer code, String message, T data) {
        return new MfaResult(ResultEnum.SUCCESS.getSuccess(), code, message, data);
    }

    public static <T> MfaResult success(Integer code, String message) {
        return success(code, message, null);
    }


    public static <T> MfaResult success(T data) {
        return success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static MfaResult success() {
        return success(null, null);
    }

    private static <T> MfaResult fail(Integer code, String message, T data) {
        log.error("Restful Request fail, code:{}, message:{}, data:{}", code, message, data);
        return new MfaResult(ResultEnum.FAIL.getSuccess(), code, message, data);
    }

    public static <T> MfaResult fail(String message) {
        return fail(ResultEnum.FAIL.getCode(), message, null);
    }

    public static <T> MfaResult fail(T data) {
        return fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), data);
    }

    public static <T> MfaResult fail(Integer code, String message) {
        return fail(code, message, null);
    }

    public static MfaResult fail() {
        return fail(null);
    }


}
