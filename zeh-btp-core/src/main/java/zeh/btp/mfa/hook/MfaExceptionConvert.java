package zeh.btp.mfa.hook;

/**
 * @description: 异常转换钩子
 * @author: GuanLan.Zhao
 * @create: 2024-05-30
 **/
@FunctionalInterface
public interface MfaExceptionConvert {

    RuntimeException convertMfaException(RuntimeException e);

}
