package ft.btp.simple.config;

import ft.btp.simple.core.DistributedLockAop;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: 基于redis的分布式锁自动配置
 * @author: ErHu.Zhao
 * @create: 2024-08-19
 **/
@AutoConfiguration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@ConditionalOnBean({RedisTemplate.class})
public class DistributedLockAutoConfiguration {

    @Bean
    public DistributedLockAop distributedLockAop(RedisTemplate redisTemplate) {
        return new DistributedLockAop(redisTemplate);
    }
}
