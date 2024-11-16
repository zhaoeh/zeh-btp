package zeh.btp.feign.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 自定义feign配置自动配置类
 * @author: ErHu.Zhao
 * @create: 2024-09-06
 **/
@AutoConfiguration
@ConditionalOnClass(FeignClient.class)
public class CustomizedFeignAutoConfiguration {
}
