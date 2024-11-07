package jp.co.futech.module.insight.po.res.risk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AccountRisk2ModelNewShowRes {

    @JsonIgnore
    @Schema(description = "日期")
    private String dt;

    @JsonIgnore
    @Schema(description = "日期")
    private String loginName;

    @JsonProperty("user_features")
    @Schema(description = "user features")
    private JsonNode features;

    @JsonProperty("user_actions")
    @Schema(description = "user_actions")
    private List<AccountRisk2ModelNewRes.UserAction> userActions;
}
