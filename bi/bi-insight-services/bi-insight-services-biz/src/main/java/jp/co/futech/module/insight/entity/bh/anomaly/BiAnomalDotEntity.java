package jp.co.futech.module.insight.entity.bh.anomaly;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: t_bi_anomal_dot 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@TableName("t_bi_anomal_dot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BiAnomalDotEntity {
    private String metric;

    private String site;

    private String dt;

    private String dimension;

    private String sub_dimension;

    private BigDecimal ggr;

    private String detetion_time;

    private String severity;

    private BigDecimal rate;

    private String anomaly_describe;

    private String cause_analysis;

    private String detail;

    private String anomaly_info;

}
