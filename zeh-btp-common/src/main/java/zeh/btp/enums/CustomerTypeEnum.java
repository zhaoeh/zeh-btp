package zeh.btp.enums;

public enum CustomerTypeEnum {

    PLAYER("PLAYER", 1),
    AGENT("AGENT", 3),
    SYSTEM("SYSTEM",-1);

    private String des;

    private Integer value;

    CustomerTypeEnum(String des, Integer value) {

        this.des = des;
        this.value = value;

    }

    public String getDes() {
        return des;
    }

    public Integer getValue() {
        return value;
    }

    public static Integer getValueByDesc(String desc) {
        for (CustomerTypeEnum status : values()) {
            if (status.getDes().equals(desc)) {
                return status.getValue();
            }
        }
        return null;
    }
}
