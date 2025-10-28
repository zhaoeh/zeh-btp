package com.consistency.config;

import com.consistency.aop.ConsistencyAspect;
import com.consistency.hook.ConsistencyConfigure;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
// 允许使用 consistency.config.enable 配置项修改为false来关闭全局自动配置
@ConditionalOnProperty(prefix = "consistency.config", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(ConsistencyProperties.class)
public class ConsistencyAutoConfiguration {

    /**
     * 注册并行模式请求aop
     * @return aop实例
     */
    @ConditionalOnBean({RedissonClient.class})
    @Bean
    public ConsistencyAspect consistencyAspect(ConsistencyProperties consistencyProperties, RedissonClient redissonClient, ObjectProvider<ConsistencyConfigure> consistencyConfigurerObjectProvider) {
        return new ConsistencyAspect(consistencyProperties, redissonClient, consistencyConfigurerObjectProvider);
    }

}
