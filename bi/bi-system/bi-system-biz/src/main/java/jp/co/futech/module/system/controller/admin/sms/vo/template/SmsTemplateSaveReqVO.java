package jp.co.futech.module.system.controller.admin.sms.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 短信模板创建/修改 Request VO")
@Data
public class SmsTemplateSaveReqVO {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "短信类型，参见 SmsTemplateTypeEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000080}{000000001}")
    private Integer type;

    @Schema(description = "开启状态，参见 CommonStatusEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000081}{000000001}")
    private Integer status;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    @NotNull(message = "{100000044}{000000001}")
    private String code;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "bi")
    @NotNull(message = "{100000082}{000000001}")
    private String name;

    @Schema(description = "模板内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "你好，{name}。你长的太{like}啦！")
    @NotNull(message = "{100000083}{000000001}")
    private String content;

    @Schema(description = "备注", example = "哈哈哈")
    private String remark;

    @Schema(description = "短信 API 的模板编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4383920")
    @NotNull(message = "{100000084}{000000001}")
    private String apiTemplateId;

    @Schema(description = "短信渠道编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "{100000085}{000000001}")
    private Long channelId;

}
