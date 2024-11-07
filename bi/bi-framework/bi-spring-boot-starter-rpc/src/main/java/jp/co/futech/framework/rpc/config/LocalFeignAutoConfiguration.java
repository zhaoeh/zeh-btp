package jp.co.futech.framework.rpc.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

/**
 * @description: 本地Feign调用url设置
 * @author: ErHu.Zhao
 * @create: 2024-07-18
 **/
@AutoConfiguration
public class LocalFeignAutoConfiguration {

    private static final String REQUEST_URL_PREFIX = "http://127.0.0.1:";

    @Value("${USING_LOCAL_FEIGN:false}")
    private Boolean usingLocalFeign;

    @Autowired
    private DiscoveryClient client;

    @Bean
    public RequestInterceptor localFeignRequestInterceptor() {
        return requestTemplate -> {
            if (BooleanUtil.isTrue(usingLocalFeign)) {
                Integer port = parseTargetServerPortFromNacos(requestTemplate);
                Optional.ofNullable(port).map(p -> REQUEST_URL_PREFIX + p).map(replaceUrl -> rebuildRequestTemplate(requestTemplate, replaceUrl)).
                        orElse(requestTemplate);
            }
        };
    }

    /**
     * 从nacos注册中心中获取目标服务的启动端口
     *
     * @param requestTemplate 请求模板对象
     * @return 目标服务实例端口
     */
    private Integer parseTargetServerPortFromNacos(RequestTemplate requestTemplate) {
        String localIp = null;
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String targetServerName = requestTemplate.feignTarget().name();
        List<ServiceInstance> instanceList = client.getInstances(targetServerName);
        if (CollUtil.isEmpty(instanceList)) {
            return null;
        }
        String finalLocalIp = localIp;
        return instanceList.stream().filter(instance-> instance.getHost().equals(finalLocalIp)).findFirst().map(ServiceInstance::getPort).orElse(null);
    }

    /**
     * 根据替换的url重构RequestTemplate
     *
     * @param originalRequestTemplate
     * @param replaceUrl
     * @return
     */
    private RequestTemplate rebuildRequestTemplate(RequestTemplate originalRequestTemplate, String replaceUrl) {
        if (StringUtils.hasText(replaceUrl)) {
            return originalRequestTemplate.feignTarget(null);
//            return originalRequestTemplate.feignTarget(new Target.HardCodedTarget<>(originalRequestTemplate.feignTarget().type(), replaceUrl));
//            return originalRequestTemplate.feignTarget(new Target.HardCodedTarget<>(originalRequestTemplate.feignTarget().type(), originalRequestTemplate.feignTarget().name(), replaceUrl));
        }
        return originalRequestTemplate;
    }
}
