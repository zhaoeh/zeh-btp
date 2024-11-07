package jp.co.futech.framework.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "可排序的分页参数")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SortablePageParam extends PageParam {

    @Schema(description = "排序字段")
    @JsonIgnore
    private List<SortingField> sortingFields;

    /**
     * 当不分页查询时，是否按照sortingFields进行排序
     */
    @Schema(description = "不分页时是否按照sortingFields进行排序")
    @JsonIgnore
    private Boolean sortingWhenNonePaging;

}