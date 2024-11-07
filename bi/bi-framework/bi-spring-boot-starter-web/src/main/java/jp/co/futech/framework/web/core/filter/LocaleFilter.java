package jp.co.futech.framework.web.core.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.futech.framework.web.core.holder.TransmittableLocaleHolder;
import jp.co.futech.framework.web.core.util.WebFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-09-10
 **/
@Slf4j
public class LocaleFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accept = request.getHeader(WebFrameworkUtils.ACCEPT_LANGUAGE);
            TransmittableLocaleHolder.setLocale(accept);
            filterChain.doFilter(request, response);
        } finally {
            TransmittableLocaleHolder.clear();
        }
    }
}
