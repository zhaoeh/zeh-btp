package jp.co.futech.module.system.controller.admin.permission.vo.menu;

import ft.btp.i18n.annotation.EnableI18nField;
import ft.btp.i18n.annotation.I18nField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 菜单精简信息 Response VO")
@Data
@EnableI18nField
public class MenuSimpleRespVO {

    @Schema(description = "菜单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    private String name;

    @I18nField
    private String i18nName;

    @Schema(description = "父菜单 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long parentId;

    @Schema(description = "类型，参见 MenuTypeEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

}
