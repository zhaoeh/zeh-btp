package ft.btp.enums;

/**
 * @Description: mq 交换机和数据里的routingKey枚举
 * @Auther: yannis
 * @create: 2023-10-25
 */
public enum MQExchangeEnum {
    //取款申请
    WITHDRAW_APPLY("exchange_withdraw_apply", "customer.withdraw.apply");
    private String exchange;
    private String routingKey;

    MQExchangeEnum(String exchange, String routingKey) {
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public String getExchange() {
        return exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}

