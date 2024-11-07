package jp.co.futech.module.insight.po.res.risk;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jp.co.futech.module.insight.config.RiskQueryCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 查询条件集合响应
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRiskConditionsQueryRes {

    @JsonProperty("primary_risk_types")
    @Schema(description = "primary_risk_types结果集")
    private List<RiskQueryCondition> primaryRiskTypes;

    @JsonProperty("secondary_risk_types")
    @Schema(description = "secondary_risk_types结果集")
    private List<RiskQueryCondition> secondaryRiskTypes;

    @JsonProperty("time_ranges")
    @Schema(description = "time_ranges结果集")
    private List<RiskQueryCondition> timeRanges;

    @JsonProperty("risk_level")
    @Schema(description = "risk_level结果集")
    private List<RiskQueryCondition> riskLevels;


}
