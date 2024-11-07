package jp.co.futech.module.insight.po.req.risk;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jp.co.futech.framework.common.pojo.PageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 风控二期查询请求
 * @author: ErHu.Zhao
 * @create: 2024-07-17
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRisk2ModelNewReq extends PageParam {

    @JsonProperty("dt")
    @Schema(description = "日期")
    @NotNull(message = "dt {000000001}")
    private String dt;

    @JsonProperty("login_name")
    @Schema(description = "用户名称")
    @NotNull(message = "loginName {000000001}")
    private String loginName;

    @JsonProperty("order_fields")
    @Schema(description = "排序模型")
    private List<OrderField> orderFields;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderField {
        private String field;

        private String order;
    }
}
