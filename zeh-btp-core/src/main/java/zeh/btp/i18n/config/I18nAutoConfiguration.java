package zeh.btp.i18n.config;

import zeh.btp.i18n.api.DefaultI18nCodeMapper;
import zeh.btp.i18n.core.I18nConfigFinder;
import zeh.btp.i18n.core.I18nMessageHelper;
import zeh.btp.i18n.core.I18nMessagesLoader;
import zeh.btp.i18n.core.I18nMessagesProvider;
import zeh.btp.i18n.scope.I18nPeriodRefreshBeanScopeManager;
import zeh.btp.i18n.scope.InnerI18nPeriodRefresh;
import zeh.btp.scope.annotation.CustomizedRefreshScope;
import zeh.btp.scope.config.CustomizedRefreshScopeAutoConfiguration;
import zeh.btp.scope.hook.PeriodRefreshBeanScopeManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.lang.Nullable;

/**
 * @description: i18n自动配置
 * @author: ErHu.Zhao
 * @create: 2024-06-21
 **/
@AutoConfiguration
@AutoConfigureBefore({MessageSourceAutoConfiguration.class})
@AutoConfigureAfter({CustomizedRefreshScopeAutoConfiguration.class})
@ConditionalOnBean({I18nMessagesProvider.class})
public class I18nAutoConfiguration {

    @Bean
    public I18nMessagesLoader i18nMessagesCore(@Nullable I18nMessagesProvider i18nMessagesProvider,
                                               ObjectProvider<I18nConfigFinder> configFinderObjectProvider) {
        return new I18nMessagesLoader(i18nMessagesProvider, configFinderObjectProvider);
    }

    /**
     * 国际化配置过期时间配置
     *
     * @param scopeManager 作用域管理器
     * @return
     */
    @Bean
    @ConditionalOnBean(I18nPeriodRefreshBeanScopeManager.class)
    public PeriodRefreshBeanScopeManager periodRefreshBeanScopeManager(ObjectProvider<I18nPeriodRefreshBeanScopeManager> scopeManager) {
        return new InnerI18nPeriodRefresh(scopeManager);
    }

    /**
     * 通过@ObjectRefreshScope标注MessageSource作为一个代理bean，每次都会执行自定义Scope
     *
     * @param i18NMessagesLoader i18NMessagesLoader instance
     * @return MessageSource instance
     */
    @Bean
    @CustomizedRefreshScope(manager = InnerI18nPeriodRefresh.class)
    public MessageSource messageSource(I18nMessagesLoader i18NMessagesLoader) {
        StaticMessageSource staticMessageSource = new StaticMessageSource();
        i18NMessagesLoader.setSource(staticMessageSource).reloadMessages();
        return staticMessageSource;
    }

    @Bean
    public DefaultI18nCodeMapper defaultI18nCodeMapper() {
        return new DefaultI18nCodeMapper();
    }

    @Bean
    public I18nMessageHelper messageHelper() {
        return new I18nMessageHelper();
    }

}
