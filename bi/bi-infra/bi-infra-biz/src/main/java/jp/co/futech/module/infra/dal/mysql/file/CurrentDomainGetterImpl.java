package jp.co.futech.module.infra.dal.mysql.file;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.futech.framework.file.core.client.CurrentDomainGetter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @description: 当前域名获取器
 * @author: ErHu.Zhao
 * @create: 2024-06-28
 **/
@Service
public class CurrentDomainGetterImpl implements CurrentDomainGetter {

    private String currentDomain;

    /**
     * 初始化当前域名
     */
    private void initCurrentDomain() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (Objects.nonNull(requestAttributes)) {
            request = requestAttributes.getRequest();
        }
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("action of init current domain cannot be used in non-web environments.");
        }
        initDomain(request);
    }

    /**
     * 初始化域名
     *
     * @param request http request
     */
    private void initDomain(HttpServletRequest request) {
        String scheme = request.getScheme();
        String host = request.getHeader("Host");
        currentDomain = scheme + "://" + host;
    }

    @Override
    public String getCurrentDomain() {
        if (Objects.isNull(currentDomain)) {
            initCurrentDomain();
        }
        return currentDomain;
    }
}
