package jp.co.futech.module.insight.po.req.anomaly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: pojo
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
public class AnomalyGetDimensionReq implements Serializable {
    @JsonProperty("metric")
    private String metric;

}
