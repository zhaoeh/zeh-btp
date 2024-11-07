package jp.co.futech.module.system.controller.admin.dict.vo.data;

import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.pojo.PageParam;
import jp.co.futech.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Size;

@Schema(description = "管理后台 - 字典类型分页列表 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataPageReqVO extends PageParam {

    @Schema(description = "字典标签", example = "芋道")
    @Size(max = 100, message = "{000000003}")
    private String label;

    @Schema(description = "字典类型，模糊匹配", example = "sys_common_sex")
    @Size(max = 100, message = "{100000022}{000000003}")
    private String dictType;

    @Schema(description = "展示状态，参见 CommonStatusEnum 枚举类", example = "1")
    @InEnum(value = CommonStatusEnum.class, message = "{100000006}{000000009}")
    private Integer status;

}
