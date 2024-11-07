package jp.co.futech.framework.idempotent.config;

import jp.co.futech.framework.idempotent.core.aop.IdempotentAspect;
import jp.co.futech.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import jp.co.futech.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import jp.co.futech.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import jp.co.futech.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import jp.co.futech.framework.redis.config.BiRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = BiRedisAutoConfiguration.class)
public class BiIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
