package zeh.btp.i18n.config;

import zeh.btp.i18n.core.I18nConfigFinder;
import zeh.btp.i18n.core.I18nMessagePostProcessor;
import zeh.btp.i18n.core.I18nMessageWrapper;
import zeh.btp.i18n.core.I18nMessagesProvider;
import zeh.btp.i18n.interpolator.CustomizeMessageInterpolator;
import zeh.btp.i18n.processor.DefaultResponseAdvice;
import zeh.btp.i18n.processor.I18nProcessor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @description: 国际化处理器自动配置类
 * @author: ErHu.Zhao
 * @create: 2024-08-07
 **/
@AutoConfiguration
@AutoConfigureAfter({I18nComponentAutoConfiguration.class})
@ConditionalOnBean({I18nMessagesProvider.class})
public class I18nProcessorAutoConfiguration {

    /**
     * 当容器中存在自定义的消息插值器时，注册国际化处理器，主要负责对目标对象中符合规则的字段进行国际化处理
     *
     * @param customizeMessageInterpolator 自定义的消息插值器对象
     * @return 国际化处理器
     */
    @Bean
    @ConditionalOnBean({CustomizeMessageInterpolator.class, I18nMessageWrapper.class, I18nConfigFinder.class})
    public I18nProcessor i18nProcessor(ObjectProvider<CustomizeMessageInterpolator> customizeMessageInterpolator,
                                       ObjectProvider<I18nMessageWrapper> i18nMessageWrapper,
                                       ObjectProvider<I18nConfigFinder> i18nCodeFinder,
                                       List<I18nMessagePostProcessor> i18nMessagePostProcessor) {
        return new I18nProcessor(customizeMessageInterpolator, i18nMessageWrapper, i18nCodeFinder, i18nMessagePostProcessor);
    }

    @ConditionalOnBean({CustomizeMessageInterpolator.class})
    @ConditionalOnMissingBean({ResponseBodyAdvice.class})
    @Bean
    public DefaultResponseAdvice defaultResponseAdvice() {
        return new DefaultResponseAdvice();
    }

}
