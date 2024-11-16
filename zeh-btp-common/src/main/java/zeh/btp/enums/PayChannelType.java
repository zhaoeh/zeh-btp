package zeh.btp.enums;

import java.util.Arrays;

/**
 * @Description 支付渠道
 * @Classname PayChannelType
 * @Date 2023/6/15 15:02
 * @Created by TJSLucian
 */
public enum PayChannelType {

    MARKET("1", "Market", "^mkt|^pc|^pa|^answer|^team|^vivo|^sanxingmarket|^oppomarket|^huaweiapp|^xiaomimarket"),
    WEBSITE("2", "Website", "mkt|pc|pa|answer|team|vivo|sanxingmarket|oppomarket|huaweiapp|xiaomimarket|glife|a00|b00|c0|bc|gpoagentbp"),
    STORE("3", "Store", "^a00|^b00|^c0"),
    GLIFE("4", "Glife", "^glife|^gpoagentbp"),
    BLUE_CHIP("5", "Blue chip", "^bc");


    private final String code;
    private final String name;
    private final String value;

    PayChannelType(String code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static PayChannelType getPayChannelTypeByCode(String code) {
        return Arrays.stream(PayChannelType.values()).filter(p -> p.getCode().equals(code)).findFirst().get();
    }

}
