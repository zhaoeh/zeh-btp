package zeh.btp.i18n.core;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

/**
 * @description: locale校验器
 * @author: ErHu.Zhao
 * @create: 2024-07-09
 **/
public class LocaleChecker {

    private static final String CHECK_LOCALE = "/locale/check/check-locale";

    public static final String EXCEPTION = "check_exception";

    private LocaleResolver resolver;

    private ValidLocalesProvider validLocalesProvider;

    public LocaleChecker(LocaleResolver resolver, ValidLocalesProvider validLocalesProvider) {
        this.resolver = resolver;
        this.validLocalesProvider = validLocalesProvider;
    }

    /**
     * 校验当前环境的locale是否合法
     *
     * @return 是否合法，如果不合法则抛出异常
     */
    public boolean checkLocale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setDefaultLocale(validLocalesProvider.localeSupports().getDefaultLocale());
        Locale locale = resolver.resolveLocale(request);
        boolean validLocale = validLocalesProvider.localeSupports().getValidLocales().contains(locale);
        // Filter中异常无法被全局异常捕获，为了保证全局异常的国际化，使用请求转发，将异常转发给内部的endPoint进行抛出
        request.setAttribute(EXCEPTION, validLocalesProvider.localeSupports().getExceptionOfCheckFailed());
        if (!validLocale) {
            request.getRequestDispatcher(CHECK_LOCALE).forward(request, response);
        }
        return true;
    }

    private void setDefaultLocale(Locale defaultLocale) {
        if (Objects.nonNull(defaultLocale)) {
            ((AbstractLocaleResolver) resolver).setDefaultLocale(defaultLocale);
        }
    }
}
