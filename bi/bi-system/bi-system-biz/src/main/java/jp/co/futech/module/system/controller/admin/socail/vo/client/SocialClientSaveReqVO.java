package jp.co.futech.module.system.controller.admin.socail.vo.client;

import cn.hutool.core.util.StrUtil;
import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.enums.UserTypeEnum;
import jp.co.futech.framework.common.validation.InEnum;
import jp.co.futech.module.system.enums.social.SocialTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@Schema(description = "管理后台 - 社交客户端创建/修改 Request VO")
@Data
public class SocialClientSaveReqVO {

    @Schema(description = "编号", example = "27162")
    private Long id;

    @Schema(description = "应用名", requiredMode = Schema.RequiredMode.REQUIRED, example = "bi商城")
    @NotNull(message = "{100000025}{000000001}")
    private String name;

    @Schema(description = "社交平台的类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "31")
    @NotNull(message = "{100000016}{000000001}")
    @InEnum(SocialTypeEnum.class)
    private Integer socialType;

    @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "{100000054}{000000001}")
    @InEnum(UserTypeEnum.class)
    private Integer userType;

    @Schema(description = "客户端编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "wwd411c69a39ad2e54")
    @NotNull(message = "{100000055}{000000001}")
    private String clientId;

    @Schema(description = "客户端密钥", requiredMode = Schema.RequiredMode.REQUIRED, example = "peter")
    @NotNull(message = "{100000056}{000000001}")
    private String clientSecret;

    @Schema(description = "授权方的网页应用编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000045")
    private String agentId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000005}{000000001}")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @AssertTrue(message = "agentId {000000001}")
    @JsonIgnore
    public boolean isAgentIdValid() {
        // 如果是企业微信，必须填写 agentId 属性
        return !Objects.equals(socialType, SocialTypeEnum.WECHAT_ENTERPRISE.getType())
                || !StrUtil.isEmpty(agentId);
    }

}
