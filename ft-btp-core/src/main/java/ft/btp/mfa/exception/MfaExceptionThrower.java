package ft.btp.mfa.exception;

import ft.btp.mfa.MfaProcess;
import ft.btp.mfa.enums.MfaErrorEnum;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @description: mfaException抛出器
 * @author: ErHu.Zhao
 * @create: 2024-05-30
 **/
public class MfaExceptionThrower {

    public static void notNull(MfaProcess mfaProcess, @Nullable Object object, MfaErrorEnum errorEnum) {
        if (object == null) {
            throwsMfaException(mfaProcess, new MfaException(errorEnum.getMessage(), errorEnum.getCode()));
        }
    }

    public static void isTrue(MfaProcess mfaProcess, boolean expression, MfaErrorEnum errorEnum) {
        if (!expression) {
            throwsMfaException(mfaProcess, new MfaException(errorEnum.getMessage(), errorEnum.getCode()));
        }
    }

    public static void throwsMfaException(MfaErrorEnum errorEnum) {
        throw new MfaException(errorEnum.getMessage(), errorEnum.getCode());
    }

    public static void throwsMfaException(MfaErrorEnum errorEnum, Throwable cause) {
        throw new MfaException(errorEnum.getMessage(), errorEnum.getCode(), cause);
    }

    /**
     * 抛出mfaException
     *
     * @param message message
     */
    public static void throwsMfaException(String message) {
        throw new MfaException(message);
    }

    /**
     * 抛出mfaException
     *
     * @param code    code
     * @param message message
     */
    public static void throwsMfaException(Integer code, String message) {
        throw new MfaException(message, code);
    }

    /**
     * 抛出mfa异常
     *
     * @param mfaProcess
     * @param e
     */
    public static void throwsMfaException(MfaProcess mfaProcess, Exception e) {
        if (RuntimeException.class.isInstance(e)) {
            RuntimeException xx = (RuntimeException) e;
            Optional.ofNullable(mfaProcess).map(m -> m.buildMfaExceptionConvert()).map(convert -> convert.convertMfaException(xx)).ifPresentOrElse(a -> {
                throw a;
            }, () -> {
                throw xx;
            });
        } else {
            e.printStackTrace();
        }
    }
}
