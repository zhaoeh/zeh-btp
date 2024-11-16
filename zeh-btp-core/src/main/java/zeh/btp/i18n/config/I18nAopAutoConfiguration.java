package zeh.btp.i18n.config;

import zeh.btp.i18n.aop.EnableI18nMethodResultAop;
import zeh.btp.i18n.aop.ResponseBodyAdviceAop;
import zeh.btp.i18n.core.I18nMessagesProvider;
import zeh.btp.i18n.processor.I18nProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * @description: 国际化处理器自动配置类
 * @author: ErHu.Zhao
 * @create: 2024-08-07
 **/
@AutoConfiguration
@AutoConfigureAfter({I18nProcessorAutoConfiguration.class})
@ConditionalOnBean({I18nMessagesProvider.class})
public class I18nAopAutoConfiguration {

    /**
     * 注册responseBodyAdvice切面
     *
     * @param i18nProcessor 国际化处理器
     * @return 切面对象
     */
    @ConditionalOnBean({I18nProcessor.class})
    @Bean
    public ResponseBodyAdviceAop responseBodyAdviceAop(I18nProcessor i18nProcessor) {
        return new ResponseBodyAdviceAop(i18nProcessor);
    }

    /**
     * 注册EnableI18nFieldAop
     *
     * @param i18nProcessor
     * @return
     */
    @ConditionalOnBean({I18nProcessor.class})
    @Bean
    public EnableI18nMethodResultAop enableI18nFieldAop(I18nProcessor i18nProcessor) {
        return new EnableI18nMethodResultAop(i18nProcessor);
    }

}
