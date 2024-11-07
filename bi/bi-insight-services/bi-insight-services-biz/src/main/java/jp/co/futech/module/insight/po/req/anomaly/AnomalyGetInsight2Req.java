package jp.co.futech.module.insight.po.req.anomaly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: pojo
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
public class AnomalyGetInsight2Req implements Serializable {
    @JsonProperty("metric")
    private String metric;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("severity")
    private List<String> severity;

    @JsonProperty("Note")
    private List<String> note;

    @JsonProperty("Search")
    private String search;

    @JsonProperty("site")
    private String site;
}
