package com.snowflake.hook;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证成功钩子：该钩子注册在认证过滤器之后的一个过滤器中，强制业务侧提供实现，目的是给业务侧一个在认证成功之后统一处理某些逻辑的机会
 *
 * 可以在该钩子实现中，统一处理账号级别白名单校验、统一透传uuid、统计记录操作日志等功能
 */
public interface WhenAuthenticationSuccess {


    /**
     * 认证成功后做一些事情（比如校验已认证账号的ip白名单，根据已认证账号获取业务侧账号附加数据等）
     *
     * @param account 认证成功的账号
     * @param flag 平台标识
     */
    void doSome(HttpServletRequest request, HttpServletResponse response, String account, String flag);

    /**
     * 完成后处理
     *
     * @param request
     * @param response
     * @param account
     * @param flag
     */
    void afterComplete(HttpServletRequest request, HttpServletResponse response, String account, String flag);

}
