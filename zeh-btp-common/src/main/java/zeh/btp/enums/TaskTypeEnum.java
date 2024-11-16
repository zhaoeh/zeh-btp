package zeh.btp.enums;

/**
 * @ClassName TaskTypeEnum
 * @Description 任务类型枚举
 * @Author Amida
 * @Date 2023/5/24 10：00
 * @Version 1.0
 **/
public enum TaskTypeEnum {

    MONTH(1, "MONTH"),
    DAY(2, "DAY"),

    INIT(0, "INIT"),
    ;

    private Integer code;

    private String des;

    TaskTypeEnum(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public Integer getCode() {
        return code;
    }


}
