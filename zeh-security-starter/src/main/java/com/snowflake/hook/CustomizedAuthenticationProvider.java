package com.snowflake.hook;

import com.snowflake.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 自定义的认证供应器，必须返回本插件内部定义的LoginUser，由业务侧实现提供，一个微服务实现一个即可
 * 非强制实现，认证过滤器中，优先使用业务侧提供的该供应器负责供应可信的经过认证的内部用户
 * 目的是为了兼容采用集中式认证中心进行用户认证的系统
 * 但实际上这种逻辑有安全问题，JWT本身是无状态的、自包含的，它的目的就是为了避免对认证中心的强依赖性
 * 对JWT TOKEN的解析认证，应该由各个微服务本地提供这种能力，目前版本先兼容
 * 如果业务侧没有实现该供应器，则采用jar内部逻辑进行jwt token的解析和认证
 */
@FunctionalInterface
public interface CustomizedAuthenticationProvider {

    /**
     * 提供经过认证的内部登录用户
     *
     * @return 经过认证的内部登录用户
     */
    LoginUser supplyAuthentication(HttpServletRequest request, HttpServletResponse response);
}
