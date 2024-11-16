package zeh.btp.feign.core;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import zeh.btp.feign.container.HeaderContext;
import zeh.btp.feign.container.HeaderContextHolder;
import zeh.btp.common.helper.ConsumerHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Objects;

/**
 * @description: 自定义feign配置，该配置默认实现Feign请求拦截器，用于接入feign将请求发送给服务端之前回调执行一些拦截逻辑
 * @author: Erhu.Zhao
 * @create: 2023-10-27 18:29
 */
@Configuration
public class CustomizedFeignConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        penetrateHeaders(requestTemplate, Boolean.FALSE, Boolean.TRUE);
    }

    /**
     * 透传headers
     *
     * @param requestTemplate              requestTemplate
     * @param isPenetrateRequestHeaders    是否透传原始request headers
     * @param isPenetrateCustomizedHeaders 是否透传自定义headers
     */
    private void penetrateHeaders(RequestTemplate requestTemplate, boolean isPenetrateRequestHeaders, boolean isPenetrateCustomizedHeaders) {
        ConsumerHelper.doIt(isPenetrateRequestHeaders, this::penetrateRequestHeaders, requestTemplate);
        ConsumerHelper.doIt(isPenetrateCustomizedHeaders, this::penetrateCustomizedHeaders, requestTemplate);
    }


    /**
     * feign透传原始request的headers
     *
     * @param requestTemplate requestTemplate
     */
    private void penetrateRequestHeaders(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String values = request.getHeader(name);
            requestTemplate.header(name, values);
        }
    }

    /**
     * feign透传自定义headers
     *
     * @param requestTemplate requestTemplate
     */
    private void penetrateCustomizedHeaders(RequestTemplate requestTemplate) {
        try {
            HeaderContext headerContext = HeaderContextHolder.getHeaderContext();
            if (Objects.isNull(headerContext)) {
                return;
            }
            Enumeration<String> headerNames = headerContext.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = headerContext.getHeader(name);
                requestTemplate.header(name, value);
            }
        } finally {
            HeaderContextHolder.clearHeaderContext();
        }
    }

}