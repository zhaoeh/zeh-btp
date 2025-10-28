package com.snowflake;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 登录用户信息：内部通用封装，尽量实现通用
 */
@Data
@Accessors(chain = true)
public class LoginUser {

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 平台编码
     */
    private String platForm;

    /**
     * 额外的用户信息
     */
    private Map<String, String> info;

    /**
     * 租户编号
     */
    private Long tenantId;

    /**
     * 授权范围
     */
    private List<String> scopes;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

}
