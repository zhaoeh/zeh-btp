package com.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AI 示例模块的 Spring Boot 启动入口。
 *
 * <p>{@link SpringBootApplication} 同时开启配置类注册、自动配置和组件扫描。
 * 由于本类位于 {@code com.ai} 根包，Spring 会扫描其全部子包中的
 * Controller、Service、Tool、Advisor 和 Configuration。</p>
 */
@SpringBootApplication
public class AiApplication {

    /**
     * 创建 Spring 应用上下文并启动内嵌 Web 服务器。
     * Spring AI 的模型、ChatClient.Builder 等自动配置也在上下文启动期间完成。
     *
     * @param args 命令行启动参数，可用于覆盖 application.yml 中的配置
     */
    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }

}
