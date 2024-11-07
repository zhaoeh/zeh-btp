package jp.co.futech.framework.tenant.core.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.co.futech.framework.common.enums.RpcConstants;
import jp.co.futech.framework.common.exception.enums.GlobalErrorCodeConstants;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.common.util.servlet.ServletUtils;
import jp.co.futech.framework.security.core.LoginUser;
import jp.co.futech.framework.security.core.util.SecurityFrameworkUtils;
import jp.co.futech.framework.tenant.config.TenantProperties;
import jp.co.futech.framework.tenant.core.context.TenantContextHolder;
import jp.co.futech.framework.tenant.core.service.TenantFrameworkService;
import jp.co.futech.framework.web.config.WebProperties;
import jp.co.futech.framework.web.core.filter.ApiRequestFilter;
import jp.co.futech.framework.web.core.handler.GlobalExceptionHandler;
import jp.co.futech.framework.web.core.holder.TransmittableLocaleHolder;
import jp.co.futech.framework.web.core.util.WebFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 多租户 Security Web 过滤器
 * 1. 如果是登陆的用户，校验是否有权限访问该租户，避免越权问题。
 * 2. 如果请求未带租户的编号，检查是否是忽略的 URL，否则也不允许访问。
 * 3. 校验租户是合法，例如说被禁用、到期
 *
 * 校验用户访问的租户，是否是其所在的租户，
 *
 * @author futech.co.jp
 */
@Slf4j
public class TenantSecurityWebFilter extends ApiRequestFilter {

    private final TenantProperties tenantProperties;

    private final AntPathMatcher pathMatcher;

    private final GlobalExceptionHandler globalExceptionHandler;
    private final TenantFrameworkService tenantFrameworkService;

    private final List<String> ignoreTenantUrls;

    public TenantSecurityWebFilter(TenantProperties tenantProperties,
                                   WebProperties webProperties,
                                   GlobalExceptionHandler globalExceptionHandler,
                                   TenantFrameworkService tenantFrameworkService, List<String> ignoreTenantUrls) {
        super(webProperties);
        this.tenantProperties = tenantProperties;
        this.ignoreTenantUrls = ignoreTenantUrls;
        this.pathMatcher = new AntPathMatcher();
        this.globalExceptionHandler = globalExceptionHandler;
        this.tenantFrameworkService = tenantFrameworkService;
    }

    /**
     * 覆写父类的shouldNotFilter方法，自定义当前过滤器的过滤规则
     *
     * @param request http request
     * @return 是否跳过doFilterInternal方法 true：跳过即不执行doFilterInternal  false：不跳过即执行doFilterInternal
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 以 /admin-api 或者 /app-api 或者 /rpc-api 作为前缀的url，将执行当前过滤器
        return super.shouldNotFilter(request) &&
                !StrUtil.startWithAny(request.getRequestURI(), RpcConstants.RPC_API_PREFIX); // 因为 RPC API 也会透传租户编号
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try{
//            String api = request.getRequestURI();
//            String accept = Optional.ofNullable(TransmittableLocaleHolder.getLocale()).orElseGet(() -> request.getHeader(WebFrameworkUtils.ACCEPT_LANGUAGE));
//            TransmittableLocaleHolder.setLocale(accept);
//
//            Long tenantId = TenantContextHolder.getTenantId();
//            boolean isRpcRequest = WebFrameworkUtils.isRpcRequest(request);
//            // 1. 登陆的用户，校验是否有权限访问该租户，避免越权问题。
//            LoginUser user = SecurityFrameworkUtils.getLoginUser();
//            if (user != null) {
//                // 如果获取不到租户编号，则尝试使用登陆用户的租户编号
//                if (tenantId == null) {
//                    tenantId = user.getTenantId();
//                    TenantContextHolder.setTenantId(tenantId);
//                    // 如果传递了租户编号，则进行比对租户编号，避免越权问题
//                } else if (!Objects.equals(user.getTenantId(), TenantContextHolder.getTenantId())
//                        && !isRpcRequest) { // Cloud 特殊逻辑：如果是 RPC 请求，就不校验了。主要考虑，一些场景下，会调用 TenantUtils 去切换租户
//                    log.error("[doFilterInternal][租户({}) User({}/{}) 越权访问租户({}) URL({}/{})]",
//                            user.getTenantId(), user.getId(), user.getUserType(),
//                            TenantContextHolder.getTenantId(), request.getRequestURI(), request.getMethod());
//                    ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN.getCode(),
//                            "您无权访问该租户的数据"));
//                    return;
//                }
//            }
//
//            // 如果非允许忽略租户的 URL，则校验租户是否合法
//            if (!isIgnoreUrl(request)) {
//                // 2. 如果请求未带租户的编号，不允许访问。
//                if (tenantId == null) {
//                    log.error("[doFilterInternal][URL({}/{}) 未传递租户编号]", request.getRequestURI(), request.getMethod());
//                    ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(),
//                            "请求的租户标识未传递，请进行排查"));
//                    return;
//                }
//                // 3. 校验租户是合法，例如说被禁用、到期
//                try {
//                    tenantFrameworkService.validTenant(tenantId);
//                } catch (Throwable ex) {
//                    CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
//                    ServletUtils.writeJSON(response, result);
//                    return;
//                }
//            } else { // 如果是允许忽略租户的 URL，若未传递租户编号，则默认忽略租户编号，避免报错
//                if (tenantId == null) {
//                    TenantContextHolder.setIgnore(true);
//                }
//            }

            // 继续过滤
            chain.doFilter(request, response);
        } finally {
            TransmittableLocaleHolder.clear();
        }
    }

    private boolean isIgnoreUrl(HttpServletRequest request) {
        if (CollUtil.contains(ignoreTenantUrls, request.getRequestURI())) {
            return true;
        }
        // 快速匹配，保证性能
        if (CollUtil.contains(tenantProperties.getIgnoreUrls(), request.getRequestURI())) {
            return true;
        }
        // 逐个 Ant 路径匹配
        for (String url : tenantProperties.getIgnoreUrls()) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

}
