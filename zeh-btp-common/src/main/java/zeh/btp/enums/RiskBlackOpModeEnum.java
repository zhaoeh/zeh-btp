package zeh.btp.enums;


/**
 * @author Heng.zhang
 */

public enum RiskBlackOpModeEnum {

    IMPORT_MODE(0, "导入模式"),
    RELATION_MODE(1, "关联模式"),
    MANUAL_MODE(2, "手动更新"),
    ;

    private final int code;
    private final String description;

    // 构造方法
    RiskBlackOpModeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    // 获取描述的方法
    public String getDescription() {
        return description;
    }

    // 通过 code 获取枚举的描述
    public static String getDescriptionByCode(int code) {
        for (RiskBlackOpModeEnum mode : RiskBlackOpModeEnum.values()) {
            if (mode.code == code) {
                return mode.getDescription();
            }
        }
        return "未知模式"; // 如果没有匹配的code，返回默认值
    }

    // 获取code
    public int getCode() {
        return code;
    }
}
