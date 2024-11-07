package jp.co.futech.module.insight.entity.pg.risk;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: t_account_risk_list 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ai_risk_control_account_list")
public class PgAccountRiskListEntity {

    @TableField("login_name")
    @JsonProperty("login_name")
    @ExcelProperty(value = "Login Name", index = 1)
    @Schema(description = "登陆名称")
    private String loginName;

    @TableField("ip")
    @JsonProperty("ip")
    @ExcelProperty(value = "Ip", index = 3)
    @Schema(description = "ip")
    private String ip;

    @TableField("device_id")
    @JsonProperty("device_id")
    @ExcelProperty(value = "Device Id", index = 2)
    @Schema(description = "设备id")
    private String deviceId;
    @TableField("group_id")
    @JsonProperty("group_id")
    @ExcelProperty(value = "Group Id", index = 15)
    @Schema(description = "组id")
    private String groupId;

    @TableField("ip_country")
    @JsonIgnore
    @ExcelIgnore
    @Schema(description = "ip属地")
    private String ipCountry;

    @TableField("ip_city")
    @JsonProperty("ip_city")
    @ExcelProperty(value = "Ip City", index = 4)
    @Schema(description = "ip属地城市")
    private String ipCity;

    @TableField("primary_risk_type")
    @JsonProperty("primary_risk_type")
    @ExcelProperty(value = "Primary Risk Type", index = 5)
    @Schema(description = "Primary Risk Type")
    private String primaryRiskType;

    @TableField("secondary_risk_type")
    @JsonProperty("secondary_risk_type")
    @ExcelProperty(value = "Secondary Risk Type", index = 6)
    @Schema(description = "Secondary Risk Type")
    private String secondaryRiskType;

    @TableField("risk_desc")
    @JsonProperty("risk_desc")
    @ExcelProperty(value = "Risk Desc", index = 7)
    @Schema(description = "Risk Desc")
    private String riskDesc;

    @TableField("date")
    @JsonProperty("date")
    @ExcelProperty(value = "Date", index = 0)
    @Schema(description = "日期")
    private String date;


    @TableField("win_amount")
    @JsonProperty("win_amount")
    @ExcelProperty(value = "Win Amount", index = 8)
    @Schema(description = "Win Amount")
    private String winAmount;
    @TableField("bet_amount")
    @JsonProperty("bet_amount")
    @ExcelProperty(value = "Bet Amount", index = 9)
    @Schema(description = "Bet Amount")
    private String betAmount;
    @TableField("withdrawal_amount")
    @JsonProperty("withdrawal_amount")
    @ExcelProperty(value = "Withdrawal Amount", index = 10)
    @Schema(description = "Withdrawal Amount")
    private String withdrawalAmount;
    @TableField("deposit_amount")
    @JsonProperty("deposit_amount")
    @ExcelProperty(value = "Deposit Amount", index = 11)
    @Schema(description = "Deposit Amount")
    private String depositAmount;
    @TableField("risk_score")
    @JsonProperty("risk_score")
    @ExcelProperty(value = "Risk Score", index = 12)
    @Schema(description = "Risk Score")
    private String riskScore;
    @TableField("risk_level")
    @JsonProperty("risk_level")
    @ExcelProperty(value = "Risk Level", index = 13)
    @Schema(description = "Risk Level")
    private String riskRevel;
    @TableField("register_time")
    @JsonProperty("register_time")
    @ExcelProperty(value = "Register time", index = 14)
    @Schema(description = "Register time")
    private String registerTime;
}
