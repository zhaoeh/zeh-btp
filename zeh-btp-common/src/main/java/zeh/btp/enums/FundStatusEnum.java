package zeh.btp.enums;

/**
 * @ClassName FundStatusEnum
 * @Description 账变状态枚举
 * @Author TJSAlex
 * @Date 2023/5/19 17:03
 * @Version 1.0
 **/
public enum FundStatusEnum {

    Pending(0, "Pending"),
    Success(1, "Success"),
    Failure(2, "Failure");

    private Integer code;
    private String name;

    FundStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Integer getCodeByName(String name) {
        for (FundStatusEnum status : values()) {
            if (status.getName().equals(name)) {
                return status.getCode();
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (FundStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return null;
    }
}