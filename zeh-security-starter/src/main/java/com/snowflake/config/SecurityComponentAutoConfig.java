package com.snowflake.config;

import com.snowflake.context.TransmittableThreadLocalSecurityContextHolderStrategy;
import com.snowflake.filter.CustomizedAuthenticationFilter;
import com.snowflake.handler.*;
import com.snowflake.hook.ApiOfflineProcessor;
import com.snowflake.hook.AuthAccessFailProcessor;
import com.snowflake.hook.CustomizedAuthenticationProvider;
import com.snowflake.hook.WhenAuthenticationSuccess;
import com.snowflake.permission.PermissionCheckAround;
import com.snowflake.permission.evaluator.PermissionRulesConfigure;
import com.snowflake.permission.evaluator.custmoized.CustomizedPermissionEvaluator;
import com.snowflake.permission.evaluator.custmoized.CustomizedPermissionStrategyFactory;
import com.snowflake.permission.evaluator.dynamic.DynamicPermissionEvaluator;
import com.snowflake.permission.evaluator.dynamic.DynamicPermissionStrategyFactory;
import com.snowflake.permission.evaluator.statics.StaticPermissionEvaluator;
import com.snowflake.permission.evaluator.statics.StaticPermissionStrategyFactory;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 分布式认证授权组件自动配置类，负责向分布式认证授权系统提供核心组件能力
 */
@AutoConfiguration
@ConditionalOnMissingBean(SecurityFilterChain.class)
@AutoConfigureOrder(-1)
@EnableConfigurationProperties(CommonSecurityProperties.class)
public class SecurityComponentAutoConfig {

    @Resource
    private CommonSecurityProperties commonSecurityProperties;

    @Bean
    public AuthAccessFailHandler authAccessFailHandler() {
        return new AuthAccessFailHandler();
    }

    /**
     * 方法处理器请求模式收集器
     * @param applicationContext 容器上下文
     * @return 方法处理器请求模式收集器
     */
    @Bean
    public ApiRequestPatternsCollector apiRequestPatternsCollector(ApplicationContext applicationContext) {
        return new ApiRequestPatternsCollector(applicationContext);
    }

    /**
     * 认证失败处理类 Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectProvider<AuthAccessFailProcessor> provider, AuthAccessFailHandler handler) {
        return new InnerAuthenticationEntryPointImpl(provider, handler);
    }

    /**
     * 权限不足处理器 Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(ObjectProvider<AuthAccessFailProcessor> provider, AuthAccessFailHandler handler) {
        return new InnerAccessDeniedHandlerImpl(provider, handler);
    }

    /**
     * 不可逆加密器组件
     *
     * @return 组件实力
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(commonSecurityProperties.getPasswordEncoderLength());
    }

    /**
     * 全局异常处理器
     *
     * @return 内部自定义的全局异常处理器
     */
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ObjectProvider<AuthAccessFailProcessor> provider, ObjectProvider<AuthAccessFailHandler> handler) {
        return new GlobalExceptionHandler(provider, handler);
    }

    /**
     * 自定义的 Token 认证过滤器 Bean，分布式jar认证核心
     */
    @Bean
    public CustomizedAuthenticationFilter authenticationTokenFilter(GlobalExceptionHandler globalExceptionHandler,
                                                                    ObjectProvider<CustomizedAuthenticationProvider> authenticationProviders,
                                                                    ObjectProvider<ApiOfflineProcessor> apiOfflineProcessorObjectProvider,
                                                                    WhenAuthenticationSuccess whenAuthenticationSuccess) {
        return new CustomizedAuthenticationFilter(commonSecurityProperties, globalExceptionHandler, authenticationProviders, apiOfflineProcessorObjectProvider,
                whenAuthenticationSuccess);
    }

    /**
     * 自定义spring security上下文透传策略
     * @return 工厂bean
     */
    @Bean
    public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(TransmittableThreadLocalSecurityContextHolderStrategy.class.getName());
        return methodInvokingFactoryBean;
    }

    @Bean
    public PermissionCheckAround permissionCheckAround(ApplicationContext context) {
        return new PermissionCheckAround(context);
    }


    @Bean
    public StaticPermissionStrategyFactory staticPermissionStrategyFactory(ObjectProvider<PermissionRulesConfigure> permissionRulesConfigures) {
        return new StaticPermissionStrategyFactory(permissionRulesConfigures);
    }

    @Bean
    public DynamicPermissionStrategyFactory dynamicPermissionStrategyFactory(ObjectProvider<PermissionRulesConfigure> permissionRulesConfigures) {
        return new DynamicPermissionStrategyFactory(permissionRulesConfigures);
    }

    @Bean
    public CustomizedPermissionStrategyFactory customizedPermissionStrategyFactory(ObjectProvider<PermissionRulesConfigure> permissionRulesConfigures) {
        return new CustomizedPermissionStrategyFactory(permissionRulesConfigures);
    }


    @Bean("cp")
    public CustomizedPermissionEvaluator customizedPermissionEvaluator(CustomizedPermissionStrategyFactory factory) {
        return new CustomizedPermissionEvaluator(factory);
    }

    @Bean("dp")
    public DynamicPermissionEvaluator dynamicPermissionEvaluator(DynamicPermissionStrategyFactory factory) {
        return new DynamicPermissionEvaluator(factory);
    }

    @Bean("sp")
    public StaticPermissionEvaluator staticPermissionEvaluator(StaticPermissionStrategyFactory factory) {
        return new StaticPermissionEvaluator<>(factory);
    }
}