package jp.co.futech.module.insight.po.res.risk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 风控饼图视图
 * @author: ErHu.Zhao
 * @create: 2024-06-27
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountRiskPieChart {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "display")
    private String display;

    @JsonProperty(value = "value")
    private Integer value;


}
