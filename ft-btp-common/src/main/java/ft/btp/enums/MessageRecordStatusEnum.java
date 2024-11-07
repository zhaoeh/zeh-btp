package ft.btp.enums;

/**
 * @program: riskcontrol-cron
 * @description: 消息处理状态：0-待消费，1-已消费，2-消费失败
 * @author: Colson
 * @create: 2023-10-16 17:12
 */
public enum MessageRecordStatusEnum {

    // 0-待消费
    TO_BE_CONSUMED(0, "待消费"),
    // 1-已消费
    CONSUMED(1, "已消费"),
    // 2-消费失败
    CONSUMPTION_FAILURE(2, "消费失败"),
    ;

    String text;
    Integer value;

    MessageRecordStatusEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }
}
