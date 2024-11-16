package zeh.btp.mfa.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 开启google mfa扫描
 */
@Configuration
@ComponentScan(basePackages = {"ft.btp.mfa"})
@Slf4j
public class MfaScanConfig {

    public MfaScanConfig() {
        log.info("begin to register MfaScanConfig , and scan ft.btp.mfa");
    }
}
