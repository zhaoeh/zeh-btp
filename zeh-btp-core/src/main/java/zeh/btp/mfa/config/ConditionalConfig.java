package zeh.btp.mfa.config;

import zeh.btp.mfa.MfaProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-05-21
 **/
@Configuration
@ConditionalOnBean(MfaProcess.class)
@Import(MfaScanConfig.class)
@Slf4j
public class ConditionalConfig {
    public ConditionalConfig() {
        log.info("ConditionalConfig instant");
    }
}
