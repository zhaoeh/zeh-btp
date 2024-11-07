package jp.co.futech.framework.mq.redis.config;

import jp.co.futech.framework.mq.redis.core.RedisMQTemplate;
import jp.co.futech.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import jp.co.futech.framework.redis.config.BiRedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * Redis 消息队列 Producer 配置类
 *
 * @author futech.co.jp
 */
@Slf4j
@AutoConfiguration(after = BiRedisAutoConfiguration.class)
public class BiRedisMQProducerAutoConfiguration {

    @Bean
    public RedisMQTemplate redisMQTemplate(StringRedisTemplate redisTemplate,
                                           List<RedisMessageInterceptor> interceptors) {
        RedisMQTemplate redisMQTemplate = new RedisMQTemplate(redisTemplate);
        // 添加拦截器
        interceptors.forEach(redisMQTemplate::addInterceptor);
        return redisMQTemplate;
    }

}
