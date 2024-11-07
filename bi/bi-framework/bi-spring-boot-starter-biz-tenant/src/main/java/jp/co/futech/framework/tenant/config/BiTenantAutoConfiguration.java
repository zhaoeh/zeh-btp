package jp.co.futech.framework.tenant.config;

import jakarta.annotation.Resource;
import jp.co.futech.framework.common.enums.WebFilterOrderEnum;
import jp.co.futech.framework.mybatis.core.util.MyBatisUtils;
import jp.co.futech.framework.redis.config.BiCacheProperties;
import jp.co.futech.framework.tenant.core.annotation.IgnoreTenantRequest;
import jp.co.futech.framework.tenant.core.aop.TenantIgnoreAspect;
import jp.co.futech.framework.tenant.core.db.TenantDatabaseInterceptor;
import jp.co.futech.framework.tenant.core.job.TenantJobAspect;
import jp.co.futech.framework.tenant.core.mq.rabbitmq.TenantRabbitMQInitializer;
import jp.co.futech.framework.tenant.core.mq.redis.TenantRedisMessageInterceptor;
import jp.co.futech.framework.tenant.core.mq.rocketmq.TenantRocketMQInitializer;
import jp.co.futech.framework.tenant.core.redis.TenantRedisCacheManager;
import jp.co.futech.framework.tenant.core.security.TenantSecurityWebFilter;
import jp.co.futech.framework.tenant.core.service.TenantFrameworkService;
import jp.co.futech.framework.tenant.core.service.TenantFrameworkServiceImpl;
import jp.co.futech.framework.tenant.core.web.TenantContextWebFilter;
import jp.co.futech.framework.web.config.WebProperties;
import jp.co.futech.framework.web.core.handler.GlobalExceptionHandler;
import jp.co.futech.module.system.api.tenant.TenantApi;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@AutoConfiguration
@ConditionalOnProperty(prefix = "bi.tenant", value = "enable", matchIfMissing = true) // 允许使用 bi.tenant.enable=false 禁用多租户
@EnableConfigurationProperties(TenantProperties.class)
public class BiTenantAutoConfiguration {

    @Resource
    private ApplicationContext applicationContext;

    @Bean
    public TenantFrameworkService tenantFrameworkService(TenantApi tenantApi) {
        return new TenantFrameworkServiceImpl(tenantApi);
    }

    // ========== AOP ==========

    @Bean
    public TenantIgnoreAspect tenantIgnoreAspect() {
        return new TenantIgnoreAspect();
    }

    // ========== DB ==========

    @ConditionalOnBean(MybatisPlusInterceptor.class)
    @ConditionalOnProperty(prefix = "bi.tenant", value = "enable-db", havingValue = "true", matchIfMissing = true)
    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties properties,
                                                                 MybatisPlusInterceptor interceptor) {
        TenantLineInnerInterceptor inner = new TenantLineInnerInterceptor(new TenantDatabaseInterceptor(properties));
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    // ========== WEB ==========

    @ConditionalOnProperty(prefix = "bi.tenant", value = "enable-web", havingValue = "true", matchIfMissing = true)
    @Bean
    public FilterRegistrationBean<TenantContextWebFilter> tenantContextWebFilter() {
        FilterRegistrationBean<TenantContextWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantContextWebFilter());
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_CONTEXT_FILTER);
        return registrationBean;
    }

    // ========== Security ==========

    @ConditionalOnProperty(prefix = "bi.tenant", value = "enable-security", havingValue = "true", matchIfMissing = true)
    @Bean
    public FilterRegistrationBean<TenantSecurityWebFilter> tenantSecurityWebFilter(TenantProperties tenantProperties,
                                                                                   WebProperties webProperties,
                                                                                   GlobalExceptionHandler globalExceptionHandler,
                                                                                   TenantFrameworkService tenantFrameworkService) {
        FilterRegistrationBean<TenantSecurityWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantSecurityWebFilter(tenantProperties, webProperties,
                globalExceptionHandler, tenantFrameworkService, getIgnoreTenantUrlsFromAnnotations()));
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_SECURITY_FILTER);
        return registrationBean;
    }

    // ========== Job ==========

    @Bean
    @ConditionalOnClass(name = "com.xxl.job.core.handler.annotation.XxlJob")
    @ConditionalOnProperty(prefix = "bi.tenant", value = "enable-job", havingValue = "true", matchIfMissing = true)
    public TenantJobAspect tenantJobAspect(TenantFrameworkService tenantFrameworkService) {
        return new TenantJobAspect(tenantFrameworkService);
    }

    // ========== MQ ==========

    /**
     * 多租户 Redis 消息队列的配置类
     *
     * 为什么要单独一个配置类呢？如果直接把 TenantRedisMessageInterceptor Bean 的初始化放外面，会报 RedisMessageInterceptor 类不存在的错误
     */
    @Configuration
    @ConditionalOnClass(name = "jp.co.futech.framework.mq.redis.core.RedisMQTemplate")
    @ConditionalOnProperty(prefix = "bi.tenant", value = "enable-redis", havingValue = "true", matchIfMissing = true)
    public static class TenantRedisMQAutoConfiguration {

        @Bean
        public TenantRedisMessageInterceptor tenantRedisMessageInterceptor() {
            return new TenantRedisMessageInterceptor();
        }

    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.amqp.rabbit.core.RabbitTemplate")
    @ConditionalOnProperty(prefix = "bi.tenant", value = "enable-rabbit", havingValue = "true", matchIfMissing = true)
    public TenantRabbitMQInitializer tenantRabbitMQInitializer() {
        return new TenantRabbitMQInitializer();
    }

    @Bean
    @ConditionalOnClass(name = "org.apache.rocketmq.spring.core.RocketMQTemplate")
    @ConditionalOnProperty(prefix = "bi.tenant", value = "enable-rocket", havingValue = "true", matchIfMissing = true)
    public TenantRocketMQInitializer tenantRocketMQInitializer() {
        return new TenantRocketMQInitializer();
    }

    // ========== Redis ==========

    @ConditionalOnProperty(prefix = "bi.tenant", value = "enable-redis", havingValue = "true", matchIfMissing = true)
    @Bean
    @Primary // 引入租户时，tenantRedisCacheManager 为主 Bean
    public RedisCacheManager tenantRedisCacheManager(RedisTemplate<String, Object> redisTemplate,
                                                     RedisCacheConfiguration redisCacheConfiguration,
                                                     BiCacheProperties biCacheProperties) {
        // 创建 RedisCacheWriter 对象
        RedisConnectionFactory connectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory,
                BatchStrategies.scan(biCacheProperties.getRedisScanBatchSize()));
        // 创建 TenantRedisCacheManager 对象
        return new TenantRedisCacheManager(cacheWriter, redisCacheConfiguration);
    }

    private List<String> getIgnoreTenantUrlsFromAnnotations() {
        List<String> ignoreTenantUrls = new ArrayList<>();
        // 获得接口对应的 HandlerMethod 集合
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // 获得有 @IgnoreTenant 注解的接口
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            if (!handlerMethod.hasMethodAnnotation(IgnoreTenantRequest.class)) {
                continue;
            }
            if (entry.getKey().getPatternsCondition() == null) {
                continue;
            }
            Set<String> urls = entry.getKey().getPatternsCondition().getPatterns();
            ignoreTenantUrls.addAll(urls);
        }
        return ignoreTenantUrls;
    }
}
