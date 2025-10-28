package com.dynamic.model.core;

import cn.hutool.core.collection.CollUtil;
import com.dynamic.model.config.ModelProperties;
import com.dynamic.model.constant.ModelConstants;
import com.dynamic.model.context.ModelContextHolder;
import com.dynamic.model.enums.BizModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * 动态路由上下文拦截器
 */
@Slf4j
public class ModelContextFilter extends OncePerRequestFilter {

    private boolean skipHeaderCheck = false;

    private final ModelProperties properties;

    private final AntPathMatcher pathMatcher;

    public ModelContextFilter(ModelProperties properties) {
        if (Objects.nonNull(properties) && BooleanUtils.isTrue(properties.getSkipModelHeaderCheck())) {
            skipHeaderCheck = true;
        }
        this.properties = properties;
        this.pathMatcher = new AntPathMatcher();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isIgnoreUrl(request)) {
                // 如果是忽略request model的请求url，则设置全局忽略标记
                ModelContextHolder.setIgnore(true);
            } else {
                String requestModel = request.getHeader(ModelConstants.MODEL_HEADER);
                ModelContextHolder.set(BizModel.fromString(requestModel).orElseGet(() -> {
                    if (skipHeaderCheck) {
                        // 跳过header校验配置下，当header上送值非法，设置默认值CA
                        return BizModel.CA;
                    }
                    throw new IllegalArgumentException("header 中参数：" + ModelConstants.MODEL_HEADER + " 取值非法");
                }));
            }
            filterChain.doFilter(request, response);
        } finally {
            ModelContextHolder.clear();
        }
    }

    /**
     * 是否是忽略请求model的url
     * @param request http request
     * @return 是否是忽略model的request url true：是 false：不是
     */
    private boolean isIgnoreUrl(HttpServletRequest request) {
        // 快速匹配，保证性能
        if (CollUtil.contains(properties.getIgnoreUrls(), request.getRequestURI())) {
            return true;
        }
        // 逐个 Ant 路径匹配
        for (String url : properties.getIgnoreUrls()) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

}
