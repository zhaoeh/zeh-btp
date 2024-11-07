package jp.co.futech.framework.pay.config;

import jp.co.futech.framework.pay.core.client.PayClientFactory;
import jp.co.futech.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author futech.co.jp
 */
@AutoConfiguration
public class BiPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
