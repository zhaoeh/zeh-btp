package jp.co.futech.module.insight.po.res.anomaly;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: response
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnomalyGetInsight2Res {

    @JsonIgnore
    private String metric1;

    @JsonIgnore
    private String subDimensionPoints1;

    @JsonIgnore
    private String datePoints1;

    @JsonIgnore
    private BigDecimal ggrPoints1;

    @JsonIgnore
    private BigDecimal ggrTopPoints1;

    @JsonIgnore
    private BigDecimal ggrBottomPoints1;

    @JsonIgnore
    private String metricAnomalies2;

    @JsonIgnore
    private String siteAnomalies2;

    @JsonIgnore
    private String dateAnomalies2;

    @JsonIgnore
    private BigDecimal ggrAnomalies2;

    @JsonIgnore
    private String detetionTimeAnomalies2;

    @JsonIgnore
    private String severityAnomalies2;

    @JsonIgnore
    private BigDecimal rateAnomalies2;

    @JsonIgnore
    private String anomalyDescribeAnomalies2;

    @JsonIgnore
    private String causeAnalysisAnomalies2;

    @JsonIgnore
    private String detailAnomalies2;

    @JsonIgnore
    private String note2;

    @JsonIgnore
    private String remark2;


    @JsonProperty("points")
    private List<Point> points;

    @JsonProperty("anomalies")
    private List<Anomalie> anomalies;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Point {
        @JsonProperty("metric")
        private String metric;

        @JsonProperty("sub_dimension")
        private String subDimensionPoints;

        @JsonProperty("date")
        private String datePoints;

        @JsonProperty("ggr")
        private BigDecimal ggrPoints;

        @JsonProperty("ggr_top")
        private BigDecimal ggrTopPoints;

        @JsonProperty("ggr_bottom")
        private BigDecimal ggrBottomPoints;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Anomalie {
        @JsonProperty("metric")
        private String metricAnomalies;

        @JsonProperty("site_id")
        private String siteAnomalies;

        @JsonProperty("date")
        private String dateAnomalies;

        @JsonProperty("ggr")
        private BigDecimal ggrAnomalies;

        @JsonProperty("detetion_time")
        private String detetionTimeAnomalies;

        @JsonProperty("severity")
        private String severityAnomalies;

        @JsonProperty("rate")
        private BigDecimal rateAnomalies;

        @JsonProperty("anomaly_describe")
        private String anomalyDescribeAnomalies;

        @JsonProperty("cause_analysis")
        private String causeAnalysisAnomalies;

        @JsonProperty("detail")
        private String detailAnomalies;

        @JsonProperty("note")
        private String note;

        @JsonProperty("remark")
        private String remark;

    }

    public boolean hasText1() {
        return StringUtils.hasText(metric1);
    }

    public boolean hasText2() {
        return StringUtils.hasText(metricAnomalies2);
    }

}
