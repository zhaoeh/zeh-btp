package jp.co.futech.module.system.controller.admin.user.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.validation.InEnum;
import lombok.Data;

@Schema(description = "管理后台 - 用户更新Mfa状态 Request VO")
@Data
public class UserUpdateMfaStatusReqVO {

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "{100000007}{000000001}")
    private Long id;

    @Schema(description = "状态，见 CommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000005}{000000001}")
    @InEnum(value = CommonStatusEnum.class, message = "{100000098}{000000009}")
    private Integer isMfa;

}
