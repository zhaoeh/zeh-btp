package com.consistency.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 *
 */
@ConfigurationProperties(prefix = "consistency.config")
@Validated
@Data
public class ConsistencyProperties {

    /**
     * 模式动态路由配置是否全局开启
     */
    private static final Boolean ENABLE_DEFAULT = true;

    /**
     * 是否开启
     */
    private Boolean enable = ENABLE_DEFAULT;

}
