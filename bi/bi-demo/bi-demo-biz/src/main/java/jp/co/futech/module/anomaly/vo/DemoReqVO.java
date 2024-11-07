package jp.co.futech.module.anomaly.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "anomaly服务 - 帮助文档创建/修改/删除 Request VO")
@Data
public class DemoReqVO {

    @Schema(description = "Demo编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Demo id不能为null", groups = {QueryHelpDocument.class, DeleteHelpDocument.class, EditHelpDocument.class})
    private Long id;

    @Schema(description = "关联菜单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "关联菜单编号不能为空", groups = {CreateHelpDocument.class, EditHelpDocument.class})
    private Long menuId;

    @Schema(description = "Demo标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "小博主")
    @NotBlank(message = "Demo标题不能为空", groups = {CreateHelpDocument.class, EditHelpDocument.class})
    @Size(max = 50, message = "Demo标题不能超过50个字符", groups = {CreateHelpDocument.class, EditHelpDocument.class})
    private String title;

    @Schema(description = "Demo类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "小博主")
    @NotNull(message = "Demo类型不能为空")
    private Integer type = 0;

    @Schema(description = "Demo内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "半生编码")
    private String content;

    @Schema(description = "状态，参见 CommonStatusEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    public interface CreateHelpDocument {
    }

    public interface DeleteHelpDocument {
    }

    public interface EditHelpDocument {
    }

    public interface QueryHelpDocument {
    }
}
