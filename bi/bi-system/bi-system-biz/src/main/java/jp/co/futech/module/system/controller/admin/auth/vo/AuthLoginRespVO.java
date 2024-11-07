package jp.co.futech.module.system.controller.admin.auth.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 登录 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginRespVO {

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String userName;

    @Schema(description = "访问令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "happy")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String accessToken;

    @Schema(description = "刷新令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "nice")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String refreshToken;

    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private LocalDateTime expiresTime;

    @Schema(description = "是否开启MFA认证，默认0开启，1是不开启", example = "0")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Integer isMfa;

    @Schema(description = "绑定MFA的状态，0已绑定，1是未绑定", example = "0")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Integer mfaBindingStatus;

    @Schema(description = "MFA二次验证状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Boolean mfaCheckResult;
}
