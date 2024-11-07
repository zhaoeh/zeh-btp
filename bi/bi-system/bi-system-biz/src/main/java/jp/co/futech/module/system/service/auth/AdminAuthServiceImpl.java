package jp.co.futech.module.system.service.auth;

import cn.hutool.core.util.ObjectUtil;
import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.enums.UserTypeEnum;
import jp.co.futech.framework.common.util.monitor.TracerUtils;
import jp.co.futech.framework.common.util.servlet.ServletUtils;
import jp.co.futech.framework.common.util.validation.ValidationUtils;
import jp.co.futech.module.system.api.logger.dto.LoginLogCreateReqDTO;
import jp.co.futech.module.system.api.sms.SmsCodeApi;
import jp.co.futech.module.system.api.social.dto.SocialUserBindReqDTO;
import jp.co.futech.module.system.api.social.dto.SocialUserRespDTO;
import jp.co.futech.module.system.context.AdminUserHolder;
import jp.co.futech.module.system.controller.admin.auth.vo.*;
import jp.co.futech.module.system.convert.auth.AuthConvert;
import jp.co.futech.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import jp.co.futech.module.system.dal.dataobject.user.AdminUserDO;
import jp.co.futech.module.system.enums.logger.LoginLogTypeEnum;
import jp.co.futech.module.system.enums.logger.LoginResultEnum;
import jp.co.futech.module.system.enums.oauth2.OAuth2ClientConstants;
import jp.co.futech.module.system.enums.sms.SmsSceneEnum;
import jp.co.futech.module.system.service.logger.LoginLogService;
import jp.co.futech.module.system.service.member.MemberService;
import jp.co.futech.module.system.service.oauth2.OAuth2TokenService;
import jp.co.futech.module.system.service.social.SocialUserService;
import jp.co.futech.module.system.service.tenant.TenantService;
import jp.co.futech.module.system.service.user.AdminUserService;
import com.google.common.annotations.VisibleForTesting;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.validation.Validator;

import java.util.Map;
import java.util.Objects;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.framework.common.util.servlet.ServletUtils.getClientIP;
import static jp.co.futech.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author futech.co.jp
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminUserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private SocialUserService socialUserService;
    @Resource
    private MemberService memberService;
    @Resource
    private Validator validator;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SmsCodeApi smsCodeApi;

    @Resource
    private TenantService tenantService;

    /**
     * 验证码的开关，默认为 true
     */
    @Value("${bi.captcha.enable:true}")
    private Boolean captchaEnable;


    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 校验验证码
        validateCaptcha(reqVO);
        String username = reqVO.getUsername();
        String password = reqVO.getPassword();
        // 使用账号密码，进行登录
        AdminUserDO user = authenticate(username, password);

        // 如果 socialType 非空，说明需要绑定社交用户
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        AdminUserHolder.setAdminUser(user);

        return null;
    }

    @Override
    public void sendSmsCode(AuthSmsSendReqVO reqVO) {
        // 登录场景，验证是否存在
        if (userService.getUserByMobile(reqVO.getMobile()) == null) {
            throw exception(AUTH_MOBILE_NOT_EXISTS);
        }
        // 发送验证码
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    @Override
    public AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) {
        // 校验验证码
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_MEMBER_LOGIN.getScene(), getClientIP()));

        // 获得用户信息
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE);
    }

    public void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    @Override
    public AuthLoginRespVO socialLogin(AuthSocialLoginReqVO reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        SocialUserRespDTO socialUser = socialUserService.getSocialUserByCode(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (socialUser == null || socialUser.getUserId() == null) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }

        // 获得用户
        AdminUserDO user = userService.getUser(socialUser.getUserId());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL);
    }

    @VisibleForTesting
    void validateCaptcha(AuthLoginReqVO reqVO) {
        // 如果验证码关闭，则不进行校验
        if (!captchaEnable) {
            return;
        }
        // 校验验证码
        ValidationUtils.validate(validator, reqVO, AuthLoginReqVO.CodeEnableGroup.class);
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(reqVO.getCaptchaVerification());
        ResponseModel response = captchaService.verification(captchaVO);
        // 验证不通过
        if (!response.isSuccess()) {
            // 创建登录失败日志（验证码不正确)
            createLoginLog(null, reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR.setParams(Map.of("msg", response.getRepMsg())), response.getRepMsg());
        }
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        AuthLoginRespVO  authLoginRespVO=new AuthLoginRespVO();
        // 插入登陆日志
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        authLoginRespVO=AuthConvert.INSTANCE.convert(accessTokenDO);
        // 构建返回结果
        return authLoginRespVO;
    }

    public AuthLoginRespVO createTokenAfterLoginSuccess(AdminUserDO userVo) {
        AuthLoginRespVO authLoginRespVO = new AuthLoginRespVO();
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userVo.getId(), getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        authLoginRespVO = AuthConvert.INSTANCE.convert(accessTokenDO);
        authLoginRespVO.setUserId(userVo.getId());
        authLoginRespVO.setUserName(userVo.getUsername());
        // 构建返回结果
        return authLoginRespVO;
    }


    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logType);
    }

    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        if (ObjectUtil.equal(getUserType().getValue(), userType)) {
            reqDTO.setUsername(getUsername(userId));
        } else {
            reqDTO.setUsername(memberService.getMemberUserMobile(userId));
        }
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

