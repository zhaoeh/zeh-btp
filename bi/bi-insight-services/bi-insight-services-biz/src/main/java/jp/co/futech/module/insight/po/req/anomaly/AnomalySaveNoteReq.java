package jp.co.futech.module.insight.po.req.anomaly;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @description: save note request
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
public class AnomalySaveNoteReq {

    @NotBlank(message = "metric {000000004}")
    @JsonProperty("metric")
    private String metric;

    @NotBlank(message = "site {000000004}")
    @JsonProperty("site")
    private String site;

    @NotBlank(message = "date {000000004}")
    @JsonProperty("date")
    private String date;

    @JsonProperty("note")
    private String note;

    @JsonProperty("remark")
    private String remark;

    @NotBlank(message = "userName {000000004}")
    @JsonProperty("user_name")
    private String userName;
}
