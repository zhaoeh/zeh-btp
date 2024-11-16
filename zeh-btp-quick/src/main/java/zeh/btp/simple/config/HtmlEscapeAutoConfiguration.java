package zeh.btp.simple.config;

import zeh.btp.simple.core.HtmlEscapeAop;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @description: html特殊字符转义自动配置
 * @author: ErHu.Zhao
 * @create: 2024-08-19
 **/
@AutoConfiguration
public class HtmlEscapeAutoConfiguration {

    @Bean
    public HtmlEscapeAop htmlEscapeAop() {
        return new HtmlEscapeAop();
    }
}
