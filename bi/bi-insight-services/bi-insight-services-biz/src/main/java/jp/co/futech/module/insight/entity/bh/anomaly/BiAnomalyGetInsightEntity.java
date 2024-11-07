package jp.co.futech.module.insight.entity.bh.anomaly;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_bi_daily_get_insight2")
public class BiAnomalyGetInsightEntity {

    private String metric;

    @TableField("sub_dimension_points")
    private String subDimensionPoints;

    @TableField("date_points")
    private String datePoints;

    @TableField("ggr_points")
    private BigDecimal ggrPoints;

    @TableField("ggr_top_points")
    private BigDecimal ggrTopPoints;

    @TableField("ggr_bottom_points")
    private BigDecimal ggrBottomPoints;

    @TableField("metric_anomalies")
    private String metricAnomalies;

    @TableField("site_anomalies")
    private String siteAnomalies;

    @TableField("date_anomalies")
    private String dateAnomalies;

    @TableField("dimension_anomalies")
    private String dimensionAnomalies;

    @TableField("sub_dimension_anomalies")
    private String subDimensionAnomalies;

    @TableField("ggr_anomalies")
    private BigDecimal ggrAnomalies;

    @TableField("detetion_time_anomalies")
    private String detetionTimeAnomalies;

    @TableField("severity_anomalies")
    private String severityAnomalies;

    @TableField("rate_anomalies")
    private BigDecimal rateAnomalies;

    @TableField("anomaly_describe_anomalies")
    private String anomalyDescribeAnomalies;

    @TableField("cause_analysis_anomalies")
    private String causeAnalysisAnomalies;

    @TableField("detail_anomalies")
    private String detailAnomalies;

    @TableField("anomaly_info_anomalies")
    private String anomalyInfoAnomalies;

    @TableField("update_dt")
    private String updateDt;
}
