package jp.co.futech.module.system.controller.admin.user.vo.user;

import cn.hutool.core.util.ObjectUtil;
import jp.co.futech.framework.common.validation.Mobile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.*;
import java.util.Set;

@Schema(description = "管理后台 - 用户创建/修改 Request VO")
@Data
public class UserSaveReqVO {

    @Schema(description = "用户编号", example = "1024")
    private Long id;

    @Schema(description = "用户账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "bi")
    @NotBlank(message = "{100000095}{000000004}")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "{100000095}{000000018}")
    @Size(min = 4, max = 30, message = "{100000095}{000000002}")
    private String username;

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @Size(max = 30, message = "{100000063}{000000003}")
    private String nickname;

    @Schema(description = "备注", example = "我是一个用户")
    private String remark;

    @Schema(description = "部门ID", example = "我是一个用户")
    private Long deptId;

    @Schema(description = "岗位编号数组", example = "1")
    private Set<Long> postIds;

    @Schema(description = "用户邮箱", example = "bi@iocoder.cn")
    @Email(message = "{000000007}")
    @Size(max = 50, message = "{100000019}{000000003}")
    private String email;

    @Schema(description = "手机号码", example = "15601691300")
    @Mobile
    private String mobile;

    @Schema(description = "用户性别，参见 SexEnum 枚举类", example = "1")
    private Integer sex;

    @Schema(description = "用户头像", example = "https://www.iocoder.cn/xxx.png")
    private String avatar;

    // ========== 仅【创建】时，需要传递的字段 ==========

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @Length(min = 4, max = 16, message = "{100000011}{000000002}")
    private String password;

    @AssertTrue(message = "{100000011}{000000001}")
    @JsonIgnore
    public boolean isPasswordValid() {
        return id != null // 修改时，不需要传递
                || (ObjectUtil.isAllNotEmpty(password)); // 新增时，必须都传递 password
    }

}
