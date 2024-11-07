package jp.co.futech.module.system.controller.admin.auth.vo;

import jp.co.futech.framework.common.validation.InEnum;
import jp.co.futech.framework.common.validation.Mobile;
import jp.co.futech.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 发送手机验证码 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSmsSendReqVO {

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "biyuanma")
    @NotEmpty(message = "{100000014}{000000014}")
    @Mobile
    private String mobile;

    @Schema(description = "短信场景", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000015}{000000001}")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}
