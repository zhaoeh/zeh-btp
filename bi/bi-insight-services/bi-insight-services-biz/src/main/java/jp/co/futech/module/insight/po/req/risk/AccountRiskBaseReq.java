package jp.co.futech.module.insight.po.req.risk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-06-26
 **/
@Data
@ToString(callSuper = true)
public class AccountRiskBaseReq implements Serializable {

    @Schema(description = "选择类型 1：按天查询 2：按月查询", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotBlank(message = "timeRange不能为空串")
    @Pattern(regexp = "[1-2]", message = "Illegal Time Range")
    @JsonProperty("timeRange")
    private String timeRange;

    @JsonIgnore
    private String timeRangeFormat;

    @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2023-12-01")
    @JsonProperty("startDate")
    @Pattern(regexp = "^(\\d{4}-\\d{2}-\\d{2})?$", message = "startDate data format error")
    private String startDate;

    @Schema(description = "结束日期", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2024-12-01")
    @JsonProperty("endDate")
    @Pattern(regexp = "^(\\d{4}-\\d{2}-\\d{2})?$", message = "endDate data format error")
    private String endDate;

}
