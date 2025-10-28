package com.snowflake.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证或者授权失败信息实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthAccessFailInfo {

    /**
     * 失败类型
     */
    private FailType failType;

    /**
     * 请求认证或者授权的用户id
     */
    private Long userId;

    /**
     * 请求认证或者授权的用户账号
     */
    private String userAccount;

    /**
     * 请求认证或者授权的用户名称
     */
    private String userName;

    /**
     * 请求访问的url
     */
    private String requestUrl;

    /**
     * 业务级原因
     */
    private String businessCause;

    /**
     * 系统级原因
     */
    private String sysCause;
}
