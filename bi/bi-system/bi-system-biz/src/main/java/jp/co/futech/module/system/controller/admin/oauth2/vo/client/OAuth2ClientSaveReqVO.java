package jp.co.futech.module.system.controller.admin.oauth2.vo.client;

import cn.hutool.core.util.StrUtil;
import jp.co.futech.framework.common.util.json.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - OAuth2 客户端创建/修改 Request VO")
@Data
public class OAuth2ClientSaveReqVO {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "客户端编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "tudou")
    @NotNull(message = "{100000055}{000000001}")
    private String clientId;

    @Schema(description = "客户端密钥", requiredMode = Schema.RequiredMode.REQUIRED, example = "fan")
    @NotNull(message = "{100000056}{000000001}")
    private String secret;

    @Schema(description = "应用名", requiredMode = Schema.RequiredMode.REQUIRED, example = "土豆")
    @NotNull(message = "{100000025}{000000001}")
    private String name;

    @Schema(description = "应用图标", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/xx.png")
    @NotNull(message = "{100000057}{000000001}")
    @URL(message = "{100000057}{000000013}")
    private String logo;

    @Schema(description = "应用描述", example = "我是一个应用")
    private String description;

    @Schema(description = "状态，参见 CommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "{100000005}{000000001}")
    private Integer status;

    @Schema(description = "访问令牌的有效期", requiredMode = Schema.RequiredMode.REQUIRED, example = "8640")
    @NotNull(message = "{100000058}{000000001}")
    private Integer accessTokenValiditySeconds;

    @Schema(description = "刷新令牌的有效期", requiredMode = Schema.RequiredMode.REQUIRED, example = "8640000")
    @NotNull(message = "{100000059}{000000001}")
    private Integer refreshTokenValiditySeconds;

    @Schema(description = "可重定向的 URI 地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "{100000060}{000000001}")
    private List<@NotEmpty(message = "{100000061}{000000014}") @URL(message = "{100000061}{000000013}") String> redirectUris;

    @Schema(description = "授权类型，参见 OAuth2GrantTypeEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "password")
    @NotNull(message = "{100000062}{000000001}")
    private List<String> authorizedGrantTypes;

    @Schema(description = "授权范围", example = "user_info")
    private List<String> scopes;

    @Schema(description = "自动通过的授权范围", example = "user_info")
    private List<String> autoApproveScopes;

    @Schema(description = "权限", example = "system:user:query")
    private List<String> authorities;

    @Schema(description = "资源", example = "1024")
    private List<String> resourceIds;

    @Schema(description = "附加信息", example = "{yunai: true}")
    private String additionalInformation;

    @AssertTrue(message = "{000000015}")
    public boolean isAdditionalInformationJson() {
        return StrUtil.isEmpty(additionalInformation) || JsonUtils.isJson(additionalInformation);
    }

}
