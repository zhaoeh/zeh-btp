package jp.co.futech.module.system.controller.admin.auth.vo;

import jp.co.futech.framework.common.validation.InEnum;
import jp.co.futech.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "管理后台 - 社交绑定登录 Request VO，使用 code 授权码 + 账号密码")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSocialBindLoginReqVO {

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

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "biyuanma")
    @NotEmpty(message = "{100000009}{000000014}")
    @Length(min = 4, max = 16, message = "{100000010}{000000002}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{100000010}{000000005}")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "buzhidao")
    @NotEmpty(message = "{100000011}{000000014}")
    @Length(min = 4, max = 16, message = "{100000011}{000000002}")
    private String password;

}
