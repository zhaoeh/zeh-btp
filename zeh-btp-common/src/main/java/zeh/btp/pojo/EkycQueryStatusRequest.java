package zeh.btp.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 查询ekyc状态请求体
 * @author: ErHu.Zhao
 * @create: 2024-10-07
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EkycQueryStatusRequest {

    @NotNull
    private Integer customerId;

    @NotBlank
    private String loginName;

    @NotNull
    private Boolean needOldKycStatus;

    @NotNull
    private Boolean noNeedEx;
}
