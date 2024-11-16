package zeh.btp.simple.core;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @description: 本地配置文件优先级最高配置
 * @author: Erhu.Zhao
 * @create: 2024-08-20
 */
public class LocalConfigPriorityConfiguration implements ApplicationContextInitializer, Ordered {
    private final Properties properties = new Properties();
    private final YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
    private static EnvironmentHandler environmentHandler;
    private static String[] profiles = {
            "local-config-high-priority.properties",
            "local-config-high-priority.yml",
            "local-config-high-priority.yaml",
    };

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        if (Objects.nonNull(environmentHandler)) {
            environmentHandler.handleEnvironment(environment);
        }

        for (String profile : profiles) {
            Resource resource = new ClassPathResource(profile);
            if (resource.isReadable()) {
                try {
                    if (Objects.nonNull(profile) && profile.endsWith(".properties")) {
                        properties.load(resource.getInputStream());
                        environment.getPropertySources().addFirst(new PropertiesPropertySource(resource.getFilename(), properties));
                    } else if (Objects.nonNull(profile) && (profile.endsWith(".yml") || profile.endsWith(".yaml"))) {
                        yamlPropertiesFactoryBean.setResources(resource);
                        Properties properties = yamlPropertiesFactoryBean.getObject();
                        environment.getPropertySources().addFirst(new PropertiesPropertySource(resource.getFilename(), properties));
                    }

                } catch (IOException e) {
                }
            }

        }
    }

    /**
     * 确保在nacos的处理order之后执行，用于覆盖nacos已经灌入到Environment中的配置项
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -298;
    }


    public static void setProfiles(List<String> resources) {
        if (CollectionUtils.isEmpty(resources)) {
            return;
        }
        LocalConfigPriorityConfiguration.profiles = resources.toArray(new String[0]);
    }

    public static void setEnvironmentHandler(EnvironmentHandler environmentHandler) {
        LocalConfigPriorityConfiguration.environmentHandler = environmentHandler;
    }


    @FunctionalInterface
    public interface EnvironmentHandler {

        /**
         * 处理Environment
         *
         * @param environment Environment
         */
        void handleEnvironment(ConfigurableEnvironment environment);
    }
}