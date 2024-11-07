package jp.co.futech.framework.tenant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * 多租户配置
 *
 * @author futech.co.jp
 */
@ConfigurationProperties(prefix = "bi.tenant")
@Data
public class TenantProperties {

    /**
     * 租户是否开启
     */
    private static final Boolean ENABLE_DEFAULT = true;

    /**
     * 是否开启
     */
    private Boolean enable = ENABLE_DEFAULT;

    /**
     * 是否开启DB多租自动配置
     */
    private Boolean enableDB = ENABLE_DEFAULT;

    /**
     * 是否开启Web多租自动配置
     */
    private Boolean enableWeb = ENABLE_DEFAULT;

    /**
     * 是否开启Security多租自动配置
     */
    private Boolean enableSecurity = ENABLE_DEFAULT;

    /**
     * 是否开启Job多租自动配置
     */
    private Boolean enableJob = ENABLE_DEFAULT;

    /**
     * 是否开启Redis多租自动配置
     */
    private Boolean enableRedis = ENABLE_DEFAULT;

    /**
     * 是否开启Rabbit MQ多租自动配置
     */
    private Boolean enableRabbit = ENABLE_DEFAULT;

    /**
     * 是否开启Rocket MQ多租自动配置
     */
    private Boolean enableRocket = ENABLE_DEFAULT;

    /**
     * 需要忽略多租户的请求
     *
     * 默认情况下，每个请求需要带上 tenant-id 的请求头。但是，部分请求是无需带上的，例如说短信回调、支付回调等 Open API！
     */
    private Set<String> ignoreUrls = Collections.emptySet();

    /**
     * 需要忽略多租户的表
     *
     * 即默认所有表都开启多租户的功能，所以记得添加对应的 tenant_id 字段哟
     */
    private Set<String> ignoreTables = Collections.emptySet();

}
