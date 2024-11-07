package jp.co.futech.module.insight.config.rpc;

import jp.co.futech.module.system.api.i18n.I18nApi;
import jp.co.futech.module.system.api.user.AdminUserApi;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {I18nApi.class, AdminUserApi.class})
@Data
public class RpcConfiguration {

    @Autowired
    private I18nApi i18nApi;
}
