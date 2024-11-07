package jp.co.futech.module.insight.po.res.anomaly;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AnomalyGetDimensionRes {

    @JsonProperty("dimension")
    private String dimension;

    @JsonIgnore
    private String subItemStr;

    @JsonProperty("sub_items")
    private List<String> subItems;

}
