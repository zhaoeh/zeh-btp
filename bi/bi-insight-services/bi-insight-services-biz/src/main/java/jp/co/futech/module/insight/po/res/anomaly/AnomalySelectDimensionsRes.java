package jp.co.futech.module.insight.po.res.anomaly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: response
 * @author: ErHu.Zhao
 * @create: 2024-07-23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnomalySelectDimensionsRes implements Serializable {

    @JsonProperty("date")
    private String dt;

    @JsonProperty("ggr")
    private BigDecimal ggr;

    @JsonProperty("sub_dimension")
    private String subDimension;
}
