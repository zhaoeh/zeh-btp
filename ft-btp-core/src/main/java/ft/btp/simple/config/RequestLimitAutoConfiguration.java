package ft.btp.simple.config;

import ft.btp.common.AnnotatedBeanPostProcessor;
import ft.btp.simple.RequestLimit;
import ft.btp.simple.api.PriorityLimitConfigurer;
import ft.btp.simple.core.RequestLimitAspect;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @description: 钩子自动配置
 * @author: ErHu.Zhao
 * @create: 2024-08-08
 **/
@AutoConfiguration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@ConditionalOnBean({RedisTemplate.class})
public class RequestLimitAutoConfiguration {

    @Bean
    public BeanPostProcessor annotationBeanPostProcessor() {
        return new AnnotatedBeanPostProcessor(RequestLimit.class);
    }

    @Bean
    public RequestLimitAspect requestLimitAspect(RedisTemplate redisTemplate, List<PriorityLimitConfigurer> priorityLimitConfigurers) {
        return new RequestLimitAspect(redisTemplate, priorityLimitConfigurers);
    }

}
