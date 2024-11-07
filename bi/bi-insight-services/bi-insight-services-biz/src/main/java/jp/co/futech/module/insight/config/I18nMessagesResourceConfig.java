package jp.co.futech.module.insight.config;

import ft.btp.scope.holder.RefreshScopeHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 国际化资源来源配置
 * @author: ErHu.Zhao
 * @create: 2024-08-28
 **/
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "bi.i18n")
@Data
@Slf4j
public class I18nMessagesResourceConfig {

    private String messages;

    public I18nMessagesResourceConfig() {
        log.info("实例化 I18nMessagesResourceConfig 对象");
        RefreshScopeHolder.resetScope();
    }

    /**
     * nacos远程配置是否已经被刷新
     *
     * @return
     */
    public boolean isRefreshed() {
        return RefreshScopeHolder.isReset();
    }
}
