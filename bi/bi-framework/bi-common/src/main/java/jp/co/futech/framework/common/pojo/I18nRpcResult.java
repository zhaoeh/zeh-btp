package jp.co.futech.framework.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jp.co.futech.framework.common.exception.ErrorCode;
import jp.co.futech.framework.common.exception.ServiceException;
import jp.co.futech.framework.common.exception.enums.GlobalErrorCodeConstants;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;


/**
 * @description: 特殊的国际化响应，避免其他服务调用时，对messageSource对象产生循环依赖
 * 举例说明：如果使用CommonResult，CommonResult中需要国际化消息，此时需要依赖messageSource对象；如果其他服务跨服务调用该接口，目的就是实例化messageSource，此时messageSource依赖该接口，彼此依赖产生循环
 * @author: ErHu.Zhao
 * @create: 2024-08-26
 **/
@Data
public class I18nRpcResult<T> implements Serializable {

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
    private String msg;

    /**
     * 将传入的 result 对象，转换成另外一个泛型结果的对象
     * <p>
     * 因为 A 方法返回的 CommonResult 对象，不满足调用其的 B 方法的返回，所以需要进行转换。
     *
     * @param result 传入的 result 对象
     * @param <T>    返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> I18nRpcResult<T> error(I18nRpcResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    public static <T> I18nRpcResult<T> error(Integer code, String message) {
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        I18nRpcResult<T> result = new I18nRpcResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> I18nRpcResult<T> error(Integer code, String message, String i18n) {
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        I18nRpcResult<T> result = new I18nRpcResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> I18nRpcResult<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg(), errorCode.getI18n());
    }

    public static <T> I18nRpcResult<T> success(T data) {
        I18nRpcResult<T> result = new I18nRpcResult<>();
        result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
        result.data = data;
        result.msg = GlobalErrorCodeConstants.SUCCESS.getMsg();
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
        throw new ServiceException(code, msg);
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

    public static <T> I18nRpcResult<T> error(ServiceException serviceException) {
        return error(serviceException.getCode(), serviceException.getMessage());
    }
}
