package jp.co.futech.module.system.controller.admin.dict.vo.data;

import ft.btp.validation.annotation.ReferenceManager;
import ft.btp.validation.annotation.ReferenceValid;
import ft.btp.validation.annotation.show.ReferenceEnums;
import ft.btp.validation.annotation.show.common.ReferenceField;
import ft.btp.validation.annotation.validate.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.validation.InEnum;
import jp.co.futech.framework.file.core.enums.FileStorageEnum;
import lombok.Data;

@Schema(description = "管理后台 - 字典数据创建/修改 Request VO")
@Data
@ReferenceManager
public class DictDataSaveReqVO {

    @Schema(description = "字典数据编号", example = "1024")
    private Long id;

    @Schema(description = "显示顺序不能为空", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "{100000001}{000000001}")
    private Integer sort;

    @Schema(description = "字典标签", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotBlank(message = "{100000002}{000000004}")
    @Size(max = 100, message = "{100000002}{000000003}")
    private String label;

    @Schema(description = "字典值", requiredMode = Schema.RequiredMode.REQUIRED, example = "iocoder")
    @NotBlank(message = "{100000003}{000000004}")
    @NotBlank
    @Size(max = 100, message = "{100000003}{000000003}")
    @ReferenceValid(enums = {
            @ReferenceEnums(referenceField = @ReferenceField(name = "dictType", when = "infra_file_storage"),
                    enumVa = @EnumValid(enumClass = FileStorageEnum.class, enumField = "storage"))
    })
    private String value;

    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_common_sex")
    @NotBlank(message = "{100000004}{000000004}")
    @Size(max = 100, message = "{100000004}{000000003}")
    private String dictType;

    @Schema(description = "状态,见 CommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000005}{000000001}")
    @InEnum(value = CommonStatusEnum.class, message = "{100000006}{000000009}")
    private Integer status;

    @Schema(description = "颜色类型,default、primary、success、info、warning、danger", example = "default")
    private String colorType;

    @Schema(description = "css 样式", example = "btn-visible")
    private String cssClass;

    @Schema(description = "备注", example = "我是一个角色")
    private String remark;

}
