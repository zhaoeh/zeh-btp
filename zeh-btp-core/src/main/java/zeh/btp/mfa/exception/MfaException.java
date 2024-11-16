package zeh.btp.mfa.exception;

import zeh.btp.mfa.enums.MfaErrorEnum;

/**
 * @description: MFA Exception
 * @author: ErHu.Zhao
 * @create: 2024-05-16
 **/
public class MfaException extends RuntimeException {

    /**
     * 异常码
     */
    protected Integer code;

    public MfaException() {
        super();
    }

    public MfaException(MfaErrorEnum errorEnum) {
        this(errorEnum.getMessage(), errorEnum.getCode());
    }

    public MfaException(MfaErrorEnum errorEnum, Throwable cause) {
        this(errorEnum.getMessage(), errorEnum.getCode(), cause);
    }

    public MfaException(String message) {
        super(message);
    }

    public MfaException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public MfaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MfaException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
