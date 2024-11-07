package jp.co.futech.module.insight.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @description: index指标配置枚举
 * @author: ErHu.Zhao
 * @create: 2024-06-18
 **/
@ConfigurationProperties(prefix = "anomaly.indexes")
@Data
@Configuration
@RefreshScope
public class IndexConfig {

    private Map<String, SqlNameBean> indexMap;

    @PostConstruct
    public void check() {
        Assert.notEmpty(indexMap, "indexMap cannot be empty,please check anomaly.indexes.index_map configure");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SqlNameBean {
        private String queryField;

        private String resultField;
    }

}
