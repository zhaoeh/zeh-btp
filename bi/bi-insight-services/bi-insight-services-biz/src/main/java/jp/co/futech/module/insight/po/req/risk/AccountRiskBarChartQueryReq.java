package jp.co.futech.module.insight.po.req.risk;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description: account risk柱状图查询请求
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Schema(description = "账号风控 - 柱状图查询汇总 Request")
@Data
public class AccountRiskBarChartQueryReq extends AccountRiskBaseReq {
}
