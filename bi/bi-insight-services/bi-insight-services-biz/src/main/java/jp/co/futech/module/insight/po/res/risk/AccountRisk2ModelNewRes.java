package jp.co.futech.module.insight.po.res.risk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AccountRisk2ModelNewRes {

    @JsonIgnore
    @Schema(description = "日期")
    private String dt;

    @JsonIgnore
    @Schema(description = "日期")
    private String loginName;

    @JsonProperty("user_features")
    @Schema(description = "user features")
    private JsonNode features;

    @JsonProperty("users_features_str")
    @JsonIgnore
    @Schema(description = "users_features_str")
    private String usersFeaturesStr;

    @JsonProperty("user_actions")
    @Schema(description = "user_actions")
    private List<UserAction> userActions;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private String windowStart;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private String windowEnd;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private String transDate;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private String ts;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private String ipAddress;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private String deviceId;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private Integer transIdCnt;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private Integer transCardCnt;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private Integer transCnt;

    @com.fasterxml.jackson.annotation.JsonView(JsonViews.MyView.class)
    private BigDecimal transAmt;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserAction {
        @JsonProperty("login_name")
        @Schema(description = "登录名称")
        private String loginName;

        @JsonProperty("user_name")
        @Schema(description = "用户名称")
        private String userName;

        @JsonProperty("billtime")
        @Schema(description = "billtime")
        private String billtime;

        @JsonProperty("device_id")
        @Schema(description = "device_id")
        private String deviceId;

        @JsonProperty("ip")
        @Schema(description = "ip")
        private String ip;

        @JsonProperty("game_name")
        @Schema(description = "game_name")
        private String gameName;

        @JsonProperty("game_id")
        @Schema(description = "game_id")
        private String gameId;

        @JsonProperty("bet_amount")
        @Schema(description = "bet_amount")
        private BigDecimal betAmount;

        @JsonProperty("win_amount")
        @Schema(description = "win_amount")
        private BigDecimal winAmount;

        @JsonProperty("date")
        @Schema(description = "date")
        private String dt;
    }

    public interface JsonViews {
        public class MyView {
        }
    }
}
