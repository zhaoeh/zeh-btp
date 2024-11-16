package zeh.btp.validation.config;

import zeh.btp.validation.messages.MessageSourceHolder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @description: 自定义校验器自动配置类
 * @author: ErHu.Zhao
 * @create: 2024-07-08
 **/
@AutoConfiguration
public class CustomizeValidationAutoConfiguration {

    @Bean
    public MessageSourceHolder messageSourceHolder() {
        return new MessageSourceHolder();
    }
}
