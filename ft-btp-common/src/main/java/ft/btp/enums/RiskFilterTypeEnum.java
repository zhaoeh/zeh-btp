package ft.btp.enums;

import lombok.Getter;

/**
 * 拦截类型
 *
 * @author dante
 * @date 2024/04/19
 */
@Getter
public enum RiskFilterTypeEnum {
    WITHDRAW_RISK("withdraw", "withdraw risk/取款风控拦截"),

    ;

    private final String code;

    private final String desc;

    RiskFilterTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
