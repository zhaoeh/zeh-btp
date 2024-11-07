package jp.co.futech.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "管理后台 - 邮件发送 Req VO")
@Data
public class MailTemplateSendReqVO {

    @Schema(description = "接收邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "7685413@qq.com")
    @NotEmpty(message = "{100000043}{000000014}")
    private String mail;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    @NotNull(message = "{100000044}{000000001}")
    private String templateCode;

    @Schema(description = "模板参数")
    private Map<String, Object> templateParams;

}
