package jp.co.futech.module.system.controller.admin.permission.vo.role;

import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 角色更新状态 Request VO")
@Data
public class RoleUpdateStatusReqVO {

    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "{100000069}{000000001}")
    private Long id;

    @Schema(description = "状态,见 CommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000005}{000000001}")
    @InEnum(value = CommonStatusEnum.class, message = "{100000006}{000000008}")
    private Integer status;

}
