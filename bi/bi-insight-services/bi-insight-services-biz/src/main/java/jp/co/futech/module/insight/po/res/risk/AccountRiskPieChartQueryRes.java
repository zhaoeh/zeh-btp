package jp.co.futech.module.insight.po.res.risk;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Map;

/**
 * @description: 饼图响应
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRiskPieChartQueryRes {

//    @JsonProperty("Frequent cross-city logins with multiple devices")
    @Schema(description = "ruleUserCnt")
    private String ruleUserCnt;

//    @JsonProperty("Frequent cross-city logins with short durations")
//    @Schema(description = "rule2Users")
//    private Integer rule2Users;
//
//    @JsonProperty("Unusual city logins with multiple users on same IP")
//    @Schema(description = "rule3Users")
//    private Integer rule3Users;
//
//    @JsonProperty("Unusual city logins with multiple orders on same IP and device")
//    @Schema(description = "rule4Users")
//    private Integer rule4Users;
//
//    @JsonProperty("Unusual city logins with multiple users on same device")
//    @Schema(description = "rule5Users")
//    private Integer rule5Users;
//
    @JsonIgnore
    @Schema(description = "totalUsers")
    private Integer totalUsers;


    private static final ObjectMapper objectMapper = new ObjectMapper();

    public AccountRiskPieChartQueryRes(String ruleUserCnt, int totalUsers) {
        this.ruleUserCnt = ruleUserCnt;
        this.totalUsers = totalUsers;
    }

    public Map<String, String> getRuleUserCnt() {
        try {
            // 将 JSON 字符串解析为 Map
            return objectMapper.readValue(ruleUserCnt, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
}
