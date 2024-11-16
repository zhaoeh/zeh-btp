package zeh.btp.enums;

/**
 * 代理转账状态值 --无审核逻辑
 */
public enum ATransferCreditModeEnum {

    //给玩家加额度
    ADD("113508", "IN"),

    //给玩家减额度
    SUBTRACT("113507", "OUT");

    private String code;
    private String str;

    ATransferCreditModeEnum(String code, String str) {
        this.code = code;
        this.str = str;
    }

    public static ATransferCreditModeEnum getAdd() {
        return ADD;
    }

    public static ATransferCreditModeEnum getSubtract() {
        return SUBTRACT;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}