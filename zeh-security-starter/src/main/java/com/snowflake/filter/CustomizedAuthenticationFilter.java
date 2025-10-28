package com.snowflake.filter;

import com.snowflake.LoginUser;
import com.snowflake.config.CommonSecurityProperties;
import com.snowflake.exception.InnerErrorCode;
import com.snowflake.handler.GlobalExceptionHandler;
import com.snowflake.hook.ApiOfflineProcessor;
import com.snowflake.hook.CustomizedAuthenticationProvider;
import com.snowflake.hook.WhenAuthenticationSuccess;
import com.snowflake.pojo.CommonResult;
import com.snowflake.utils.SecurityFrameworkUtils;
import com.snowflake.utils.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.snowflake.enums.GlobalErrorCodeConstants.OFFLINE;

/**
 * 分布式认证服务核心过滤器，专职用于认证
 */
@RequiredArgsConstructor
@Slf4j
public class CustomizedAuthenticationFilter extends OncePerRequestFilter {

    // 下游自定义过滤器中识别的认证失败异常信息标记
    public static final String CUSTOMIZED_AUTH_FAIL_MSG = "CustomizedAuthFailMsg";

    // 下游自定义过滤器中识别的授权失败异常信息标记
    public static final String CUSTOMIZED_ACCESS_DINED_MSG = "CustomizedAccessDinedMsg";

    /**
     * api路径匹配器
     */
    private final PathPatternParser pathPatternParser = new PathPatternParser();

    private final CommonSecurityProperties commonSecurityProperties;

    private final GlobalExceptionHandler globalExceptionHandler;

    private final ObjectProvider<CustomizedAuthenticationProvider> authenticationProviders;

    /**
     * 接口下线处理器
     */
    private final ObjectProvider<ApiOfflineProcessor> apiOfflineProcessorObjectProvider;

    private final WhenAuthenticationSuccess whenAuthenticationSuccess;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (needToOffline(request)) {
            CommonResult<InnerErrorCode> accessDenied = CommonResult.error(OFFLINE);
            String requestUrl = request.getRequestURI();
            log.error("访问 URL:{} ，该功能已下线！响应体：{}", requestUrl, accessDenied);
            // 返回 403
            ServletUtils.writeJSON(response, accessDenied);
            return;
        }

        // 场景1:优先获取业务侧提供的认证对象（为了适配业务侧使用集中式认证中心进行独立的认证服务）
        LoginUser loginUser = findLoginUserByProvider(request, response);

        // 场景2:基于分布式认证授权jar的内部约定，从jwt token获取当前认证用户
        if (Objects.isNull(loginUser)) {

            // 此处，可以基于当前分布式jar和前端的header约定，内部解析JWT并做校验，生成经过认证的内部登录用户
            // todo:等待实现，为了进度且兼容当前代理系统和彩票系统集中式认证的逻辑，此处暂时先不做，完全由业务侧提供用户认证凭证
        }

        // 一旦认证成功，则设置当前用户到security上下文中；后续一旦security检测到认证失败，会回调
        if (loginUser != null) {
            try {
                SecurityFrameworkUtils.setLoginUser(loginUser, request);
                // 认证成功回调处理，业务侧可在此处添加一些认证成功后置逻辑，比如统一校验账号级别的ip白名单等
                // 应用账号级别的白名单应该属于授权范畴，但要是放在授权注解结合api级别去做，显得冗余，因此，专门封装此认证成功回调器，供业务侧进入到授权流程后统一处理某些事项
                whenAuthenticationSuccess.doSome(request, response, Objects.toString(loginUser.getAccount()), loginUser.getPlatForm());
            } catch (Exception e) {
                whenAuthenticationSuccess.afterComplete(request, response, Objects.toString(loginUser.getAccount()), loginUser.getPlatForm());
                if (e instanceof AccessDeniedException) {
                    request.setAttribute(CUSTOMIZED_ACCESS_DINED_MSG, e);
                    SecurityContextHolder.clearContext();
                } else if (e instanceof AuthenticationException) {
                    request.setAttribute(CUSTOMIZED_AUTH_FAIL_MSG, e);
                    SecurityContextHolder.clearContext();
                } else {
                    // 其他异常，抛出，脱离security认证授权链路
                    throw e;
                }
            }
        }
        try {
            // 继续过滤链，上游认证授权异常统一交给security进行最终决策认证
//            log.info("过滤链打印请求:{}",request.getRequestURI());
            chain.doFilter(request, response);
        } finally {
            if (Objects.nonNull(loginUser)) {
                whenAuthenticationSuccess.afterComplete(request, response, Objects.toString(loginUser.getAccount()), loginUser.getPlatForm());
            }
        }

    }

    /**
     * 当前请求是否需要处理下线
     * @param request
     * @return
     */
    private boolean needToOffline(HttpServletRequest request) {
        ApiOfflineProcessor offlineProcessor = apiOfflineProcessorObjectProvider.getIfAvailable();
        if (Objects.nonNull(offlineProcessor)) {
            // 业务侧提供的下线接口清单
            Map<HttpMethod, List<String>> offlineApiList = offlineProcessor.supplyOfflineList();
            if (CollectionUtils.isEmpty(offlineApiList)) {
                return false;
            }
            HttpMethod method = HttpMethod.valueOf(request.getMethod());
            String uri = request.getRequestURI();
            return isHitOffline(offlineApiList, method, uri);
        }
        return false;
    }

    /**
     * 当前请地址是否命中下线黑名单列表
     * @param offlineApiList
     * @param method
     * @param uri
     * @return
     */
    private boolean isHitOffline(Map<HttpMethod, List<String>> offlineApiList, HttpMethod method, String uri) {
        // 根据当前请求的method，和下线供应器提供的下线规则做匹配
        // 一旦当前请求method和uri与目标显现规则的某个api matches，则命中下线黑名单
        List<String> patterns = offlineApiList.getOrDefault(method, List.of());
        for (String patternStr : patterns) {
            if(!StringUtils.hasText(patternStr)){
                continue;
            }
            PathPattern pattern = pathPatternParser.parse(patternStr);
            if (pattern.matches(PathContainer.parsePath(uri))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过业务侧实现的认证供应器提供认证后的内部对象
     * @return 经过认证的内部对象
     */
    private LoginUser findLoginUserByProvider(HttpServletRequest request, HttpServletResponse response) {
        CustomizedAuthenticationProvider provider = authenticationProviders.getIfAvailable();
        if (Objects.nonNull(provider)) {
            return provider.supplyAuthentication(request, response);
        }
        return null;
    }
}