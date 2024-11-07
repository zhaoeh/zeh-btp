package jp.co.futech.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 账号密码登录 -开启二次验证")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginMfaReqVO {
    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "{100000007}{000000001}")
    private Long userId;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED, example = "231545")
    @NotEmpty(message = "{100000008}{000000014}")
    private String checkCode;

}
