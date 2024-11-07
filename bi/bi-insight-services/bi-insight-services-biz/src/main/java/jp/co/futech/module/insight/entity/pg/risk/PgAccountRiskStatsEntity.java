package jp.co.futech.module.insight.entity.pg.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: t_account_risk_stat 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ai_risk_control_account_stats")
public class PgAccountRiskStatsEntity {

    @TableField("date")
    private String date;

    @TableField("time_range")
    private String timeRange;

    @TableField("rule_user_cnt")
    private Integer rule_user_cnt;

    @TableField("total_users")
    private Integer totalUsers;
}
