package jp.co.futech.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;
import jp.co.futech.framework.web.config.WebProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 过滤 /admin-api、/app-api 等 API 请求的过滤器
 *
 * @author futech.co.jp
 */
@RequiredArgsConstructor
public abstract class ApiRequestFilter extends OncePerRequestFilter {

    protected final WebProperties webProperties;

    /**
     * 是否跳过 doFilterInternal 方法
     *
     * @param request http request
     * @return 是否跳过 doFilterInternal 方法 true：跳过即不执行 false：不跳过即执行
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 只过滤 API 请求的地址：以/admin-api 或者 /app-api 作为前缀的url将执行当前过滤器
        return !StrUtil.startWithAny(request.getRequestURI(), webProperties.getAdminApi().getPrefix(),
                webProperties.getAppApi().getPrefix());
    }

}
