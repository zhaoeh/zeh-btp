package zeh.btp.mfa.hook;

import zeh.btp.mfa.show.MfaResult;

/**
 * @description: 响应包装钩子
 * @author: GuanLan.Zhao
 * @create: 2024-05-30
 **/
@FunctionalInterface
public interface MfaResultWrapper {

    Object wrapMfaResult(MfaResult mfaResult);
}
