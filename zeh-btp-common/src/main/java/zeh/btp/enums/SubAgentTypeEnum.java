package zeh.btp.enums;

public enum SubAgentTypeEnum {
    DIRECT_AGENTS("DIRECT_AGENTS", "Direct Agents"),
    DOWNLINE_AGENTS("DOWNLINE_AGENTS", "Downline_Agents");


    private final String code;
    private final String value;


    SubAgentTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }


    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}