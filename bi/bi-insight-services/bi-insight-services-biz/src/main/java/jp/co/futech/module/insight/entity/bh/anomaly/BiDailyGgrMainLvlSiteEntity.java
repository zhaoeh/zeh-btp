package jp.co.futech.module.insight.entity.bh.anomaly;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: t_bi_daily_ggr_main_lvl_site 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_bi_daily_ggr_main_lvl_site")
public class BiDailyGgrMainLvlSiteEntity {


    private String dt;

    private String holiday;

    private String holiday_type;

    private String site_id;

    private String platform;

    private String gamekind;

    private BigDecimal ggr_today;

    private BigDecimal ggr_yesterday;

    private BigDecimal ggr_all_today;

    private BigDecimal ggr_all_yesterday;

    private BigDecimal ggr_decline;

    private BigDecimal ggr_decline_rate;

    private BigDecimal ggr_all_decline;

    private BigDecimal ggr_all_decline_rate;

    private BigDecimal ggr_all_yesterday_ratio;

    private BigDecimal ggr_all_decline_ratio;

    private BigDecimal decline_lift;

    private BigDecimal ggr_change_contribution;

    private BigDecimal rank_;

    private BigDecimal betamt_today;

    private BigDecimal betamt_yesterday;

    private BigDecimal betamt_decline_rate;

    private BigDecimal btloss_today;

    private BigDecimal btloss_yesterday;

    private BigDecimal btloss_decline_rate;

    private BigDecimal betcnt_today;

    private BigDecimal betcnt_yesterday;

    private BigDecimal betcnt_decline_rate;

    private BigDecimal btamavg_today;

    private BigDecimal btamavg_yesterday;

    private BigDecimal btamavg_decline_rate;

    private BigDecimal promotion_all_cnt;

    private BigDecimal APPLETZCS_cnt;

    private BigDecimal promotion_all_cnt_incrate;

    private BigDecimal APPLETZCS_cnt_incrate;

    private BigDecimal promotion_all_cnt_incrate_2d;

    private BigDecimal APPLETZCS_cnt_incrate_2d;

    private BigDecimal highamt_ratio_today;

    private BigDecimal highamt_ratio_yesterday;

    private BigDecimal highamt_ratio_decline_rate;

    private BigDecimal highcnt_ratio_today;

    private BigDecimal highcnt_ratio_yesterday;

    private BigDecimal highcnt_ratio_decline_rate;

    private BigDecimal arpu_today;

    private BigDecimal arpu_yesterday;

    private BigDecimal arpu_decline_rate;

    private BigDecimal dau_today;

    private BigDecimal dau_yesterday;

    private BigDecimal dau_decline_rate;

    private BigDecimal retention_7d_today;

    private BigDecimal retention_7d_yesterday;

    private BigDecimal retention_7d_decline_rate;

    private BigDecimal New_ratio_today;

    private BigDecimal New_ratio_yesterday;

    private BigDecimal New_ratio_decline_rate;

    private BigDecimal Growing_ratio_today;

    private BigDecimal Growing_ratio_yesterday;

    private BigDecimal Growing_ratio_decline_rate;

    private BigDecimal Stable_ratio_today;

    private BigDecimal Stable_ratio_yesterday;

    private BigDecimal Stable_ratio_decline_rate;

}
