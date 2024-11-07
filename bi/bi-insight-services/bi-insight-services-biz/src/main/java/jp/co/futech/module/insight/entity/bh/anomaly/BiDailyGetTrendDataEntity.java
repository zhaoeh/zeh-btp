package jp.co.futech.module.insight.entity.bh.anomaly;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: t_bi_daily_get_tree_data 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_bi_daily_get_trend_data")
public class BiDailyGetTrendDataEntity {

    private String date_trend;

    private String site_id_trend;

    private String platform_trend;

    private String gamekind_trend;

    private BigDecimal value_trend;

    private String index_trend;

    private String metric_trend;

    private String dimension_trend;

    private String sub_items_trend;

    private String site_trend;

    private String metric_anomal;

    private String site_anomal;

    private String date_anomal;

    private String dimension_anomal;

    private String sub_dimension_anomal;

    private BigDecimal ggr_anomal;

    private String detetion_time_anomal;

    private String severity_anomal;

    private BigDecimal rate_anomal;

    private String anomaly_describe_anomal;

    private String cause_analysis_anomal;

    private String detail_anomal;

    private String anomaly_info_anomal;

    private String update_dt;

}
