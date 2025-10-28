package com.snowflake.isolation;

import com.snowflake.config.CommonSecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 数据隔离安全策略：基于mp多租户插件实现
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "common.isolation", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(CommonSecurityProperties.class)
public class IsolationAutoConfiguration {
}
