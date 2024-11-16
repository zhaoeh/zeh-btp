package zeh.btp.enums;


/**
 * @author Heng.zhang
 */

public enum RiskBlackSourceEnum {

    MANUAL(0, "人工"),
    RELATION(1, "关联匹配"),
    PAGCOR(2, "pagcor黑名单");
    private final int code;
    private final String description;

    // 构造方法
    RiskBlackSourceEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    // 获取描述的方法
    public String getDescription() {
        return description;
    }

    // 通过 code 获取枚举的描述
    public static String getDescriptionByCode(int code) {
        for (RiskBlackSourceEnum status : RiskBlackSourceEnum.values()) {
            if (status.code == code) {
                return status.getDescription();
            }
        }
        return "未知状态"; // 如果没有匹配的code，返回默认值
    }

    // 获取code
    public int getCode() {
        return code;
    }
}
