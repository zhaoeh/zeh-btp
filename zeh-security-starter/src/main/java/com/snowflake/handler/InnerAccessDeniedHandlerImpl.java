package com.snowflake.handler;

import com.snowflake.exception.InnerErrorCode;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.snowflake.enums.GlobalErrorCodeConstants.FORBIDDEN;

/**
 * spring security内部授权失败回调器：访问一个需要认证的 URL 资源，已经认证（登录）但是没有权限的情况下，返回 403 。
 * 其生命周期作用于Spring security过滤器链内部抛出的授权异常
 *
 */
@Slf4j
public class InnerAccessDeniedHandlerImpl implements AccessDeniedHandler {

    /**
     * 认证或者授权失败处理器钩子
     */
    private final AuthAccessFailProcessor processor;

    private final AuthAccessFailHandler handler;

    public InnerAccessDeniedHandlerImpl(ObjectProvider<AuthAccessFailProcessor> accessFailProcessorObjectProvider, AuthAccessFailHandler handler) {
        this.processor = accessFailProcessorObjectProvider.getIfAvailable();
        this.handler = handler;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        CommonResult<InnerErrorCode> accessDenied = CommonResult.error(FORBIDDEN);
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        String userAccount = SecurityFrameworkUtils.getLoginUserAccount();
        String requestUrl = request.getRequestURI();
        log.error("访问 URL:{} 时，用户id:{} 用户account：{} 权限不足，越权访问！响应体：{}",
                requestUrl,
                userId,
                userAccount,
                accessDenied, e);
        String accessDinedMsg = StringUtils.substring(WebFrameworkUtils.getAccessDinedMsg(request), 0, 2000);
        String sysMsg = StringUtils.substring(e.getMessage(), 0, 2000);
        ProcessDelegate.doProcess(processor, handler, request,
                AuthAccessFailInfo.builder().
                        failType(FailType.ACCESS_FAIL).
                        userId(userId).userAccount(userAccount).requestUrl(requestUrl).
                        businessCause(accessDinedMsg).sysCause(sysMsg).build());
        // 返回 403
        ServletUtils.writeJSON(response, accessDenied);
    }

}
