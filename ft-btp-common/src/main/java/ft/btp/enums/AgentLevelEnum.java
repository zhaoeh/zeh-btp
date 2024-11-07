package ft.btp.enums;

import java.util.Objects;

public enum AgentLevelEnum {

    AGENT_TOP("顶级代理", 1, "A"),
    AGENT_2("2级代理", 2, "A"),
    AGENT_3("3级代理", 3, "A"),
    AGENT_4("4级代理", 4, "A"),
    AGENT_5("5级代理", 5, "A"),
    AGENT_NON("普通玩家custom type中的1", 0, "P"),


    DIRECT_PLAYER("直属玩家", 6, "P"),

    ;

    private String des;

    private Integer code;

    private String tag;

    AgentLevelEnum(String des, Integer code, String tag) {

        this.des = des;
        this.code = code;
        this.tag = tag;

    }

    public String getDes() {
        return des;
    }

    public Integer getCode() {
        return code;
    }

    public String getTag() {
        return tag;
    }


    public static AgentLevelEnum getByCode(Integer code) {

        if (Objects.isNull(code)) {
            throw new IllegalArgumentException("Invalid value for AgentLevelEnum : " + code + "is not blank");
        }

        for (AgentLevelEnum agentLevelEnum : AgentLevelEnum.values()) {
            if (agentLevelEnum.code.equals(code)) {
                return agentLevelEnum;
            }
        }
        throw new IllegalArgumentException("Invalid value for AgentLevelEnum : " + code + " error ");
    }

}
