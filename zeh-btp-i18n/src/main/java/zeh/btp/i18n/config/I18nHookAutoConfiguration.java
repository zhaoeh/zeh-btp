package zeh.btp.i18n.config;

import zeh.btp.common.AnnotatedBeanPostProcessor;
import zeh.btp.i18n.annotation.EnableI18nWeavingForResponse;
import zeh.btp.i18n.core.I18nMessagesProvider;
import zeh.btp.i18n.processor.CheckResponseBodyAdviceProcessor;
import zeh.btp.i18n.processor.DefaultResponseAdvice;
import zeh.btp.i18n.processor.ResponseBodyAdviceBeanPostProcessor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * @description: 钩子自动配置
 * @author: ErHu.Zhao
 * @create: 2024-08-08
 **/
@AutoConfiguration
@ConditionalOnBean({I18nMessagesProvider.class})
public class I18nHookAutoConfiguration {

    @Bean
    public BeanPostProcessor myBeanPostProcessor() {
        return new ResponseBodyAdviceBeanPostProcessor();
    }

    @Bean
    public BeanPostProcessor annotationBeanPostProcessor() {
        return new AnnotatedBeanPostProcessor(EnableI18nWeavingForResponse.class);
    }

    @Bean
    public SmartInitializingSingleton checkResponseBodyAdviceProcessor(ObjectProvider<DefaultResponseAdvice> defaultResponseAdvice) {
        return new CheckResponseBodyAdviceProcessor(defaultResponseAdvice);
    }

}
