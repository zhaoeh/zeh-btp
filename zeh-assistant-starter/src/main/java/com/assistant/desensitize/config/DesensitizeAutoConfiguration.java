package com.assistant.desensitize.config;

import com.assistant.desensitize.condition.DesensitizeCondition;
import com.assistant.desensitize.core.factory.DesensitizeStrategyFactory;
import com.assistant.desensitize.core.strategy.DesensitizeStrategy;
import com.assistant.desensitize.core.strategy.impl.AccountStrategy;
import com.assistant.desensitize.core.strategy.impl.DefaultStrategy;
import com.assistant.desensitize.core.strategy.impl.IdCardStrategy;
import com.assistant.desensitize.core.strategy.impl.MobileStrategy;
import com.assistant.desensitize.handler.DesensitizeMetadataCollector;
import com.assistant.desensitize.web.DesensitizeContextFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AutoConfiguration
@AutoConfigureAfter({JacksonAutoConfiguration.class})
@ConditionalOnWebApplication
@Slf4j
public class DesensitizeAutoConfiguration {

    @Bean
    public DesensitizeStrategy defaultStrategy() {
        return new DefaultStrategy();
    }

    @Bean
    public DesensitizeStrategy idCardStrategy() {
        return new IdCardStrategy();
    }

    @Bean
    public DesensitizeStrategy mobileStrategy() {
        return new MobileStrategy();
    }

    @Bean
    public DesensitizeStrategy accountStrategy() {
        return new AccountStrategy();
    }

    /**
     * 默认：始终脱敏
     * @return false：不跳过脱敏
     */
    @Bean
    @ConditionalOnMissingBean
    public DesensitizeCondition defaultCondition() {
        return () -> false;
    }

    @Bean
    public DesensitizeMetadataCollector desensitizeMetadataCollector(ApplicationContext applicationContext) {
        return new DesensitizeMetadataCollector(applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public DesensitizeContextFilter desensitizeContextFilter(DesensitizeCondition desensitizeCondition, DesensitizeMetadataCollector desensitizeMetadataCollector) {
        return new DesensitizeContextFilter(desensitizeMetadataCollector, desensitizeCondition);
    }

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public DesensitizeStrategyFactory desensitizeStrategyFactory(List<DesensitizeStrategy> strategies) {
        return new DesensitizeStrategyFactory(strategies);
    }
}
