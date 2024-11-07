package jp.co.futech.module.system.controller.admin.help.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @description: 帮助文档创建VO
 * @author: ErHu.Zhao
 * @create: 2024-06-07
 **/
@Schema(description = "管理后台 - 帮助文档创建/修改/删除 Request VO")
@Data
public class HelpReqVO {

    @Schema(description = "帮助文档编号-list|delete|edit请求下必传", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1024")
    @NotNull(message = "{100000029}{000000001}", groups = {DeleteHelpDocument.class, EditHelpDocument.class})
    @Null(message = "{100000030}{000000012}", groups = {CreateHelpDocument.class})
    private Long id;

    @Schema(description = "关联菜单编号-create|edit请求下必传", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1024")
    @NotNull(message = "{100000031}{000000001}", groups = {CreateHelpDocument.class, QueryHelpDocument.class, EditHelpDocument.class})
    private Long menuId;

    @Schema(description = "帮助文档标题-create|edit请求下必传", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "小博主")
    @NotBlank(message = "{100000032}{000000004}", groups = {CreateHelpDocument.class, EditHelpDocument.class})
    @Size(max = 50, message = "{100000032}{000000003}", groups = {CreateHelpDocument.class, EditHelpDocument.class})
    private String title;

    @Schema(description = "帮助文档类型", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "小博主")
    @NotNull(message = "{100000033}{000000001}")
    private Integer type = 0;

    @Schema(description = "帮助文档内容", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "半生编码")
    private String content;

    @Schema(description = "状态，参见 CommonStatusEnum 枚举类", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Integer status;

    private String locale;

    public interface CreateHelpDocument {
    }

    public interface DeleteHelpDocument {
    }

    public interface EditHelpDocument {
    }

    public interface QueryHelpDocument {
    }
}
