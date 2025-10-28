package com.snowflake.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * 内部通用配置文件，不做强制要求，后续可扩展支持配置化的url白名单或者其他配置项
 */
@ConfigurationProperties(prefix = "common.security")
@Data
public class CommonSecurityProperties {

    /**
     * 免登录的 URL 列表
     */
    private List<String> permitAllUrls = Collections.emptyList();

    /**
     * 是否开启权限预检：默认是 true可通过此配置项进行关闭
     */
    private Boolean preCheckEnable = true;

    /**
     * PasswordEncoder 加密复杂度，越高开销越大
     */
    private Integer passwordEncoderLength = 4;
}
