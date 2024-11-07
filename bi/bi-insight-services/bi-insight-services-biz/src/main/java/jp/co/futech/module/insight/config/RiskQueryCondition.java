package jp.co.futech.module.insight.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 风控条件查询实体
 * @author: ErHu.Zhao
 * @create: 2024-06-26
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RiskQueryCondition {

    @JsonProperty("num")
    @Schema(description = "标志：纯数字，用于前后端交互",example = "1")
    private String num;

    @JsonProperty("display")
    @Schema(description = "展示字串，用于页面展示",example = "Daily")
    private String display;

    @JsonProperty("value")
    @Schema(description = "值，真实取值",example = "daily")
    private String value;

    @JsonProperty("desc")
    @Schema(description = "描述信息",example = "按天维度标识")
    private String desc;
}