//    @Override
//    public String getMfaQrcode(String userName) {
//        String secret = googleAuthentivatorTool.getSecret(userName);
//        AdminUserDO user = userService.getUserByUsername(userName);
//        TenantDO tenant = tenantService.getTenant(user.getTenantId());
//        // 保存secretKey
//        userService.updateUserMfaSecretKey(user.getId(),secret);
//        //生成二维码url
//        String qrStr="";
//        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
//            String url = googleAuthentivatorTool.getQRBarcodeURL(userName, tenant.getName(), secret);
//            QRCodeUtils.writeToStream(url, bos);
//            qrStr = Base64.encodeBase64String(bos.toByteArray());
//        }catch (WriterException | IOException e) {
//            throw exception(AUTH_LOGIN_USER_DISABLED,e);
//        }
//        if (StringUtils.isEmpty(qrStr)) {
//            throw exception(AUTH_LOGIN_USER_DISABLED);
//        }
//        return "data:image/png;base64," + qrStr;
//    }

//    @Override
//    public AuthLoginRespVO mfaCheckCode(AuthLoginMfaReqVO reqVO){
//        AuthLoginRespVO  authLoginRespVO=new AuthLoginRespVO();
//        AdminUserDO user = userService.getUser(reqVO.getUserId());
//        long t = System.currentTimeMillis();
//        googleAuthentivatorTool.setWindowSize(5); //宽限时间5*30秒
//        boolean result = false;
//        try{
//            result = googleAuthentivatorTool.check_code(user.getMfaSecretKey(), Long.valueOf(reqVO.getCheckCode()), t);
//        }catch (Exception e){
//            throw exception(AUTH_LOGIN_CHECK_CODE,e);
//        }
//        if(result){
//            // 插入登陆日志
//            createLoginLog(reqVO.getUserId(), getUsername(reqVO.getUserId()), LoginLogTypeEnum.LOGIN_MFA, LoginResultEnum.SUCCESS);
//            // 创建访问令牌
//            OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(reqVO.getUserId(), getUserType().getValue(),
//                    OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
//            authLoginRespVO=AuthConvert.INSTANCE.convert(accessTokenDO);
//        }
//        authLoginRespVO.setUserId(reqVO.getUserId());
//        authLoginRespVO.setUserName(getUsername(reqVO.getUserId()));
//        authLoginRespVO.setMfaCheckResult(result);
//        // 构建返回结果
//        return authLoginRespVO;
//    }

}
