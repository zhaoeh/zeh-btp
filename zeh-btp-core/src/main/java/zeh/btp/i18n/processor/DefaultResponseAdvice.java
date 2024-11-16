package zeh.btp.i18n.processor;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @description: 默认的http 响应处理器
 * @author: ErHu.Zhao
 * @create: 2024-08-08
 **/
@ControllerAdvice
public class DefaultResponseAdvice implements ResponseBodyAdvice {
    private boolean supports;

    public void setSupports(boolean supports) {
        this.supports = supports;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (this.supports) {
            if (returnType.getMethod() == null || ResponseEntity.class.isAssignableFrom(returnType.getMethod().getReturnType())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }
}
