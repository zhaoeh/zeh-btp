package jp.co.futech.module.insight.entity.bh.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: t_daily_user_risk_model 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_daily_user_risk_model")
public class AccountRisk2ModelEntity {

    @TableField("dt")
    @JsonProperty("dt")
    @Schema(description = "日期")
    private String dt;

    @TableField("login_name")
    @JsonProperty("login_name")
    @Schema(description = "玩家名字")
    private String loginName;

    @TableField("score_array")
    @JsonProperty("score_array")
    @Schema(description = "模型分")
    private List<BigDecimal> scoreArray;

    @TableField("users_features_array")
    @JsonProperty("users_features_array")
    @Schema(description = "users_features的json")
    private List<String> usersFeaturesArray;

    @TableField("users_actions_array")
    @JsonProperty("users_actions_array")
    @Schema(description = "users_actions的json")
    private List<String> usersActionsArray;

}
