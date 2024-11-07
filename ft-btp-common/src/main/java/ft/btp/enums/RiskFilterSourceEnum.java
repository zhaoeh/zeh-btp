package ft.btp.enums;

import lombok.Getter;

/**
 * 拦截来源
 *
 * @author dante
 * @date 2024/04/19
 */
@Getter
public enum RiskFilterSourceEnum {
    WITHDRAW_RISK("withdraw_risk", "withdraw risk/取款风控拦截"),

    ;

    private final String code;

    private final String desc;

    RiskFilterSourceEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
