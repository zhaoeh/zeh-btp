package jp.co.futech.module.system.controller.admin.oauth2.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "管理后台 - OAuth2 更新用户基本信息 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserUpdateReqVO {

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @Size(max = 30, message = "{100000063}{000000003}")
    private String nickname;

    @Schema(description = "用户邮箱", example = "bi@iocoder.cn")
    @Email(message = "{000000007}")
    @Size(max = 50, message = "{100000019}{000000003}")
    private String email;

    @Schema(description = "手机号码", example = "15601691300")
    @Length(min = 11, max = 11, message = "{100000014}{000000016}")
    private String mobile;

    @Schema(description = "用户性别，参见 SexEnum 枚举类", example = "1")
    private Integer sex;

}
