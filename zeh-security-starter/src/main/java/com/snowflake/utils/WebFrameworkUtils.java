package com.snowflake.utils;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebFrameworkUtils {

    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";
    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ACCOUNT = "login_user_account";
    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";
    // 认证成功，授权失败原因属性key
    private static final String REQUEST_ATTRIBUTE_ACCESS_DINED_MSG = "access_dined_msg";


    private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";


    /**
     * 终端的 Header
     *
     */
    public static final String HEADER_TERMINAL = "terminal";

    public static void setLoginUserId(ServletRequest request, Long userId) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID, userId);
    }

    public static void setLoginUserAccount(ServletRequest request, String userAccount) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ACCOUNT, userAccount);
    }

    /**
     * 设置用户类型
     *
     * @param request 请求
     * @param userType 用户类型
     */
    public static void setLoginUserType(ServletRequest request, Integer userType) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE, userType);
    }

    /**
     * 设置授权失败原因属性
     *
     * @param request 请求
     * @param accessDinedMsg 授权失败原因
     */
    public static void setAccessDinedMsg(ServletRequest request, String accessDinedMsg) {
        request.setAttribute(REQUEST_ATTRIBUTE_ACCESS_DINED_MSG, accessDinedMsg);
    }


    /**
     * 获得当前用户的编号，从请求中
     * 注意：该方法仅限于 framework 框架使用！！！
     *
     * @param request 请求
     * @return 用户编号
     */
    public static Long getLoginUserId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (Long) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID);
    }

    /**
     * 获取请求账号
     * @param request
     * @return
     */
    public static String getLoginUserAccount(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (String) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ACCOUNT);
    }


    /**
     * 获取授权失败原因属性
     * @param request 请求
     * @return 授权失败影音
     */
    public static String getAccessDinedMsg(ServletRequest request) {
        if (request == null) {
            return null;
        }
        return (String) request.getAttribute(REQUEST_ATTRIBUTE_ACCESS_DINED_MSG);
    }

    /**
     * 获取http request，只使用spring mvc
     * @return 请求对象
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}
