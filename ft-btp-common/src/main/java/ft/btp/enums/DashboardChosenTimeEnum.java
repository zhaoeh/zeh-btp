package ft.btp.enums;

/**
 * @ClassName FundTypeEnum
 * @Description 账变类型枚举
 * @Author TJSAlex
 * @Date 2023/5/19 16:49
 * @Version 1.0
 **/
public enum DashboardChosenTimeEnum {

    ChosenType_Month(1001, "Month", "月"),
    ChosenType_Week(1002, "Week", "周"),
    ChosenType_Day(1003, "Day", "天"),

    ChosenPeriod_Current(2001, "current", "当前月/周/天"),

    ChosenPeriod_Last(2002, "last", "上个月/周/天"),
    ChosenPeriod_Last_Two(2003, "last_two", "上上个月/周/天");


    private final Integer code;
    private final String name;
    private final String desc;

    DashboardChosenTimeEnum(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
