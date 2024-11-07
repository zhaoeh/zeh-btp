package jp.co.futech.module.system.controller.admin.dept.vo.dept;

import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "管理后台 - 部门创建/修改 Request VO")
@Data
public class DeptSaveReqVO {

    @Schema(description = "部门编号", example = "1024")
    private Long id;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotBlank(message = "{100000017}{000000004}")
    @Size(max = 30, message = "{100000017}{000000003}")
    private String name;

    @Schema(description = "父部门 ID", example = "1024")
    private Long parentId;

    @Schema(description = "显示顺序不能为空", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "{100000001}{000000001}")
    private Integer sort;

    @Schema(description = "负责人的用户编号", example = "2048")
    private Long leaderUserId;

    @Schema(description = "联系电话", example = "15601691000")
    @Size(max = 11, message = "{100000018}{000000003}")
    private String phone;

    @Schema(description = "邮箱", example = "bi@iocoder.cn")
    @Email(message = "{000000007}")
    @Size(max = 50, message = "{100000019}{000000003}")
    private String email;

    @Schema(description = "状态,见 CommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000005}{000000001}")
    @InEnum(value = CommonStatusEnum.class, message = "{100000006}{000000009}")
    private Integer status;

}
