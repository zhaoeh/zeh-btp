package com.snowflake.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.NetUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * 客户端工具类
 *
 */
@Slf4j
public class ServletUtils {

    public static final String IPV6_LOCALHOST = "0:0:0:0:0:0:0:1";
    public static final String IP_UNKNOWN = "unknown";

    /**
     * 返回 JSON 字符串
     *
     * @param response 响应
     * @param object   对象，会序列化成 JSON 字符串
     */
    @SuppressWarnings("deprecation")
    public static void writeJSON(HttpServletResponse response, Object object) {
        String content = JsonUtils.toJsonString(object);
        CustomizedServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    /**
     * 返回附件
     *
     * @param response 响应
     * @param filename 文件名
     * @param content  附件内容
     */
    public static void writeAttachment(HttpServletResponse response, String filename, byte[] content) throws IOException {
        // 设置 header 和 contentType
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // 输出附件
        IoUtil.write(response.getOutputStream(), false, content);
    }

    /**
     * @param request 请求
     * @return ua
     */
    public static String getUserAgent(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return ua != null ? ua : "";
    }

    /**
     * 获得请求
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getUserAgent() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return getUserAgent(request);
    }

    public static String getRequestUrl() {
        HttpServletRequest request = getRequest();
        return Objects.isNull(request) ? "" : request.getRequestURI();
    }

    public static String getClientIp() {
        HttpServletRequest request = getRequest();
        return Objects.isNull(request) ? "" : getClientIp(request);
    }

    public static String getRequestUrl(HttpServletRequest request) {
        return Objects.isNull(request) ? "" : request.getRequestURI();
    }

    public static String getClientIp(HttpServletRequest request) {
        return Objects.isNull(request) ? "" : doGetClientIp(request);
    }

    public static String doGetClientIp(HttpServletRequest request) {
        String ip = null;
        try {
            // 优先基于gateway ip header约定获取
            ip = request.getHeader("X-Uid-Client");
            if (NetUtil.isUnknown(ip)) {
                ip = request.getHeader("cf-connecting-ip");
            }
            if (NetUtil.isUnknown(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (NetUtil.isUnknown(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (NetUtil.isUnknown(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (NetUtil.isUnknown(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (NetUtil.isUnknown(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (NetUtil.isUnknown(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (NetUtil.isUnknown(ip)) {
                ip = request.getRemoteAddr();
            }
            // 使用代理，则获取第一个IP地址
            if (StringUtils.hasText(ip) && !IP_UNKNOWN.equalsIgnoreCase(ip) && ip.contains(",")) {
                ip = NetUtil.getMultistageReverseProxyIp(ip);
            }
            if (IPV6_LOCALHOST.equals(ip)) {
                ip = NetUtil.LOCAL_IP;
            }
        } catch (Exception e) {
            log.error("Get ipAddress From Http has error==>{} ", e.getMessage());
        }
        return ip;
    }

}
