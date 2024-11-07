package ft.btp.enums;

import lombok.Getter;

/**
 * 拦截状态
 *
 * @author dante
 * @date 2024/04/19
 */
@Getter
public enum RiskFilterStatusEnum {
    INITIAL(0, "initial/初始"),
    RELEASED(1, "Released/已放行"),
    PENDING_REVIEW(2, "Pending review/待审核"),
    INTERCEPTED(3, "Intercepted/已拦截"),
    ;

    private final Integer code;

    private final String desc;

    RiskFilterStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
