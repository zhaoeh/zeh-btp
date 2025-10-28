package com.snowflake.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.snowflake.enums.GlobalErrorCodeConstants;
import com.snowflake.exception.InnerBusinessException;
import com.snowflake.exception.InnerServiceException;
import com.snowflake.hook.AuthAccessFailProcessor;
import com.snowflake.pojo.AuthAccessFailInfo;
import com.snowflake.pojo.CommonResult;
import com.snowflake.pojo.FailType;
import com.snowflake.utils.ServiceExceptionUtil;
import com.snowflake.utils.ServletUtils;
import com.snowflake.utils.WebFrameworkUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;
import java.util.Set;

import static com.snowflake.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static com.snowflake.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 分布式认证授权jar内部的全局异常处理器
 */
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * 认证或者授权失败处理器钩子
     */
    private final AuthAccessFailProcessor processor;

    private AuthAccessFailHandler handler;

    /**
     * 忽略的 ServiceException 错误提示，避免打印过多 logger
     */
    public static final Set<String> IGNORE_ERROR_MESSAGES = Set.of("无效的刷新令牌");

    public GlobalExceptionHandler(ObjectProvider<AuthAccessFailProcessor> accessFailProcessorObjectProvider, ObjectProvider<AuthAccessFailHandler> handlerObjectProvider) {
        this.processor = accessFailProcessorObjectProvider.getIfAvailable();
        this.handler = handlerObjectProvider.getIfAvailable();
        if (Objects.isNull(this.handler)) {
            this.handler = new AuthAccessFailHandler();
        }
    }

    /**
     * 处理所有异常，主要是提供给 Filter 使用
     * 因为 Filter 不走 SpringMVC 的流程，但是我们又需要兜底处理异常，所以这里提供一个全量的异常处理过程，保持逻辑统一。
     *
     * @param request 请求
     * @param ex 异常
     * @return 通用返回
     */
    public CommonResult<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return methodArgumentTypeMismatchExceptionHandler((MethodArgumentTypeMismatchException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionExceptionHandler((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return bindExceptionHandler((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) ex);
        }
        if (ex instanceof ValidationException) {
            return validationException((ValidationException) ex);
        }
        if (ex instanceof NoHandlerFoundException) {
            return noHandlerFoundExceptionHandler((NoHandlerFoundException) ex);
        }
//        if (ex instanceof NoResourceFoundException) {
//            return noResourceFoundExceptionHandler(request, (NoResourceFoundException) ex);
//        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return httpRequestMethodNotSupportedExceptionHandler((HttpRequestMethodNotSupportedException) ex);
        }
        if (ex instanceof InnerServiceException) {
            return serviceExceptionHandler((InnerServiceException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionHandler(request, (AccessDeniedException) ex);
        }
        return defaultExceptionHandler(request, ex);
    }


    @ExceptionHandler(value = InnerBusinessException.class)
    public CommonResult<?> businessExceptionHandler(InnerBusinessException e) {
        String causeDesc = e.buildCauseDesc();
        if (e.isPrintStack() || StringUtils.isNotBlank(causeDesc)) {
            // 选择性打印业务级异常
            HttpServletRequest request = ServletUtils.getRequest();
            String requestUrl = ServletUtils.getRequestUrl(request);
            String clientIp = ServletUtils.getClientIp(request);
            Long userId = WebFrameworkUtils.getLoginUserId(request);
            String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
            if (e.isPrintStack()) {
                if (StringUtils.isNotBlank(causeDesc)) {
                    log.error("[innerBusinessException] userId:{} userAccount:{} clientIp:{} 请求：{}，cause:{}，发生业务异常，e:", userId, userAccount, clientIp, requestUrl, causeDesc, e);
                } else {
                    log.error("[innerBusinessException] userId:{} userAccount:{} clientIp:{} 请求：{}，发生业务异常，e:", userId, userAccount, clientIp, requestUrl, e);
                }
            } else {
                log.error("[innerBusinessException] userId:{} userAccount:{} clientIp:{} 请求：{} 发生业务异常，error:{}", userId, userAccount, clientIp, requestUrl, causeDesc);
            }
        }
        return CommonResult.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     *
     * 比如：接口上设置了 @RequestParam("xx") 参数，实际上并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[missingServletRequestParameterExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     *
     * 比如：接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResult<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[methodArgumentTypeMismatchExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", ex.getMessage()));
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[methodArgumentNotValidExceptionExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<?> bindExceptionHandler(BindException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[handleBindException] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null; // 断言，避免告警
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     *
     * 比如，接口上设置了 @RequestBody实体中 xx 属性类型为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult<?> methodArgumentTypeInvalidFormatExceptionHandler(HttpMessageNotReadableException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[methodArgumentTypeInvalidFormatExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
            return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", invalidFormatException.getValue()));
        } else {
            return defaultExceptionHandler(ServletUtils.getRequest(), ex);
        }
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[constraintViolationExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }

    /**
     * 处理 Dubbo Consumer 本地参数校验时，抛出的 ValidationException 异常
     */
    @ExceptionHandler(value = ValidationException.class)
    public CommonResult<?> validationException(ValidationException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[constraintViolationExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        // 无法拼接明细的错误信息，因为 Dubbo Consumer 抛出 ValidationException 异常时，是直接的字符串信息，且人类不可读
        return CommonResult.error(BAD_REQUEST);
    }

    /**
     * 处理 SpringMVC 请求地址不存在
     *
     * 注意，它需要设置如下两个配置项：
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResult<?> noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[noHandlerFoundExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        return CommonResult.error(GlobalErrorCodeConstants.NOT_FOUND.getCode(), String.format("请求地址不存在:%s", ex.getRequestURL()));
    }


    /**
     * 处理 SpringMVC 请求方法不正确
     *
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestUrl = ServletUtils.getRequestUrl(request);
        String clientIp = ServletUtils.getClientIp(request);
        Long userId = WebFrameworkUtils.getLoginUserId(request);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(request);
        log.error("[httpRequestMethodNotSupportedExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} error，e:", userId, userAccount, clientIp, requestUrl, ex);
        return CommonResult.error(GlobalErrorCodeConstants.METHOD_NOT_ALLOWED.getCode(), String.format("请求方法不正确:%s", ex.getMessage()));
    }

    /**
     * 处理 Spring Security 权限不足的异常
     *
     * 来源是，使用 @PreAuthorize 注解，AOP 进行权限拦截
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public CommonResult<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
        Long userId = WebFrameworkUtils.getLoginUserId(req);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(req);
        String requestUrl = req.getRequestURL().toString();
        String clientIp = ServletUtils.getClientIp(req);
        log.error("accessDeniedExceptionHandler，userId:{} account:{} clientIp:{} 越权访问 url:{}，e:", userId, userAccount, clientIp, requestUrl, ex);
        String accessDinedMsg = StringUtils.substring(WebFrameworkUtils.getAccessDinedMsg(req), 0, 2000);
        String sysMsg = StringUtils.substring(ex.getMessage(), 0, 2000);
        ProcessDelegate.doProcess(processor, handler, req,
                AuthAccessFailInfo.builder().
                        failType(FailType.ACCESS_FAIL).
                        userId(userId).userAccount(userAccount).requestUrl(requestUrl).
                        businessCause(accessDinedMsg).sysCause(sysMsg).build());
        return CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public CommonResult<?> accessAuthenticationExceptionHandler(HttpServletRequest req, AuthenticationException ex) {
        Long userId = WebFrameworkUtils.getLoginUserId(req);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(req);
        String requestUrl = req.getRequestURL().toString();
        String clientIp = ServletUtils.getClientIp(req);
        log.error("accessAuthenticationExceptionHandler，userId:{} account:{} 认证失败，clientIp:{} 无法访问 url:{}，e:",
                userId, userAccount, clientIp, requestUrl, ex);
        String accessDinedMsg = StringUtils.substring(WebFrameworkUtils.getAccessDinedMsg(req), 0, 2000);
        String sysMsg = StringUtils.substring(ex.getMessage(), 0, 2000);
        ProcessDelegate.doProcess(processor, handler, req,
                AuthAccessFailInfo.builder().
                        failType(FailType.AUTH_FAIL).
                        businessCause(accessDinedMsg).sysCause(sysMsg).build());
        return CommonResult.error(GlobalErrorCodeConstants.UNAUTHORIZED);
    }


    @ExceptionHandler(value = InnerServiceException.class)
    public CommonResult<?> serviceExceptionHandler(InnerServiceException ex) {
        // 不包含的时候，才进行打印，避免 ex 堆栈过多
        if (!IGNORE_ERROR_MESSAGES.contains(ex.getMessage())) {
            // 即使打印，也只打印第一层 StackTraceElement，并且使用 warn 在控制台输出，更容易看到
            try {
                StackTraceElement[] stackTraces = ex.getStackTrace();
                for (StackTraceElement stackTrace : stackTraces) {
                    if (!Objects.equals(stackTrace.getClassName(), ServiceExceptionUtil.class.getName())) {
                        log.warn("[serviceExceptionHandler]\n\t{}", stackTrace);
                        break;
                    }
                }
            } catch (Exception ignored) {
                // 忽略日志，避免影响主流程
            }
        }
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        String requestUrl = ServletUtils.getRequestUrl(req);
        String clientIp = ServletUtils.getClientIp(req);
        Long userId = WebFrameworkUtils.getLoginUserId(req);
        String userAccount = WebFrameworkUtils.getLoginUserAccount(req);
        log.error("[defaultExceptionHandler] userId:{} userAccount:{} clientIp:{} 请求：{} 发生系统异常，e:", userId, userAccount, clientIp, requestUrl, ex);
        // 返回 ERROR CommonResult
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMsg());
    }

}
