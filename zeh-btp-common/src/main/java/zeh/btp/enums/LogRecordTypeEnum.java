package zeh.btp.enums;

/**
 * @program: riskcontrol-cron
 * @description: 日志类型：ERROR，INFO，...
 * @author: Colson
 * @create: 2023-10-19 14:12
 */
public enum LogRecordTypeEnum {

    // ERROR
    ERROR("ERROR", "错误日志"),
    // WARN
    WARN("WARN", "警告日志"),
    // INFO
    INFO("INFO", "普通日志"),
    ;

    String text;
    String value;

    LogRecordTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }
}
