package jp.co.futech.module.system.controller.admin.socail.vo.user;

import jp.co.futech.framework.common.validation.InEnum;
import jp.co.futech.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 社交绑定 Request VO，使用 code 授权码")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialUserBindReqVO {

    @Schema(description = "社交平台的类型，参见 UserSocialTypeEnum 枚举值", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @InEnum(SocialTypeEnum.class)
    @NotNull(message = "{100000016}{000000001}")
    private Integer type;

    @Schema(description = "授权码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "{100000012}{000000014}")
    private String code;

    @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    @NotEmpty(message = "state {000000014}")
    private String state;

}
