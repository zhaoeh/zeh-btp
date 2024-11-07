package jp.co.futech.module.insight.entity.bh.risk;

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
@TableName("t_account_risk_list")
public class AccountRiskListEntity {

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
}
