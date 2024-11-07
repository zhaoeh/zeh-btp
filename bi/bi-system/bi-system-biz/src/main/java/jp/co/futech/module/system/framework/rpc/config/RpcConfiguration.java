package jp.co.futech.module.system.framework.rpc.config;

import jp.co.futech.module.infra.api.file.FileApi;
import jp.co.futech.module.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class})
public class RpcConfiguration {
}
