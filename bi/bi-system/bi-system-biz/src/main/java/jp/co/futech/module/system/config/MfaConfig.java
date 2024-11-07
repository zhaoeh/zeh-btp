package jp.co.futech.module.system.config;

import ft.btp.mfa.MfaProcess;
import ft.btp.mfa.enums.MfaErrorEnum;
import ft.btp.mfa.exception.MfaException;
import ft.btp.mfa.hook.MfaExceptionConvert;
import ft.btp.mfa.hook.MfaResultWrapper;
import ft.btp.mfa.show.MfaResult;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.module.system.context.AdminUserHolder;
import jp.co.futech.module.system.dal.dataobject.tenant.TenantDO;
import jp.co.futech.module.system.dal.dataobject.user.AdminUserDO;
import jp.co.futech.module.system.enums.logger.LoginLogTypeEnum;
import jp.co.futech.module.system.enums.logger.LoginResultEnum;
import jp.co.futech.module.system.service.auth.AdminAuthServiceImpl;
import jp.co.futech.module.system.service.tenant.TenantService;
import jp.co.futech.module.system.service.user.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static jp.co.futech.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_CHECK_CODE;
import static jp.co.futech.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;

/**
 * @description: mfa客户端配置
 * @author: ErHu.Zhao
 * @create: 2024-05-17
 **/
@Configuration
@Slf4j
public class MfaConfig {

    @Resource
    private AdminUserService userService;

    @Resource
    private AdminAuthServiceImpl adminAuthService;

    @Resource
    private TenantService tenantService;

    @Bean
    public MfaProcess process() {

        MfaProcess process = new MfaProcess() {

            @Override
            public void destroy(Object[] targetArgs, Object targetResult) {
                AdminUserHolder.resetAdminUser();
            }

            @Override
            public int offset() {
                return 5;
            }

            @Override
            public MfaExceptionConvert buildMfaExceptionConvert() {
                return MfaConfig::convertMfaException;
            }

            @Override
            public MfaResultWrapper buildWrapper() {
                return MfaConfig::wrapperMfaResult;
            }

            @Override
            public boolean enableMfa(Object[] targetArgs, Object targetResult) {
                boolean isEnableMfa = Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getIsMfa).map(e -> e.equals(0)).orElse(false);
                if (!isEnableMfa && createToken(targetResult)) {
                    // 当前用户未开启mfa，走正常登录流程，记录登录日志
                    adminAuthService.createLoginLog(Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getId).orElse(null),
                            Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getUsername).orElse(null), LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.SUCCESS);
                }
                return isEnableMfa;
            }

            @Override
            public boolean bindingMfa(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
                return Optional.ofNullable(obtainAdminUser()).filter(MfaConfig::hasBindingMfa).isPresent();
            }

            @Override
            public String supplyMfaSecret(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
                return Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getMfaSecretKey).orElse(null);
            }

            @Override
            public String authUser(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
                return Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getUsername).orElse(null);
            }

            @Override
            public String authFlag(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
                return Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getTenantId).map(tenantService::getTenant).map(TenantDO::getName).orElse(null);
            }

            @Override
            public void saveMfaSecret(HttpServletRequest request, Object[] targetArgs, Object targetResult, String mfaSecret) {
                Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getId).ifPresent(id -> userService.updateUserMfaSecretKey(id, mfaSecret));
            }

            @Override
            public void doActionWhenCheckSucceed(HttpServletRequest request, Object[] targetArgs, Object targetResult) {

                if (createToken(targetResult)) {
                    adminAuthService.createLoginLog(Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getId).orElse(null),
                            Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getUsername).orElse(null),
                            LoginLogTypeEnum.LOGIN_MFA, LoginResultEnum.SUCCESS);
                }

            }

            @Override
            public void doActionWhenBindingDone(HttpServletRequest request, Object[] targetArgs, Object targetResult) {
                Optional.ofNullable(obtainAdminUser()).map(AdminUserDO::getId).ifPresent(id -> userService.updateMfaBindingStatus(id));
            }
        };
        log.info("finish to instant MfaProcess.");
        return process;
    }

    /**
     * 转换异常
     *
     * @param e 异常
     * @return 转换后的异常
     */
    private static RuntimeException convertMfaException(RuntimeException e) {
        if (MfaException.class.isInstance(e)) {
            MfaException mfaException = (MfaException) e;
            Integer code = mfaException.getCode();
            if (MfaErrorEnum.QR_CODE_IS_BLANK.getCode().equals(code)) {
                return exception(AUTH_LOGIN_USER_DISABLED);
            }
            if (MfaErrorEnum.GENERATE_QR_CODE_FAILED.getCode().equals(code)) {
                return exception(AUTH_LOGIN_USER_DISABLED, e);
            }
            if (MfaErrorEnum.CHECK_FAILED.getCode().equals(code) || MfaErrorEnum.UNAUTHORIZED.getCode().equals(code)) {
                log.error("check mfa failed,e is ", e);
                return exception(AUTH_LOGIN_CHECK_CODE, e);
            }
            return exception0(code, mfaException.getMessage());
        }
        return e;
    }


    /**
     * 包装mfa响应
     *
     * @param result mfa result
     * @return 包装后的响应结构
     */
    private static Object wrapperMfaResult(MfaResult result) {
        Assert.notNull(result, "MfaResult cannot be null");
        if (result.isSuccess()) {
            return CommonResult.success(result.getData());
        } else {
            return CommonResult.error(result.getCode(), result.getMessage());
        }
    }

    /**
     * 获取admin user info
     *
     * @return
     */
    private AdminUserDO obtainAdminUser() {
        AdminUserDO adminUser = AdminUserHolder.getAdminUser();
        return adminUser;
    }

    /**
     * 是否绑定了mfa
     *
     * @param adminUser 实体
     * @return 是否绑定了mfa true：已经绑定 false：未绑定
     */
    private static boolean hasBindingMfa(AdminUserDO adminUser) {
        Assert.notNull(adminUser, "adminUser cannot be null");
        return StringUtils.isNotBlank(adminUser.getMfaSecretKey()) && Objects.equals(adminUser.getMfaBindingStatus(), 0);
    }

    /**
     * 重构响应结果
     *
     * @param targetResult
     */
    private boolean createToken(Object targetResult) {
        if (CommonResult.class.isInstance(targetResult)) {
            // 创建 Token 令牌，记录登录日志
            AdminUserDO adminUserDO = obtainAdminUser();
            CommonResult result = (CommonResult) targetResult;
            result.setData(adminAuthService.createTokenAfterLoginSuccess(adminUserDO));
            return true;
        }
        return false;
    }

}
