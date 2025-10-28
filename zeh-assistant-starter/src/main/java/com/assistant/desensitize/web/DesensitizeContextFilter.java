package com.assistant.desensitize.web;

import com.assistant.desensitize.condition.DesensitizeCondition;
import com.assistant.desensitize.core.DesensitizeContext;
import com.assistant.desensitize.handler.DesensitizeMetadataCollector;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 脱敏上下文拦截器
 */
@Slf4j
public class DesensitizeContextFilter extends OncePerRequestFilter {

    private final DesensitizeMetadataCollector desensitizeMetadataCollector;

    private final DesensitizeCondition condition;

    public DesensitizeContextFilter(DesensitizeMetadataCollector desensitizeMetadataCollector, DesensitizeCondition condition) {
        this.desensitizeMetadataCollector = desensitizeMetadataCollector;
        this.condition = condition;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 设置上下文
            boolean need = desensitizeMetadataCollector.needDesensitize(request);
            if (!need) {
                DesensitizeContext.setSkip(true);
                filterChain.doFilter(request, response);
                return;
            }
            DesensitizeContext.setSkip(condition.shouldSkip());
            filterChain.doFilter(request, response);
        } finally {
            DesensitizeContext.clear();
        }
    }
}
