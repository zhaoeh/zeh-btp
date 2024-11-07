package jp.co.futech.module.insight.po.res.risk;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 柱状图响应
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRiskBarChartQueryRes {

    @JsonProperty("date")
    @Schema(description = "日期")
    private String date;

    @JsonProperty("total_users")
    @Schema(description = "total users")
    private Integer totalUsers;

}
