package com.snowflake.handler;


import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.snowflake.annotation.IgnorePermissionPreCheck;
import com.snowflake.permission.PermissionCheck;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

import static com.snowflake.utils.CollectionUtils.convertList;

public class ApiRequestPatternsCollector {

    private final ApplicationContext applicationContext;

    private Multimap<HttpMethod, MutablePair<String, Set<Class<? extends Annotation>>>> cache;

    public ApiRequestPatternsCollector(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Multimap<HttpMethod, MutablePair<String, Set<Class<? extends Annotation>>>> getRequestPatterns() {
        if (Objects.nonNull(cache)) {
            return cache;
        }
        Multimap<HttpMethod, MutablePair<String, Set<Class<? extends Annotation>>>> result = HashMultimap.create();
        // 获得接口对应的 HandlerMethod 集合
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();

        String mainClassPackage = getMainClassBasePackage();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {

            HandlerMethod handlerMethod = entry.getValue();
            String targetHandlerPackageName = handlerMethod.getBeanType().getPackageName();

            // 只收集业务侧包下的所有handler method request mapping patterns
            if (StringUtils.startsWith(targetHandlerPackageName, mainClassPackage)) {


                // 聚合当前业务侧springboot容器中，所有handler method的请求路径模式
                // key为配置的请求路径模式，value为该请求url是否放行，默认为false
                Set<MutablePair<String, Set<Class<? extends Annotation>>>> requestPatterns = new HashSet<>();
                if (entry.getKey().getPatternsCondition() != null) {
                    // 针对springboot2版本
                    Set<String> requestUrls = entry.getKey().getPatternsCondition().getPatterns();
                    if (!CollectionUtils.isEmpty(requestUrls)) {
                        requestPatterns.addAll(requestUrls.stream().map(url -> {
                            Set<Class<? extends Annotation>> targetUrlAnnotations = new HashSet<>();
                            return MutablePair.of(url, targetUrlAnnotations);
                        }).collect(Collectors.toSet()));
                    }
                }
                if (entry.getKey().getPathPatternsCondition() != null) {
                    // 针对springboot3版本
                    List<String> requestUrls = convertList(entry.getKey().getPathPatternsCondition().getPatterns(), PathPattern::getPatternString);
                    if (!CollectionUtils.isEmpty(requestUrls)) {
                        requestPatterns.addAll(requestUrls.stream().map(url -> {
                            Set<Class<? extends Annotation>> targetUrlAnnotations = new HashSet<>();
                            return MutablePair.of(url, targetUrlAnnotations);
                        }).collect(Collectors.toSet()));
                    }
                }

                // 目标handler method不存在任何映射模式，直接跳过
                if (requestPatterns.isEmpty()) {
                    continue;
                }

                // 获取目标映射处理器方法
                handlePermissionCheck(handlerMethod, requestPatterns);
                handlePermitAll(handlerMethod, requestPatterns);
                handlePreAuthorize(handlerMethod, requestPatterns);
                handleIgnorePermissionPreCheck(handlerMethod, requestPatterns);

                // 特殊：使用 @RequestMapping 注解，并且未写 method 属性，默认严格模式，需要认证
                Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();
                if (CollUtil.isEmpty(methods)) {
                    result.putAll(HttpMethod.GET, requestPatterns);
                    result.putAll(HttpMethod.POST, requestPatterns);
                    result.putAll(HttpMethod.PUT, requestPatterns);
                    result.putAll(HttpMethod.DELETE, requestPatterns);
                    result.putAll(HttpMethod.HEAD, requestPatterns);
                    result.putAll(HttpMethod.PATCH, requestPatterns);
                    continue;
                }
                // 根据请求方法，添加到 result 结果
                entry.getKey().getMethodsCondition().getMethods().forEach(requestMethod -> {
                    switch (requestMethod) {
                        case GET:
                            result.putAll(HttpMethod.GET, requestPatterns);
                            break;
                        case POST:
                            result.putAll(HttpMethod.POST, requestPatterns);
                            break;
                        case PUT:
                            result.putAll(HttpMethod.PUT, requestPatterns);
                            break;
                        case DELETE:
                            result.putAll(HttpMethod.DELETE, requestPatterns);
                            break;
                        case HEAD:
                            result.putAll(HttpMethod.HEAD, requestPatterns);
                            break;
                        case PATCH:
                            result.putAll(HttpMethod.PATCH, requestPatterns);
                            break;
                    }
                });
            }

        }
        this.cache = result;
        return result;
    }

    /**
     * 处理handler method PermitAll注解
     * @param handlerMethod handler method
     * @param requestPatterns handler method对应的请求映射器模式集
     */
    private void handlePermitAll(HandlerMethod handlerMethod, Set<MutablePair<String, Set<Class<? extends Annotation>>>> requestPatterns) {
        // 处理 @PermitAll注解
        if (handlerMethod.hasMethodAnnotation(PermitAll.class)) {
            // 存在permitAll注解的api，设置模式为true
            requestPatterns.forEach(p -> {
                Set<Class<? extends Annotation>> currentHandlerMethodAnnotations = p.getRight();
                if (Objects.isNull(currentHandlerMethodAnnotations)) {
                    currentHandlerMethodAnnotations = new HashSet<>();
                    p.setRight(currentHandlerMethodAnnotations);
                }
                currentHandlerMethodAnnotations.add(PermitAll.class);
            });
        }
    }


    /**
     * 处理handler method PermissionCheck注解
     * @param handlerMethod handler method
     * @param requestPatterns handler method对应的请求映射器模式集
     */
    private void handlePermissionCheck(HandlerMethod handlerMethod, Set<MutablePair<String, Set<Class<? extends Annotation>>>> requestPatterns) {
        // 处理 @PermitAll注解
        if (handlerMethod.hasMethodAnnotation(PermissionCheck.class)) {
            // 存在permitAll注解的api，设置模式为true
            requestPatterns.forEach(p -> {
                Set<Class<? extends Annotation>> currentHandlerMethodAnnotations = p.getRight();
                if (Objects.isNull(currentHandlerMethodAnnotations)) {
                    currentHandlerMethodAnnotations = new HashSet<>();
                    p.setRight(currentHandlerMethodAnnotations);
                }
                currentHandlerMethodAnnotations.add(PermissionCheck.class);
            });
        }
    }


    /**
     * 处理handler method PreAuthorize注解
     * @param handlerMethod handler method
     * @param requestPatterns handler method对应的请求映射器模式集
     */
    private void handlePreAuthorize(HandlerMethod handlerMethod, Set<MutablePair<String, Set<Class<? extends Annotation>>>> requestPatterns) {
        // 处理 @PermitAll注解
        if (handlerMethod.hasMethodAnnotation(PreAuthorize.class)) {
            // 存在permitAll注解的api，设置模式为true
            requestPatterns.forEach(p -> {
                Set<Class<? extends Annotation>> currentHandlerMethodAnnotations = p.getRight();
                if (Objects.isNull(currentHandlerMethodAnnotations)) {
                    currentHandlerMethodAnnotations = new HashSet<>();
                    p.setRight(currentHandlerMethodAnnotations);
                }
                currentHandlerMethodAnnotations.add(PreAuthorize.class);
            });
        }
    }

    /**
     * 处理handler method IgnorePermissionPreCheck注解
     * @param handlerMethod handler method
     * @param requestPatterns handler method对应的请求映射器模式集
     */
    private void handleIgnorePermissionPreCheck(HandlerMethod handlerMethod, Set<MutablePair<String, Set<Class<? extends Annotation>>>> requestPatterns) {
        // 处理 @PermitAll注解
        if (handlerMethod.hasMethodAnnotation(IgnorePermissionPreCheck.class)) {
            // 存在permitAll注解的api，设置模式为true
            requestPatterns.forEach(p -> {
                Set<Class<? extends Annotation>> currentHandlerMethodAnnotations = p.getRight();
                if (Objects.isNull(currentHandlerMethodAnnotations)) {
                    currentHandlerMethodAnnotations = new HashSet<>();
                    p.setRight(currentHandlerMethodAnnotations);
                }
                currentHandlerMethodAnnotations.add(IgnorePermissionPreCheck.class);
            });
        }
    }


    /**
     * 获取业务侧springboot主启动类class对象
     * @return springboot主启动类class
     */
    public Class<?> getSpringBootMainClass() {
        // 从所有 Bean 中找出带 @SpringBootApplication 的类
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : allBeanNames) {
            Object bean = applicationContext.getBean(beanName);
            Class<?> clazz = bean.getClass();
            if (AnnotationUtils.findAnnotation(clazz, SpringBootConfiguration.class) != null ||
                    AnnotationUtils.findAnnotation(clazz, SpringBootApplication.class) != null) {
                return clazz;
            }
        }
        return null;
    }


    /**
     * 获取业务侧主启动类所在主包路径
     * @return 业务侧主包路径
     */
    public String getMainClassBasePackage() {
        Class<?> mainClass = getSpringBootMainClass();
        if (Objects.isNull(mainClass)) {
            throw new IllegalStateException("非springboot环境，禁止启动！");
        }
        return mainClass.getPackageName();
    }

}
