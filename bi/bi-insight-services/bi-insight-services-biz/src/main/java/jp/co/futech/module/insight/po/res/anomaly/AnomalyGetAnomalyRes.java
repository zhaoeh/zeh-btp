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
 * @description: response
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnomalyGetAnomalyRes {

    @JsonProperty("dimension")
    private String dimension;

    @JsonProperty("subDimension")
    private String subDimension;

    @JsonProperty("points")
    private List<Point> points;

    @JsonProperty("anomaly")
    private Anomaly anomaly;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Point {
        @JsonProperty("metric")
        private String metric;

        @JsonProperty("date")
        private String dt;

        @JsonProperty("ggr")
        private BigDecimal ggr;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Anomaly {
        @JsonProperty("metric")
        private String metric;

        @JsonProperty("date")
        private String dt;

        @JsonProperty("dimension")
        @JsonIgnore
        private String dimension;

        @JsonProperty("sub_dimension")
        @JsonIgnore
        private String subDimension;

        @JsonProperty("ggr")
        private BigDecimal ggr;

        @JsonProperty("detetion_time")
        private String detetionTime;

        @JsonProperty("severity")
        private String severity;

        @JsonProperty("rate")
        private BigDecimal rate;

        @JsonProperty("anomaly_describe")
        private String anomalyDescribe;

        @JsonProperty("cause_analysis")
        private String causeAnalysis;

        @JsonProperty("detail")
        private String detail;

        @JsonProperty("note")
        private String note;

        @JsonProperty("remark")
        private String remark;

        @JsonProperty("create_time")
        private String createTime;

        @JsonProperty("dimension_rate")
        private String dimensionRate;

    }

}
