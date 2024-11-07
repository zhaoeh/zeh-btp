package jp.co.futech.module.insight.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据源配置
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class JdbcParamsConfig {

    private Integer initialSize;
    private Integer maxActive;
    private Integer minIdle;
    private Integer maxWait;

    private Integer connectionTimeout;

    private Long idleTimeout;

    private Long maxLifetime;

    private Integer maxPoolSize;

    private Integer socketTimeout;

    private String useTimeZone;

}

