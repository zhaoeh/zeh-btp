package jp.co.futech.gateway.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-09-10
 **/
@Configuration
public class CacheConfig {

    @Bean
    public Cache buildCache(){
        return new ConcurrentMapCache("zhao");
    }
}
