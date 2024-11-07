package jp.co.futech.framework.common.exception;

import jp.co.futech.framework.common.exception.enums.GlobalErrorCodeConstants;
import jp.co.futech.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.Data;

import java.util.Map;

/**
 * 错误码对象
 * <p>
 * 全局错误码，占用 [0, 999], 参见 {@link GlobalErrorCodeConstants}
 * 业务异常错误码，占用 [1 000 000 000, +∞)，参见 {@link ServiceErrorCodeRange}
 * <p>
 * TODO 错误码设计成对象的原因，为未来的 i18 国际化做准备
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;

    /**
     * 国际化标志
     */
    private final String i18n;

    private Map<String, Object> params;

    public ErrorCode(Integer code, String message, String i18n) {
        this.code = code;
        this.msg = message;
        this.i18n = i18n;
    }

}
