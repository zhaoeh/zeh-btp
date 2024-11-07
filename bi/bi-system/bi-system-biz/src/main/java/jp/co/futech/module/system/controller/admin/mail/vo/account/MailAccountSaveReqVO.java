package jp.co.futech.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 邮箱账号创建/修改 Request VO")
@Data
public class MailAccountSaveReqVO {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "biyuanma@123.com")
    @NotNull(message = "{100000019}{000000001}")
    @Email(message = "{000000007}")
    private String mail;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "bi")
    @NotNull(message = "{100000034}{000000001}")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotNull(message = "{100000011}{000000001}")
    private String password;

    @Schema(description = "SMTP 服务器域名", requiredMode = Schema.RequiredMode.REQUIRED, example = "www.iocoder.cn")
    @NotNull(message = "SMTP {100000035}{000000001}")
    private String host;

    @Schema(description = "SMTP 服务器端口", requiredMode = Schema.RequiredMode.REQUIRED, example = "80")
    @NotNull(message = "SMTP {100000036}{000000001}")
    private Integer port;

    @Schema(description = "是否开启 ssl", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "{100000037} ssl {000000001}")
    private Boolean sslEnable;

}
