package zeh.btp.enums;

/**
 * @ClassName YesNoEnum
 * @Description 常用枚举
 * @Author Erhu.Zhao
 * @Date 2023/10/18 15:42
 * @Version 1.0
 **/
public enum YesNoEnum {
    YES("1", ""),
    NO("0", "");

    private String code;

    private String desc;

    YesNoEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public Integer getIntCode() {
        return Integer.parseInt(code);
    }

    public String getDesc() {
        return desc;
    }
}
