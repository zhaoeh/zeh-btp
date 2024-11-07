package jp.co.futech.module.system.api.i18n;

import ft.btp.i18n.info.I18nMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jp.co.futech.framework.common.pojo.I18nRpcResult;
import jp.co.futech.module.system.enums.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = ApiConstants.NAME, url = ApiConstants.URL)
@Tag(name = "RPC 服务 - 国际化")
public interface I18nApi {

    String PREFIX = ApiConstants.PREFIX + "/i18n";

    /**
     * rpc远程获取system i18n messages
     *
     * @return system服务的i18n messages
     */
    @GetMapping(PREFIX + "/get-i18n-messages")
    @Operation(summary = "获取i18n消息集")
    @PermitAll
    I18nRpcResult<I18nMessages> getI18nMessages();

}
