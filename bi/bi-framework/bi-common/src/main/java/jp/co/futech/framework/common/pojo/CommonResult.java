package jp.co.futech.framework.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ft.btp.i18n.annotation.EnableI18nField;
import ft.btp.i18n.annotation.I18nField;
import ft.btp.i18n.info.DynamicParams;
import jp.co.futech.framework.common.exception.ErrorCode;
import jp.co.futech.framework.common.exception.ServiceException;
import jp.co.futech.framework.common.exception.enums.GlobalErrorCodeConstants;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * 通用返回
 *
 * @param <T> 数据泛型
 */
@Data
@EnableI18nField
public class CommonResult<T> implements DynamicParams, Serializable {

    /**
     * 错误码
     *
     * @see ErrorCode#getCode()
     */
    private Integer code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误提示，用户可阅读
     *
     * @see ErrorCode#getMsg() ()
     */
    @I18nField(i18nField = "i18n")
    private String msg;

    /**
     * i18n国际化标志
     */
//    @JsonIgnore
    private String i18n;

    /**
     * i18n占位参数
     */
    @JsonIgnore
    private Map<String, Object> params;

    /**
     * 将传入的 result 对象，转换成另外一个泛型结果的对象
     *
     * 因为 A 方法返回的 CommonResult 对象，不满足调用其的 B 方法的返回，所以需要进行转换。
     *
     * @param result 传入的 result 对象
     * @param <T>    返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMsg(), result.getI18n());
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> CommonResult<T> error(Integer code, String message, String i18n) {
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        result.i18n = i18n;
        return result;
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg(), errorCode.getI18n());
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
        result.data = data;
        result.msg = GlobalErrorCodeConstants.SUCCESS.getMsg();
        result.i18n = GlobalErrorCodeConstants.SUCCESS.getI18n();
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
     * 判断是否有异常。如果有，则抛出 {@link ServiceException} 异常
     */
    public void checkError() throws ServiceException {
        if (isSuccess()) {
            return;
        }
        // 业务异常
        throw new ServiceException(code, msg, i18n);
    }

    /**
     * 判断是否有异常。如果有，则抛出 {@link ServiceException} 异常
     * 如果没有，则返回 {@link #data} 数据
     */
    @JsonIgnore // 避免 jackson 序列化
    public T getCheckedData() {
        checkError();
        return data;
    }

    public static <T> CommonResult<T> error(ServiceException serviceException) {
        return error(serviceException.getCode(), serviceException.getMessage(), serviceException.getI18n());
    }

    @Override
    public Map<String, Object> provideParams() {
        return params;
    }
}
