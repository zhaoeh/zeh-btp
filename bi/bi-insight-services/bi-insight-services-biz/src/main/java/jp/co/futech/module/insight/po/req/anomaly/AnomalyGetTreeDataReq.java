package jp.co.futech.module.insight.po.req.anomaly;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @description: get tree data request
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
public class AnomalyGetTreeDataReq {

    @NotBlank(message = "metric {000000004}")
    @JsonProperty("metric")
    private String metric;

    @NotBlank(message = "site {000000004}")
    @JsonProperty("site")
    private String site;

    @JsonProperty("date")
    private List<String> date;

    @NotBlank(message = "user_name {000000004}")
    @JsonProperty("user_name")
    private String userName;
}
