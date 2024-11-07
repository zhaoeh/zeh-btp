package jp.co.futech.module.system.api.i18n;

import ft.btp.i18n.info.I18nMessages;
import jp.co.futech.framework.common.pojo.I18nRpcResult;
import jp.co.futech.framework.tenant.core.annotation.IgnoreTenantRequest;
import jp.co.futech.module.system.service.i18n.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import static jp.co.futech.framework.common.pojo.I18nRpcResult.success;

/**
 * @description: 国际化消息Feign实现
 * @author: ErHu.Zhao
 * @create: 2024-07-18
 **/
@RestController
@Validated
public class I18nApiImpl implements I18nApi {

    @Autowired
    private I18nService i18nService;

    @IgnoreTenantRequest
    @Override
    public I18nRpcResult<I18nMessages> getI18nMessages() {
        return success(i18nService.encapI18nMessages(true));
    }
}
