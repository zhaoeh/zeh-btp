package com.snowflake.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.snowflake.annotation.IgnorePermissionPreCheck;
import com.snowflake.filter.CustomizedAuthenticationFilter;
import com.snowflake.handler.ApiRequestPatternsCollector;
import com.snowflake.hook.AuthorizeRequestsCustomizer;
import com.snowflake.permission.PermissionCheck;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;


/**
 * 自定义的 Spring Security 配置适配器实现
 */
@AutoConfiguration
@ConditionalOnMissingBean(SecurityFilterChain.class)
@AutoConfigureAfter(SecurityComponentAutoConfig.class)
@EnableMethodSecurity(securedEnabled = true)
@Slf4j
public class CommonWebSecurityAutoConfig {

    @Resource
    private CommonSecurityProperties commonSecurityProperties;

    /**
     * 认证失败处理类 Bean
     */
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 权限不够处理器 Bean
     */
    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * Token 认证过滤器 Bean
     */
    @Resource
    private CustomizedAuthenticationFilter authenticationTokenFilter;


    /**
     * 自定义的权限映射 Bean 集合，业务侧必须提供实现，用于定制业务侧自己的权限认证配置规则
     *
     */
    @Resource
    private List<AuthorizeRequestsCustomizer> authorizeRequestsCustomizers;

    @Resource
    private ApiRequestPatternsCollector requestPatternsCollector;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置 URL 的安全配置
     *
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        handlePermissionPreCheck();
        // 登出
        httpSecurity
                // 开启跨域
                .cors(Customizer.withDefaults())
                // CSRF 禁用，因为不使用 Session
                .csrf(AbstractHttpConfigurer::disable)
                // 基于 token 机制，所以不需要 Session
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // 一堆自定义的 Spring Security 处理器
                .exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));
        // 登录、登录暂时不使用 Spring Security 的拓展点，主要考虑一方面拓展多用户、多种登录方式相对复杂，一方面用户的学习成本较高

        // 获得 @PermitAll 带来的 URL 列表，免登录
        Multimap<HttpMethod, String> permitAllUrls = obtainMatchedUrlsWithTargetCondition(annotations ->
                Objects.nonNull(annotations) && annotations.contains(PermitAll.class));
        StringBuilder builder = new StringBuilder("\n");
        permitAllUrls.forEach((k, v) -> builder.append(k).append(":").append(v).append("\n"));
        log.info("当前容器标注PermitAll，共有{}个request pattern忽略认证：{}", permitAllUrls.size(), builder);

        // 设置每个请求的权限
        httpSecurity
                // ①：全局共享规则
                .authorizeHttpRequests(c -> c
                        // 1.1 静态资源，可匿名访问
                        .requestMatchers(HttpMethod.GET, "/*.html", "/*.html", "/*.css", "/*.js").permitAll()
                        // 1.2 设置 @PermitAll 无需认证
                        .requestMatchers(HttpMethod.GET, permitAllUrls.get(HttpMethod.GET).toArray(new String[0])).permitAll()
                        .requestMatchers(HttpMethod.POST, permitAllUrls.get(HttpMethod.POST).toArray(new String[0])).permitAll()
                        .requestMatchers(HttpMethod.PUT, permitAllUrls.get(HttpMethod.PUT).toArray(new String[0])).permitAll()
                        .requestMatchers(HttpMethod.DELETE, permitAllUrls.get(HttpMethod.DELETE).toArray(new String[0])).permitAll()
                        .requestMatchers(HttpMethod.HEAD, permitAllUrls.get(HttpMethod.HEAD).toArray(new String[0])).permitAll()
                        .requestMatchers(HttpMethod.PATCH, permitAllUrls.get(HttpMethod.PATCH).toArray(new String[0])).permitAll()
                        .requestMatchers(commonSecurityProperties.getPermitAllUrls().toArray(new String[0])).permitAll()
                )
                .authorizeHttpRequests(c -> authorizeRequestsCustomizers.forEach(customizer -> customizer.customize(c)))
                .authorizeHttpRequests(c -> c.anyRequest().authenticated());

        // 添加 自定义的认证过滤器，并在认证过滤器认证完毕之后添加自定义的认证成功过滤器
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    /**
     * 处理权限预检
     */
    private void handlePermissionPreCheck() {
        if (BooleanUtils.isTrue(commonSecurityProperties.getPreCheckEnable())) {
            Multimap<HttpMethod, String> urlsWithIgnorePermissionPreCheck = obtainMatchedUrlsWithTargetCondition(annotations ->
                    Objects.nonNull(annotations) && annotations.contains(IgnorePermissionPreCheck.class));
            if (!urlsWithIgnorePermissionPreCheck.isEmpty()) {
                StringBuilder ignore = new StringBuilder("\n");
                urlsWithIgnorePermissionPreCheck.forEach((k, v) -> ignore.append(k).append(":").append(v).append("\n"));
                log.info("当前容器，共有{}个request pattern跳过权限预检：{}", urlsWithIgnorePermissionPreCheck.size(), ignore);
            }

            // 开启权限预检，收集触发权限预检的所有request patterns
            Multimap<HttpMethod, String> urlsWithHitPermissionPreCheck = obtainMatchedUrlsWithTargetCondition(annotations ->
                    Objects.nonNull(annotations) &&
                            !annotations.contains(PermissionCheck.class) &&
                            !annotations.contains(PreAuthorize.class) &&
                            !annotations.contains(IgnorePermissionPreCheck.class));
            if (urlsWithHitPermissionPreCheck.isEmpty()) {
                return;
            }
            StringBuilder builder = new StringBuilder("\n");
            urlsWithHitPermissionPreCheck.forEach((k, v) -> builder.append(k).append(":").append(v).append("\n"));


            log.error("当前容器触发权限预检，共有{}个request pattern触发权限预检，请检查权限标注：{}", urlsWithHitPermissionPreCheck.size(), builder);


            throw new IllegalStateException("触发权限预检规则！禁止启动");
        }
    }


    /**
     * 获取符合指定条件的 handler method
     * @param condition 指定条件
     * @return 符合指定条件的 handler method
     */
    private Multimap<HttpMethod, String> obtainMatchedUrlsWithTargetCondition(Predicate<Set<Class<? extends Annotation>>> condition) {
        Objects.requireNonNull(condition);
        Multimap<HttpMethod, String> result = HashMultimap.create();
        Multimap<HttpMethod, MutablePair<String, Set<Class<? extends Annotation>>>> requestPatterns =
                requestPatternsCollector.getRequestPatterns();
        if (Objects.isNull(requestPatterns)) {
            return result;
        }
        for (Map.Entry<HttpMethod, MutablePair<String, Set<Class<? extends Annotation>>>> entry : requestPatterns.entries()) {
            HttpMethod key = entry.getKey();
            MutablePair<String, Set<Class<? extends Annotation>>> pair = entry.getValue();

            Set<Class<? extends Annotation>> annotations = pair.getRight();
            if (condition.test(annotations)) {
                result.put(key, pair.getLeft());
            }
        }
        return result;
    }

}
