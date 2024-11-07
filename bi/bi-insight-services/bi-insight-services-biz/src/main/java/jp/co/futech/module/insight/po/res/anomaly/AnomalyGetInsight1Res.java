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
public class AnomalyGetInsight1Res {

    @JsonIgnore
    private String metric1;

    @JsonIgnore
    private String date1;

    @JsonIgnore
    private BigDecimal ggr1;

    @JsonIgnore
    private BigDecimal ggrTop1;

    @JsonIgnore
    private BigDecimal ggrBottom1;

    @JsonIgnore
    private String metric2;

    @JsonIgnore
    private String date2;

    @JsonIgnore
    private BigDecimal ggr2;

    @JsonIgnore
    private String detetionTime2;

    @JsonIgnore
    private String severity2;

    @JsonIgnore
    private BigDecimal rate2;

    @JsonIgnore
    private String anomalyDescribe2;

    @JsonIgnore
    private String causeAnalysis2;

    @JsonIgnore
    private String detail2;

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

        @JsonProperty("date")
        private String date;

        @JsonProperty("ggr")
        private BigDecimal ggr;

        @JsonProperty("ggr_top")
        private BigDecimal ggrTop;

        @JsonProperty("ggr_bottom")
        private BigDecimal ggrBottom;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Anomalie {
        @JsonProperty("metric")
        private String metric;

        @JsonProperty("date")
        private String date;

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

    }

    public boolean hasText1() {
        return StringUtils.hasText(metric1);
    }

    public boolean hasText2() {
        return StringUtils.hasText(metric2);
    }

}
