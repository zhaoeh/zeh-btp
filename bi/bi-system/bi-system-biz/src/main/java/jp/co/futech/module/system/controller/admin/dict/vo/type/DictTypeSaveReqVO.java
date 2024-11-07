package jp.co.futech.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "管理后台 - 字典类型创建/修改 Request VO")
@Data
public class DictTypeSaveReqVO {

    @Schema(description = "字典类型编号", example = "1024")
    private Long id;

    @Schema(description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "性别")
    @NotBlank(message = "{100000023}{000000004}")
    @Size(max = 100, message = "{100000024}{000000003}")
    private String name;

    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_common_sex")
    @NotNull(message = "{100000022}{000000001}")
    @Size(max = 100, message = "{100000022}{000000003}")
    private String type;

    @Schema(description = "状态，参见 CommonStatusEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000005}{000000001}")
    private Integer status;

    @Schema(description = "备注", example = "快乐的备注")
    private String remark;

}
