package jp.co.futech.module.system.controller.admin.dept.vo.post;

import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "管理后台 - 岗位创建/修改 Request VO")
@Data
public class PostSaveReqVO {

    @Schema(description = "岗位编号", example = "1024")
    private Long id;

    @Schema(description = "岗位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "小土豆")
    @NotBlank(message = "{100000020}{000000004}")
    @Size(max = 50, message = "{100000020}{000000003}")
    private String name;

    @Schema(description = "岗位编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "bi")
    @NotBlank(message = "{100000021}{000000004}")
    @Size(max = 64, message = "{100000021}{000000003}")
    private String code;

    @Schema(description = "显示顺序不能为空", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "{100000001}{000000001}")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "备注", example = "快乐的备注")
    private String remark;

}