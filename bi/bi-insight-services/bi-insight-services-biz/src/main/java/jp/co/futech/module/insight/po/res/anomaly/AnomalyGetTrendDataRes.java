package jp.co.futech.module.insight.po.res.anomaly;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: get trend data request
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnomalyGetTrendDataRes {

    @JsonProperty("trend_data")
    private List<TrendData> trendDatas;

    @JsonProperty("anomal_data")
    private List<AnomalData> anomalDatas;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrendData {
        @JsonProperty("date")
        private String dt;

        @JsonProperty("site_id")
        private String siteId;

        @JsonProperty("platform")
        private String platform;

        @JsonProperty("gamekind")
        private String gamekind;

        @JsonProperty("value")
        private String valueTrend;

        @JsonProperty("index")
        private String indexTrend;

    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnomalData {
        @JsonProperty("metric")
        private String metric;

        @JsonProperty("site")
        private String site;

        @JsonProperty("date")
        private String dateAnomal;

        @JsonProperty("dimension")
        private String dimension;

        @JsonProperty("sub_dimension")
        private String subDimensionAnomal;

        @JsonProperty("ggr")
        private BigDecimal ggrAnomal;

        @JsonProperty("detetion_time")
        private String detetionTimeAnomal;

        @JsonProperty("severity")
        private String severityAnomal;

        @JsonProperty("rate")
        private BigDecimal rateAnomal;

        @JsonProperty("anomaly_describe")
        private String anomalyDescribeAnomal;

        @JsonProperty("cause_analysis")
        private String causeAnalysisAnomal;

        @JsonProperty("detail")
        private String detailAnomal;

        @JsonIgnore
        private String anomalyInfo;

        @JsonProperty("dimension_rate")
        private String dimensionRateAnomal;

    }
}
