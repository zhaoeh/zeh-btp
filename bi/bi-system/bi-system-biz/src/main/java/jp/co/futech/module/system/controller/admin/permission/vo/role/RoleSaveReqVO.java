package jp.co.futech.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "管理后台 - 角色创建 Request VO")
@Data
public class RoleSaveReqVO {

    @Schema(description = "角色编号", example = "1")
    private Long id;

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "管理员")
    @NotBlank(message = "{100000072}{000000004}")
    @Size(max = 30, message = "{100000072}{000000003}")
    private String name;

    @NotBlank(message = "{100000071}{000000004}")
    @Size(max = 100, message = "{100000071}{000000003}")
    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "ADMIN")
    private String code;

    @Schema(description = "显示顺序不能为空", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "{100000001}{000000001}")
    private Integer sort;

    @Schema(description = "备注", example = "我是一个角色")
    private String remark;

}
