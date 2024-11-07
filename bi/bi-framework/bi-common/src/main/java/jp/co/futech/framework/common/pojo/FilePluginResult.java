package jp.co.futech.framework.common.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.futech.framework.common.exception.ErrorCode;
import jp.co.futech.framework.common.exception.enums.GlobalErrorCodeConstants;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 针对文件插件上传特制响应结构
 * @author: ErHu.Zhao
 * @create: 2024-07-01
 **/
@Data
@Builder
public class FilePluginResult implements Serializable {

    /**
     * 错误码
     *
     * @see ErrorCode#getCode()
     */
    @JsonProperty(value = "errno")
    private Integer code;
    /**
     * 返回数据
     */
    private List<FilePlugin> data;

    @Builder
    @Data
    public static class FilePlugin {

        private String url;

        private String alt;

        private String href;
    }


    public static FilePluginResult success(String url) {
        return FilePluginResult.builder().code(GlobalErrorCodeConstants.SUCCESS.getCode()).data(List.of(FilePlugin.builder().url(url).build())).build();
    }

    public static FilePluginResult success(String url, String alt) {
        return FilePluginResult.builder().code(GlobalErrorCodeConstants.SUCCESS.getCode()).data(List.of(FilePlugin.builder().url(url).alt(alt).build())).build();
    }

    public static FilePluginResult success(String url, String alt, String href) {
        return FilePluginResult.builder().code(GlobalErrorCodeConstants.SUCCESS.getCode()).data(List.of(FilePlugin.builder().url(url).alt(alt).href(href).build())).build();
    }

    public static FilePluginResult success(FilePlugin plugin) {
        return FilePluginResult.builder().code(GlobalErrorCodeConstants.SUCCESS.getCode()).data(List.of(plugin)).build();
    }

    public static FilePluginResult success(List<FilePlugin> plugins) {
        return FilePluginResult.builder().code(GlobalErrorCodeConstants.SUCCESS.getCode()).data(plugins).build();
    }

}
