package jp.co.futech.framework.mybatis.config;

import jp.co.futech.framework.mybatis.core.log.StdOutImplSetting;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-08-01
 **/
@AutoConfiguration
public class MybatisLogConfiguration {

    @Bean
    public StdOutImplSetting stdOutImplSetting(){
        return new StdOutImplSetting();
    }
}
