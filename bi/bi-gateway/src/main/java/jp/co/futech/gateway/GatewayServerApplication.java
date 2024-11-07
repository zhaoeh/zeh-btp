package jp.co.futech.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class GatewayServerApplication {

    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
