package ft.btp.enums;

public enum PlayInfoStatusEnum {

    INITIATE("客户投注记录初始化", 0),
    UPDATE("客户投注记录已更新", 1),


    ;

    private String des;

    private Integer value;

    PlayInfoStatusEnum(String des, Integer value) {

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
