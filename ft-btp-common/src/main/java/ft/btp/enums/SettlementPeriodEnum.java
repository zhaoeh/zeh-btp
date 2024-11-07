package ft.btp.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @ClassName CommissionValuesEnum
 * @Description 佣金方案结算周期枚举
 * @Author TJSAustin
 * @Date 2023/5/20 10：00
 * @Version 1.0
 **/
public enum SettlementPeriodEnum {

    MONTH("MONTH", "月", 30),
    ONE_THIRD_MONTH("ONE-THIRD_MONTH", "三分之一月", 10),
    WEEK("WEEK", "周", 7),
    DAY("DAY", "日", 1);

    private String code;

    private String value;

    private Integer dayNum;

    SettlementPeriodEnum(String code, String value, Integer dayNum) {
        this.code = code;
        this.value = value;
        this.dayNum = dayNum;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


    public Integer getDayNum() {
        return dayNum;
    }

    /**
     * 根据枚举class类,枚举值获取枚举
     *
     * @author Austin
     * @data 2023-05-15
     **/
    public static SettlementPeriodEnum getByCode(String code) {

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Invalid value for CommissionValuesEnum : " + code + "is not blank");
        }

        for (SettlementPeriodEnum settlementPeriodEnum : SettlementPeriodEnum.values()) {
            if (settlementPeriodEnum.getCode().equals(code)) {
                return settlementPeriodEnum;
            }
        }
        throw new IllegalArgumentException("Invalid value for CommissionValuesEnum : " + code + " error ");
    }


    public static String getNameByDayNum(Integer value) {

        String result = "";
        for (SettlementPeriodEnum typeEnum : SettlementPeriodEnum.values()) {
            if (Objects.equals(value, typeEnum.dayNum)) {
                result = typeEnum.code;
            }
        }
        return result;
    }

}
