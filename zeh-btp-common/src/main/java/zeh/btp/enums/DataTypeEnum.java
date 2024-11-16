package zeh.btp.enums;

public enum DataTypeEnum {

    Turnover("Turnover", 1),
    GGR("GGR", 2),
    WinOrLoss("WinOrLoss", 3),

    ;

    private String des;

    private Integer value;

    DataTypeEnum(String des, Integer value) {

        this.des = des;
        this.value = value;

    }

    public String getDes() {
        return des;
    }

    public Integer getValue() {
        return value;
    }
}
