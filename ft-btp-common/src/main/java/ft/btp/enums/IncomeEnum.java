package ft.btp.enums;

public enum IncomeEnum {
    OUT(0, "出账"), In(1, "入账");

    public Integer code;
    public String name;

    IncomeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}