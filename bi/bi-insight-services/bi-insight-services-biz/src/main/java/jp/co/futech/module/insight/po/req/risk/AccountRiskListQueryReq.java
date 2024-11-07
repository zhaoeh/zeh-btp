package jp.co.futech.module.insight.po.req.risk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jp.co.futech.framework.common.pojo.SortablePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @description: account risk列表查询请求
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Schema(description = "账号风控 - 详情列表查询分页 Request")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountRiskListQueryReq extends SortablePageParam {

    @Valid
    @NotNull
    @JsonProperty("base")
    private AccountRiskBaseReq baseQuery;

    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "sam")
    @JsonProperty("loginName")
    private String loginName;

    @Schema(description = "主风控类型，取值[1-3]", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    @JsonProperty("primaryRiskType")
    @Pattern(regexp = "^([1-3])?$", message = "primaryRiskType {000000006}")
    private String primaryRiskType;

    @JsonIgnore
    private String primaryRiskTypeFormat;

    @Schema(description = "次风控类型，取值[1-4]", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2")
    @JsonProperty("secondaryRiskType")
    @Pattern(regexp = "^([1-4])?$", message = "secondaryRiskType {000000006}")
    private String secondaryRiskType;

    @Schema(description = "风控等级类型，取值[1-3]", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "low")
    @JsonProperty("riskLevel")
    private String riskLevel;

    @JsonIgnore
    private String secondaryRiskTypeFormat;


    public String gStartDate() {
        return baseQuery.getStartDate();
    }

    public String gEndDate() {
        return baseQuery.getEndDate();
    }

}
