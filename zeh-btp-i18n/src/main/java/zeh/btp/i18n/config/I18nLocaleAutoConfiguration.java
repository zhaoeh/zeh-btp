package zeh.btp.i18n.config;

import zeh.btp.i18n.core.I18nLocaleWrapper;
import zeh.btp.i18n.core.I18nMessagesProvider;
import zeh.btp.i18n.core.LocaleChecker;
import zeh.btp.i18n.core.ValidLocalesProvider;
import zeh.btp.i18n.endpoint.LocaleCheckerEndpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @description: i18n自动配置
 * @author: ErHu.Zhao
 * @create: 2024-06-21
 **/
@AutoConfiguration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnBean({I18nMessagesProvider.class})
public class I18nLocaleAutoConfiguration {

    @Bean
    public I18nLocaleWrapper localeWrapper() {
        return new I18nLocaleWrapper();
    }

    @ConditionalOnBean({LocaleResolver.class, ValidLocalesProvider.class})
    @Bean
    public LocaleChecker localeChecker(LocaleResolver localeResolver, ValidLocalesProvider validLocalesProvider) {
        return new LocaleChecker(localeResolver, validLocalesProvider);
    }

    @Bean
    public LocaleCheckerEndpoint localeCheckerEndpoint() {
        return new LocaleCheckerEndpoint();
    }

}
