package zeh.btp.enums;

/**
 * @Description 代理关系发生变更后，需要进行数据更新的功能
 * @Classname AgentUpdateLogEnum
 * @Date 2024/2/8 11:57
 * @Created by TJSLucian
 */
public enum AgentUpdateLogEnum {

    DASHBOARD("dashboard", "仪表盘数据需要更新"),
    PLAYERREPORT("playerReport", "player_report数据需要更新");

    private String type;

    private String desc;

    AgentUpdateLogEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
