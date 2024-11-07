package ft.btp.mfa;

import ft.btp.mfa.hook.MfaExceptionConvert;
import ft.btp.mfa.hook.MfaResultWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.BooleanUtils;

/**
 * @description: mfa核心认证接口
 * @author: ErHu.Zhao
 * @create: 2024-05-17
 **/
public interface MfaProcess {

    /**
     * 是否已经绑定确认 header 参数
     */
    String BINDING_MFA_DONE = "Binding-Mfa-Done";

    /**
     * 请求重新绑定 header 参数
     */
    String RE_BINDING_MFA = "Re-Binding-Mfa";

    /**
     * mfa 二次验证码
     */
    String MFA_CODE = "Mfa-Code";

    /**
     * 时间偏移量 几个30s，取值 1-17之间
     *
     * @return
     */
    default int offset() {
        return 5;
    }

    /**
     * 自定义mfa异常
     *
     * @return
     */
    default MfaExceptionConvert buildMfaExceptionConvert() {
        return e -> e;
    }

    /**
     * 当MfaProcess生命周期开始阶段执行，默认空实现
     *
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     */
    default void init(Object[] targetArgs, Object targetResult) {
    }

    /**
     * 当MfaProcess生命周期开始阶段执行，默认空实现
     *
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     */
    default void destroy(Object[] targetArgs, Object targetResult) {
    }

    /**
     * 构建wrapper
     *
     * @return
     */
    default MfaResultWrapper buildWrapper() {
        return a -> a;
    }

    /**
     * 目标用户是否开启mfa二次认证 默认未开启
     *
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @return
     */
    default boolean enableMfa(Object[] targetArgs, Object targetResult) {
        return false;
    }

    /**
     * 目标用户是否绑定了mfa认证 默认未绑定
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @return
     */
    default boolean bindingMfa(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
        return false;
    }

    /**
     * 是否完成mfa扫描绑定，默认从http request header中获取 binding_mfa_done 参数
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @return
     */
    default boolean bindingMfaDone(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
        String bindingMfaDone = request.getHeader(BINDING_MFA_DONE);
        return BooleanUtils.isTrue(Boolean.valueOf(bindingMfaDone));
    }

    /**
     * 是否请求重新绑定mfa，默认从http request header中获取 re_binding_mfa 参数
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @return
     */
    default boolean reBindingMfa(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
        String reBindingMfa = request.getHeader(RE_BINDING_MFA);
        return BooleanUtils.isTrue(Boolean.valueOf(reBindingMfa));
    }

    /**
     * 当用户确认绑定mfa完成后提供二次验证码
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @return
     */
    default String supplyMfaCode(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
        return request.getHeader(MFA_CODE);
    }

    /**
     * 返回mfa密钥以用于二次码校验
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @return
     */
    String supplyMfaSecret(HttpServletRequest request, Object[] targetArgs, Object targetResult);

    /**
     * 返回参与mfa认证的用户名称
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @return 参与mfa认证的用户名称
     */
    String authUser(HttpServletRequest request, Object[] targetArgs, Object targetResult);

    /**
     * 参与mfa认证的标识，由客户端自定义，不能为空或者空白字符串
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @return 参与mfa认证的用户名称
     */
    String authFlag(HttpServletRequest request, Object[] targetArgs, Object targetResult);

    /**
     * 保存mfa密钥
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     * @param mfaSecret    mfa密钥
     */
    void saveMfaSecret(HttpServletRequest request, Object[] targetArgs, Object targetResult, String mfaSecret);

    /**
     * 当mfa校验成功后的执行逻辑
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     */
    void doActionWhenCheckSucceed(HttpServletRequest request, Object[] targetArgs, Object targetResult);

    /**
     * 当mfa校验失败后的执行逻辑
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     */
    default void doActionWhenCheckFailed(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
    }

    /**
     * 当客户端确认扫描绑定后的执行逻辑
     *
     * @param request      http request
     * @param targetArgs   标注@MfaAuth注解的目标方法参数
     * @param targetResult 标注@MfaAuth注解的目标方法执行结果
     */
    void doActionWhenBindingDone(HttpServletRequest request, Object[] targetArgs, Object targetResult);


}
