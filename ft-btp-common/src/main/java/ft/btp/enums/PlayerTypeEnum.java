package ft.btp.enums;

public enum PlayerTypeEnum {

    ALL("All", 0),
    DIRECT_USERS("Direct Users", 1),
    DOWNLINE_USERS("Downline Users", 2),


    ;

    private String value;

    private Integer code;

    PlayerTypeEnum(String value, Integer code) {

        this.value = value;
        this.code = code;

    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }

    public static String getValueByCode(Integer code) {
        for (PlayerTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getValue();
            }
        }
        return null;
    }
}
