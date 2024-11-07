package jp.co.futech.module.system.controller.admin.sms.vo.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 短信渠道创建/修改 Request VO")
@Data
public class SmsChannelSaveReqVO {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "短信签名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道源码")
    @NotNull(message = "{100000075}{000000001}")
    private String signature;

    @Schema(description = "渠道编码，参见 SmsChannelEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "YUN_PIAN")
    @NotNull(message = "{100000079}{000000001}")
    private String code;

    @Schema(description = "启用状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000076}{000000001}")
    private Integer status;

    @Schema(description = "备注", example = "好吃！")
    private String remark;

    @Schema(description = "短信 API 的账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "bi")
    @NotNull(message = "{100000077}{000000001}")
    private String apiKey;

    @Schema(description = "短信 API 的密钥", example = "yuanma")
    private String apiSecret;

    @Schema(description = "短信发送回调 URL", example = "http://www.iocoder.cn")
    @URL(message = "{100000078} URL {000000013}")
    private String callbackUrl;

}
