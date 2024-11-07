package jp.co.futech.module.insight.po.res.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jp.co.futech.framework.jackson.core.databind.BigDecimalSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 风控二期查询结果res
 * @author: ErHu.Zhao
 * @create: 2024-07-17
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRisk2ModelRes {

    @JsonProperty("user features")
    @Schema(description = "user features")
    private JsonNode features;

    @JsonProperty("user actions")
    @Schema(description = "user actions")
    private List<JsonNode> userActions;

    @JsonProperty("user_action_list")
    @JsonIgnore
    @Schema(description = "user_action_list")
    private List<UserAction> userActionList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserAction {
        @JsonIgnore
        @Schema(description = "日期")
        private String dt;

        @JsonIgnore
        @Schema(description = "日期")
        private String loginName;

        @JsonIgnore
        @Schema(description = "score")
        @TableField(jdbcType = JdbcType.DECIMAL)
        @JsonSerialize(using = BigDecimalSerializer.class)
        private BigDecimal score;

        @JsonProperty("users_features_str")
        @JsonIgnore
        @Schema(description = "users_features_str")
        private String usersFeaturesStr;

        @JsonIgnore
        @Schema(description = "users_actions_str")
        private String usersActionsStr;

        @JsonIgnore
        @Schema(description = "users_actions")
        private JsonNode usersActions;
    }

}
