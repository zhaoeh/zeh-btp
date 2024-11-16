package zeh.btp.enums;

/**
 * @program: riskcontrol-cron
 * @description: 手机号码黑名单状态枚举，状态：0-失效，1-生效中
 * @author: Colson
 * @create: 2023-09-27 12:12
 */
public enum PhoneNumberBlacklistStatusEnum {

    // 0-失效
    INVALID(0, "失效"),
    // 1-生效中
    IN_EFFECT(1, "生效中"),
    ;

    String text;
    Integer value;

    PhoneNumberBlacklistStatusEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }
}
