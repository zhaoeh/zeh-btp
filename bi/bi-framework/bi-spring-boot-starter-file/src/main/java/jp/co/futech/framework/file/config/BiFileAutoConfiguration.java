package jp.co.futech.framework.file.config;

import jp.co.futech.framework.file.core.client.FileClientFactory;
import jp.co.futech.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author futech.co.jp
 */
@AutoConfiguration
public class BiFileAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "bi.file", value = "enable-file", havingValue = "true", matchIfMissing = true)
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
