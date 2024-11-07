package jp.co.futech.module.system.controller.admin.notify.vo.template;

import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 站内信模版创建/修改 Request VO")
@Data
public class NotifyTemplateSaveReqVO {

    @Schema(description = "ID", example = "1024")
    private Long id;

    @Schema(description = "模版名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "测试模版")
    @NotEmpty(message = "{100000048}{000000014}")
    private String name;

    @Schema(description = "模版编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "SEND_TEST")
    @NotNull(message = "{100000049}{000000001}")
    private String code;

    @Schema(description = "模版类型，对应 system_notify_template_type 字典", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000050}{000000001}")
    private Integer type;

    @Schema(description = "发送人名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "土豆")
    @NotEmpty(message = "{100000051}{000000014}")
    private String nickname;

    @Schema(description = "模版内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "我是模版内容")
    @NotEmpty(message = "{100000052}{000000014}")
    private String content;

    @Schema(description = "状态，参见 CommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000005}{000000001}")
    @InEnum(value = CommonStatusEnum.class, message = "{100000005}{000000008}")
    private Integer status;

    @Schema(description = "备注", example = "我是备注")
    private String remark;

}
