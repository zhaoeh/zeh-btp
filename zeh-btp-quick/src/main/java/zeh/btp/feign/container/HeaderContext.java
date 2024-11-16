package zeh.btp.feign.container;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 自定义header上下文容器，用户存储header头信息
 * @author: Erhu.Zhao
 * @create: 2023-10-30 10:09
 */
public class HeaderContext extends ConcurrentHashMap<String, String> {

    public HeaderContext() {
        super();
    }

    /**
     * 获取header names
     *
     * @return header names
     */
    public Enumeration<String> getHeaderNames() {
        return this.keys();
    }

    /**
     * 根据headerName获取header value
     *
     * @param headerName header name
     * @return header value
     */
    public String getHeader(String headerName) {
        return this.get(headerName);
    }
}