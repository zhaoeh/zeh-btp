package jp.co.futech.module.insight.po.req.anomaly;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: pojo
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
public class AnomalyGetAnomalyReq implements Serializable {
    @JsonProperty("metric")
    private String metric;

    /**
     * 异常点的日期
     */
    @JsonProperty("date")
    private String date;

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

    /**
     * 获取登录用户
     */
    @JsonProperty("user_name")
    private String userName;

    @JsonIgnore
    private String dimension;

    @JsonIgnore
    private String subDimension;

}
