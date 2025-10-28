package com.snowflake.hook;

import com.snowflake.handler.AuthAccessFailHandler;
import com.snowflake.pojo.AuthAccessFailInfo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 认证或者授权失败处理器接口
 */
@FunctionalInterface
public interface AuthAccessFailProcessor {

    /**
     * 认证或者授权失败后置处理
     * @param request http request
     * @param failInfo 失败信息封装实体
     * @param handler 委托操作器
     */
    void process(HttpServletRequest request, AuthAccessFailInfo failInfo, AuthAccessFailHandler handler);
}
