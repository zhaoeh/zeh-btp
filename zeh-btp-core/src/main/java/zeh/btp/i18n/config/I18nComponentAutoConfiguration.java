package zeh.btp.i18n.config;

import zeh.btp.i18n.api.DefaultI18nCodeMapper;
import zeh.btp.i18n.api.I18nCodeMapper;
import zeh.btp.i18n.api.I18nHandler;
import ft.btp.i18n.core.*;
import zeh.btp.i18n.core.*;
import zeh.btp.i18n.filter.LocaleCheckFilter;
import zeh.btp.i18n.interpolator.CustomizeMessageInterpolator;
import zeh.btp.scope.config.CustomizedRefreshScopeAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;

/**
 * @description: i18n自动配置
 * @author: ErHu.Zhao
 * @create: 2024-06-21
 **/
@AutoConfiguration
@AutoConfigureAfter({I18nLocaleAutoConfiguration.class, I18nAutoConfiguration.class, CustomizedRefreshScopeAutoConfiguration.class})
@ConditionalOnBean({I18nMessagesProvider.class})
@Slf4j
public class I18nComponentAutoConfiguration {

    @Bean
    @ConditionalOnBean({LocaleChecker.class})
    public FilterRegistrationBean<LocaleCheckFilter> localeCheckFilter(LocaleChecker localeChecker) {
        FilterRegistrationBean<LocaleCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LocaleCheckFilter(localeChecker));
        return registrationBean;
    }

    /**
     * 注册国际化code查找器
     *
     * @param codeMappers        code映射器集合
     * @param i18nMessagesLoader 国际化消息加载器
     * @return
     */
    @Bean
    @ConditionalOnBean({I18nMessagesLoader.class, DefaultI18nCodeMapper.class})
    public I18nConfigFinder i18nCodeFinder(List<I18nCodeMapper> codeMappers,
                                           I18nMessagesLoader i18nMessagesLoader,
                                           List<I18nHandler> handlers) {
        return new I18nConfigFinder(codeMappers, i18nMessagesLoader, handlers);
    }

    @Bean
    @ConditionalOnBean(MessageSource.class)
    public I18nMessageWrapper messageWrapper() {
        return new I18nMessageWrapper();
    }

    /**
     * 根据条件注册自定义的消息插值器（只有当容器中存在MessageSource和LocaleResolver时，否则插值器将毫无用处）
     *
     * @param messageSource 国际化消息资源对象
     * @return 自定义消息插值器对象
     */
    @Bean
    @ConditionalOnBean({MessageSource.class, LocaleResolver.class})
    public CustomizeMessageInterpolator customizeMessageInterpolator(MessageSource messageSource) {
        return new CustomizeMessageInterpolator(messageSource);
    }

}
