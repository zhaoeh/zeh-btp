package jp.co.futech.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author futech.co.jp
 */
@ConfigurationProperties(prefix = "bi.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "bi.env.tag";

    /**
     * 环境标签
     */
    private String tag;

}
