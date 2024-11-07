package jp.co.futech.module.insight.enums.risk;

/**
 * @description: choose type enum
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
public enum ChooseTypeEnums {
    DAY("1", "按天查询"), MONTH("2", "按月查询");

    private String code;

    private String desc;

    ChooseTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
