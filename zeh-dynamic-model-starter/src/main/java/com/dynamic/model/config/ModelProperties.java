package com.dynamic.model.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Set;

/**
 *
 */
@ConfigurationProperties(prefix = "model.config")
@Validated
@Data
public class ModelProperties {

    /**
     * 模式动态路由配置是否全局开启
     */
    private static final Boolean ENABLE_DEFAULT = true;

    /**
     * 是否开启
     */
    private Boolean enable = ENABLE_DEFAULT;

    /**
     * 需要忽略动态表名的请求url
     */
    private Set<String> ignoreUrls = Collections.emptySet();

    /**
     * 需要忽略动态表名的表
     * 默认情况下，每个请求需要带上 operate-model 的请求头。但是，部分请求是无需携带的，或者说即便携带也应该忽略直接走全表表的
     * 说白了，就是有些表是不根据模式进行物理区分的，而是多种模式共享的表，此时直接从mybatis层面忽略表名后缀即可
     */
    private Set<String> ignoreTables = Collections.emptySet();

    /**
     * 需要忽略多租户的 Spring Cache 缓存
     *
     * 即默认所有缓存都开启多租户的功能，所以记得添加对应的 tenant_id 字段哟
     */
    private Set<String> ignoreCaches = Collections.emptySet();

    /**
     * 是否跳过model header校验，全局控制
     */
    private Boolean skipModelHeaderCheck = true;

}
