package jp.co.futech.module.system.controller.admin.auth.vo;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jp.co.futech.framework.common.validation.InEnum;
import jp.co.futech.module.system.enums.social.SocialTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Schema(description = "管理后台 - 账号密码登录 Request VO，如果登录并绑定社交用户，需要传递 social 开头的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginReqVO {

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "biyuanma")
    @NotEmpty(message = "{100000009}{000000014}")
    @Length(min = 4, max = 16, message = "{100000010}{000000002}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{100000010}{000000005}")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "buzhidao")
    @NotEmpty(message = "{100000011}{000000014}")
    @Length(min = 4, max = 16, message = "{100000011}{000000002}")
    private String password;

    @Schema(description = "绑定状态", example = "1")
    private Integer mfaBindingStatus;

    // ========== 图片验证码相关 ==========

    @Schema(description = "验证码，验证码开启时，需要传递", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "PfcH6mgr8tpXuMWFjvW6YVaqrswIuwmWI5dsVZSg7sGpWtDCUbHuDEXl3cFB1+VvCC/rAkSwK8Fad52FSuncVg==")
    @NotEmpty(message = "{100000008}{000000014}", groups = CodeEnableGroup.class)
    private String captchaVerification;

    // ========== 绑定社交登录时，需要传递如下参数 ==========

    @Schema(description = "社交平台的类型，参见 SocialTypeEnum 枚举值", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @InEnum(SocialTypeEnum.class)
    private Integer socialType;

    @Schema(description = "授权码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String socialCode;

    @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    private String socialState;

    /**
     * 开启验证码的 Group
     */
    public interface CodeEnableGroup {}

    @AssertTrue(message = "{100000012}{000000001}")
    public boolean isSocialCodeValid() {
        return socialType == null || StrUtil.isNotEmpty(socialCode);
    }

    @AssertTrue(message = "{100000013}{000000001}")
    public boolean isSocialState() {
        return socialType == null || StrUtil.isNotEmpty(socialState);
    }

}
