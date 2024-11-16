package zeh.btp.i18n.filter;

import zeh.btp.i18n.core.LocaleChecker;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @description: Locale合法性校验过滤器，在过滤器中强制调用LocaleResolver解析locale，然后做合法性校验
 * <p>
 * 为什么非在过滤器中做？因为过滤器是最早被执行的，应该优先校验。如果在AOP中做，请求已经进入到Dispatcher了，校验太延迟。如果在拦截器中做，请求同样进入到Dispatcher了，校验太晚。
 * @author: ErHu.Zhao
 * @create: 2024-07-09
 **/
public class LocaleCheckFilter extends OncePerRequestFilter {

    private LocaleChecker checkLocale;

    public LocaleCheckFilter(LocaleChecker checkLocale) {
        this.checkLocale = checkLocale;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.checkLocale.checkLocale(request, response);
        filterChain.doFilter(request, response);
    }
}
