package jp.co.futech.framework.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "分页结果")
@Data
public final class PageObjectResult<T> implements Serializable {

    @Schema(description = "数据", requiredMode = Schema.RequiredMode.REQUIRED)
    private T object;

    @Schema(description = "总量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total;

    public PageObjectResult() {
    }

    public PageObjectResult(T list, Long total) {
        this.object = list;
        this.total = total;
    }

}
