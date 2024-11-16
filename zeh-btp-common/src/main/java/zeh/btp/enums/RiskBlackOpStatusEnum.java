package zeh.btp.enums;


/**
 * @author Heng.zhang
 */

public enum RiskBlackOpStatusEnum {

    IN_PROGRESS(0, "执行中"),
    FINISHED(1, "执行完成"),
    ;

    private final int code;
    private final String description;

    // 构造方法
    RiskBlackOpStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    // 获取描述的方法
    public String getDescription() {
        return description;
    }

    // 通过 code 获取枚举的描述
    public static String getDescriptionByCode(int code) {
        for (RiskBlackOpStatusEnum status : RiskBlackOpStatusEnum.values()) {
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
