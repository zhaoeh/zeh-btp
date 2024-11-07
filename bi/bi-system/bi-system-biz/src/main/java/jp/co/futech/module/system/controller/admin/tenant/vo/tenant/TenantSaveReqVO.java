package jp.co.futech.module.system.controller.admin.tenant.vo.tenant;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 租户创建/修改 Request VO")
@Data
public class TenantSaveReqVO {

    @Schema(description = "租户编号", example = "1024")
    private Long id;

    @Schema(description = "租户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotNull(message = "{100000089}{000000001}")
    private String name;

    @Schema(description = "联系人", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "{100000090}{000000001}")
    private String contactName;

    @Schema(description = "联系手机", example = "15601691300")
    private String contactMobile;

    @Schema(description = "租户状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000091}{000000001}")
    private Integer status;

    @Schema(description = "绑定域名", example = "https://www.iocoder.cn")
    private String website;

    @Schema(description = "租户套餐编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "{100000092}{000000001}")
    private Long packageId;

    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "{100000093}{000000001}")
    private LocalDateTime expireTime;

    @Schema(description = "账号数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "{100000094}{000000001}")
    private Integer accountCount;

    // ========== 仅【创建】时，需要传递的字段 ==========

    @Schema(description = "用户账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "bi")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "{100000095}{000000018}")
    @Size(min = 4, max = 30, message = "{100000095}{000000002}")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @Length(min = 4, max = 16, message = "{100000011}{000000002}")
    private String password;

    @AssertTrue(message = "{000000017}")
    @JsonIgnore
    public boolean isUsernameValid() {
        return id != null // 修改时，不需要传递
                || (ObjectUtil.isAllNotEmpty(username, password)); // 新增时，必须都传递 username、password
    }

}
