package jp.co.futech.framework.rpc.config;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: 容器初始化钩子实现，负责灌入本地feign url属性
 * @description:
 * @author: Erhu.Zhao
 * @create: 2023-11-09 18:15
 */
public class InitLocalFeignConfiguration implements ApplicationContextInitializer {

    private static final String TARGET_URL_PREFIX = "127.0.0.1:";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        try {
            String localFeignConfig = environment.getProperty("LOCAL_FEIGN_CONFIG");
            if (!StringUtils.hasText(localFeignConfig)) {
                return;
            }
            String[] configs = localFeignConfig.split(",");
            List<LocalFeignConfig> instances = Arrays.stream(configs).map(config -> {
                String[] items = config.split(":");
                return LocalFeignConfig.builder().envKey(items[0]).targetUrl(TARGET_URL_PREFIX + items[1]).build();
            }).collect(Collectors.toList());

            if (CollUtil.isEmpty(instances)) {
                return;
            }
            instances.stream().forEach(instance -> environment.getSystemProperties().put(instance.envKey, instance.getTargetUrl()));
        } catch (Exception e) {
        }

    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class LocalFeignConfig {
        private String envKey;

        private String targetUrl;
    }

}