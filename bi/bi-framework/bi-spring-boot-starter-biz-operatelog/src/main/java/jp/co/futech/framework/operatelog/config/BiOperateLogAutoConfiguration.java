package jp.co.futech.framework.operatelog.config;

import jp.co.futech.framework.operatelog.core.aop.OperateLogAspect;
import jp.co.futech.framework.operatelog.core.service.OperateLogFrameworkService;
import jp.co.futech.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import jp.co.futech.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class BiOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
