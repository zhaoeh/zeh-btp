package jp.co.futech.module.system.controller.admin.notify.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "管理后台 - 站内信模板的发送 Request VO")
@Data
public class NotifyTemplateSendReqVO {

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "01")
    @NotNull(message = "{100000053}id{000000001}")
    private Long userId;

    @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000054}{000000001}")
    private Integer userType;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "01")
    @NotEmpty(message = "{100000044}{000000014}")
    private String templateCode;

    @Schema(description = "模板参数")
    private Map<String, Object> templateParams;

}
