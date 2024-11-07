package jp.co.futech.module.insight.entity.bh.anomaly;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: t_bi_note_dot 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_bi_anomal_dot_all")
public class BiAnomalDotAllEntity {

    private String metric;

    private String userName;

    private String dt;

    private String dimension;

    private String subDimension;

    private BigDecimal ggr;

    private String detetionTime;

    private String severity;

    private BigDecimal rate;

    private String anomalyDescribe;

    private String causeAnalysis;

    private String detail;

    private String note;

    private String remark;

    private String createTime;

}
