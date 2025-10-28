package com.assistant.desensitize.handler;


import cn.hutool.core.collection.CollUtil;
import com.assistant.desensitize.annotation.EnableDesensitize;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.*;

import static com.assistant.desensitize.util.CollectionUtils.convertList;

@Slf4j
public class DesensitizeMetadataCollector {

    private Multimap<HttpMethod, String> source;

    private final ApplicationContext applicationContext;

    private final PathPatternParser pathPatternParser = new PathPatternParser();

    private Multimap<HttpMethod, String> cache;

    public DesensitizeMetadataCollector(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initEnableDesensitizeUrls();
    }

    public Multimap<HttpMethod, String> initEnableDesensitizeUrls() {
        if (Objects.nonNull(cache)) {
            return cache;
        }
        Multimap<HttpMethod, String> result = HashMultimap.create();
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
                if (!handlerMethod.hasMethodAnnotation(EnableDesensitize.class)) {
                    continue;
                }

                // 聚合当前业务侧springboot容器中，所有handler method的请求路径模式
                // key：请求路径 value：请求 handler method上收集的注解
                Set<String> urls = new HashSet<>();
                if (entry.getKey().getPatternsCondition() != null) {
                    // 针对springboot2版本
                    Set<String> requestUrls = entry.getKey().getPatternsCondition().getPatterns();
                    if (!CollectionUtils.isEmpty(requestUrls)) {
                        urls.addAll(requestUrls);
                    }
                }
                if (entry.getKey().getPathPatternsCondition() != null) {
                    // 针对springboot3版本
                    List<String> requestUrls = convertList(entry.getKey().getPathPatternsCondition().getPatterns(), PathPattern::getPatternString);
                    if (!CollectionUtils.isEmpty(requestUrls)) {
                        urls.addAll(requestUrls);
                    }
                }

                // 目标handler method不存在任何映射模式，直接跳过
                if (urls.isEmpty()) {
                    continue;
                }

                // 特殊：使用 @RequestMapping 注解，并且未写 method 属性，默认严格模式，需要认证
                Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();
                if (CollUtil.isEmpty(methods)) {
                    result.putAll(HttpMethod.GET, urls);
                    result.putAll(HttpMethod.POST, urls);
                    result.putAll(HttpMethod.PUT, urls);
                    result.putAll(HttpMethod.DELETE, urls);
                    result.putAll(HttpMethod.HEAD, urls);
                    result.putAll(HttpMethod.PATCH, urls);
                    continue;
                }
                // 根据请求方法，添加到 result 结果
                entry.getKey().getMethodsCondition().getMethods().forEach(requestMethod -> {
                    switch (requestMethod) {
                        case GET:
                            result.putAll(HttpMethod.GET, urls);
                            break;
                        case POST:
                            result.putAll(HttpMethod.POST, urls);
                            break;
                        case PUT:
                            result.putAll(HttpMethod.PUT, urls);
                            break;
                        case DELETE:
                            result.putAll(HttpMethod.DELETE, urls);
                            break;
                        case HEAD:
                            result.putAll(HttpMethod.HEAD, urls);
                            break;
                        case PATCH:
                            result.putAll(HttpMethod.PATCH, urls);
                            break;
                    }
                });
            }

        }
        this.cache = result;
        return result;
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


    /**
     * 是否需要脱敏
     * @param request
     * @return
     */
    public boolean needDesensitize(HttpServletRequest request) {
        Multimap<HttpMethod, String> source = initEnableDesensitizeUrls();
        if(Objects.isNull(source)){
            return false;
        }

        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String uri = request.getRequestURI();

        // 根据当前请求的method，和下线供应器提供的下线规则做匹配
        // 一旦当前请求method和uri与目标显现规则的某个api matches，则命中下线黑名单
        Collection<String> patterns = source.get(method);
        if(CollectionUtils.isEmpty(patterns)){
            return false;
        }
        for (String patternStr : patterns) {
            if (!org.springframework.util.StringUtils.hasText(patternStr)) {
                continue;
            }
            PathPattern pattern = pathPatternParser.parse(patternStr);
            if (pattern.matches(PathContainer.parsePath(uri))) {
                return true;
            }
        }
        return false;

    }

}
