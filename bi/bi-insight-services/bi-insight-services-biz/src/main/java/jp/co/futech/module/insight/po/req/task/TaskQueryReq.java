package jp.co.futech.module.insight.po.req.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jp.co.futech.framework.common.pojo.SortablePageParam;
import jp.co.futech.module.insight.entity.ms.EventTaskInfoEntity;
import lombok.*;
import lombok.experimental.Accessors;

@Schema(description = "事件管理 - 详情列表查询分页 Request")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class TaskQueryReq extends SortablePageParam {
    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    @Schema(description = "页码，从 1 开始", requiredMode = Schema.RequiredMode.REQUIRED,example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNo = PAGE_NO;

    @Schema(description = "每页条数，最大值为 100", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = PAGE_SIZE;

    private Integer taskExecutionStatus = null;
    private Integer isAuto=0;

}
