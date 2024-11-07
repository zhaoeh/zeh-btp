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
public class AnomalySelectDimensionsReq implements Serializable {
    @JsonProperty("metric")
    private String metric;

    @JsonProperty("dimension")
    private String dimension;

    @JsonProperty("subDimension")
    private List<String> subDimension;

    /**
     * 开始日期
     */
    @JsonProperty("startDate")
    private String startDate;

    /**
     * 结束日期
     */
    @JsonProperty("endDate")
    private String endDate;

}
