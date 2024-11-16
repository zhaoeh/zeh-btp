package zeh.btp.enums;

/**
 * @description: 风控导入模式枚举
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
public enum RiskImportModelEnum {

    IMPORT_MODE(0, "导入模式"),
    ASSOCIATION_MODE(1, "关联模式"),
    ;

    private Integer type;
    private String description;

    RiskImportModelEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
