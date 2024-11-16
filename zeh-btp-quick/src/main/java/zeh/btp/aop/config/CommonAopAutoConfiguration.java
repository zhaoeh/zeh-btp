package zeh.btp.aop.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import zeh.btp.aop.CommonAspect;
import zeh.btp.aop.CommonAspectRunner;

import java.util.Map;

/**
 * @description: 通用aop自定配置类
 * @author: ErHu.Zhao
 * @create: 2024-09-06
 **/
@AutoConfiguration
public class CommonAopAutoConfiguration {

    /**
     * 自动配置 CommonAspect ，该实例依赖ObjectProvider<Map<String, CommonAspectRunner>> runnerProvider，会自动从容器中获取CommonAspectRunner所有实例转换为Map，key为beanName，value为实例
     * 采用ObjectProvider宽松注入，当容器中不存在指定实例时，则注入空map
     *
     * @param runnerProvider CommonAspectRunner的实例map集合
     * @return CommonAspect 实例
     */
    @Bean
    public CommonAspect commonAspect(ObjectProvider<Map<String, CommonAspectRunner>> runnerProvider) {
        return new CommonAspect(runnerProvider);
    }
}
