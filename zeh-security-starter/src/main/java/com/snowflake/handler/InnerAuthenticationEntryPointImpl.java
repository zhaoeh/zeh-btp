package com.snowflake.handler;

import com.snowflake.exception.InnerErrorCode;
import com.snowflake.filter.CustomizedAuthenticationFilter;
import com.snowflake.hook.AuthAccessFailProcessor;
import com.snowflake.pojo.AuthAccessFailInfo;
import com.snowflake.pojo.CommonResult;
import com.snowflake.pojo.FailType;
import com.snowflake.utils.SecurityFrameworkUtils;
import com.snowflake.utils.ServletUtils;
import com.snowflake.utils.WebFrameworkUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.Objects;

import static com.snowflake.enums.GlobalErrorCodeConstants.FORBIDDEN;
import static com.snowflake.enums.GlobalErrorCodeConstants.UNAUTHORIZED;


/**
 * 访问一个需要认证的 URL 资源，但是此时自己尚未认证（登录）的情况下，返回 401，从而使前端重定向到登录页
 * 此处理器，是spring security内部认证过滤链中，当认证失败后回调的处理器。它只作用于spring security认证流程内部，不会捕获下游业务的任何异常
 * 下游业务异常，应该交由业务侧的全局异常处理负责处理。
 */
@Slf4j
public class InnerAuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    /**
     * 认证或者授权失败处理器钩子
     */
    private final AuthAccessFailProcessor processor;

    private final AuthAccessFailHandler handler;

    public InnerAuthenticationEntryPointImpl(ObjectProvider<AuthAccessFailProcessor> accessFailProcessorObjectProvider, AuthAccessFailHandler handler) {
        this.processor = accessFailProcessorObjectProvider.getIfAvailable();
        this.handler = handler;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        String userAccount = SecurityFrameworkUtils.getLoginUserAccount();
        String requestUrl = request.getRequestURI();
        Object accessDinedMsg = request.getAttribute(CustomizedAuthenticationFilter.CUSTOMIZED_ACCESS_DINED_MSG);
        if (Objects.nonNull(accessDinedMsg)) {
            Throwable customizedAccessDinedException = (Throwable) accessDinedMsg;
            e.addSuppressed(customizedAccessDinedException);
            CommonResult<InnerErrorCode> accessDenied = CommonResult.error(FORBIDDEN);
            log.error("访问 URL:{} 时，用户id:{} 用户account：{} 权限不足，越权访问！响应体：{}",
                    requestUrl,
                    userId,
                    userAccount,
                    accessDenied, e);
            String accessMsg = StringUtils.substring(StringUtils.isBlank(WebFrameworkUtils.getAccessDinedMsg(request)) ?
                    Objects.toString(customizedAccessDinedException.getMessage()) : WebFrameworkUtils.getAccessDinedMsg(request), 0, 2000);
            String sysMsg = StringUtils.substring(e.getMessage(), 0, 2000);
            ProcessDelegate.doProcess(processor, handler, request,
                    AuthAccessFailInfo.builder().
                            failType(FailType.ACCESS_FAIL).
                            userId(userId).userAccount(userAccount).requestUrl(requestUrl).
                            businessCause(accessMsg).sysCause(sysMsg).build());
            // 返回 403
            ServletUtils.writeJSON(response, accessDenied);
        } else {
            String authFail;
            Object authFailMsg = request.getAttribute(CustomizedAuthenticationFilter.CUSTOMIZED_AUTH_FAIL_MSG);
            if (Objects.nonNull(authFailMsg)) {
                Throwable customizedAuthFailException = (Throwable) authFailMsg;
                e.addSuppressed(customizedAuthFailException);
                authFail = StringUtils.substring(customizedAuthFailException.getMessage(), 0, 2000);
            } else {
                authFail = StringUtils.substring(e.getMessage(), 0, 2000);
            }
            CommonResult<InnerErrorCode> authenticationFailed = CommonResult.error(UNAUTHORIZED);
            log.error("访问 URL:{} 时，用户id:{} 用户account：{} 认证失败！响应体：{}",
                    requestUrl,
                    userId,
                    userAccount,
                    authenticationFailed, e);
            String sysMsg = StringUtils.substring(e.getMessage(), 0, 2000);
            ProcessDelegate.doProcess(processor, handler, request,
                    AuthAccessFailInfo.builder().
                            failType(FailType.AUTH_FAIL).
                            userId(userId).userAccount(userAccount).requestUrl(requestUrl).
                            businessCause(authFail).sysCause(sysMsg).build());
            // 返回 401
            ServletUtils.writeJSON(response, authenticationFailed);
        }
    }
}
