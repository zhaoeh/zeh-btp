package jp.co.futech.framework.web.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * web config属性配置类，以环境变量中 bi.web 作为前缀的属性进行注入
 */
@ConfigurationProperties(prefix = "bi.web")
@Validated
@Data
public class WebProperties {

    /**
     * 构建 /app-api 前缀对象以及要扫描的目标包（目标包采用ant表达式语言模糊匹配）
     */
    @NotNull(message = "APP API 不能为空")
    private Api appApi = new Api("/app-api", "**.controller.app.**");

    /**
     * 构建 /admin-api 前缀对象以及要扫描的目标包（目标包采用ant表达式语言模糊匹配）
     */
    @NotNull(message = "Admin API 不能为空")
    private Api adminApi = new Api("/admin-api", "**.controller.admin.**");

    @NotNull(message = "Admin UI 不能为空")
    private Ui adminUi = new Ui();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Valid
    public static class Api {

        /**
         * API 前缀，实现所有 Controller 提供的 RESTFul API 的统一前缀
         *
         *
         * 意义：通过该前缀，避免 Swagger、Actuator 意外通过 Nginx 暴露出来给外部，带来安全性问题
         *      这样，Nginx 只需要配置转发到 /api/* 的所有接口即可。
         *
         * @see BiWebAutoConfiguration#configurePathMatch(PathMatchConfigurer)
         */
        @NotEmpty(message = "API 前缀不能为空")
        private String prefix;

        /**
         * Controller 所在包的 Ant 路径规则
         *
         * 主要目的是，给该 Controller 设置指定的 {@link #prefix}
         */
        @NotEmpty(message = "Controller 所在包不能为空")
        private String controller;

    }

    @Data
    @Valid
    public static class Ui {

        /**
         * 访问地址
         */
        private String url = "http://dashboard.bi.iocoder.cn";

    }

}
