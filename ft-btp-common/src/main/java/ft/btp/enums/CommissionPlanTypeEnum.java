package ft.btp.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName CommissionPlanTypeEnum
 * @Description 佣金方案类型枚举
 * @Author TJSAustin
 * @Date 2023/5/20 10：00
 * @Version 1.0
 **/
public enum CommissionPlanTypeEnum {

    TURNOVER("TURNOVER", "投注额"),
    GGR("GGR", "GGR");

    private String code;

    private String value;

    CommissionPlanTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


    /**
     * 根据枚举class类,枚举值获取枚举
     *
     * @author Austin
     * @data 2023-05-15
     **/
    public static CommissionValuesEnum getByCode(String code) {

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Invalid value for CommissionValuesEnum : " + code + "is not blank");
        }

        for (CommissionValuesEnum commissionValuesEnum : CommissionValuesEnum.values()) {
            if (commissionValuesEnum.getCode().equals(code)) {
                return commissionValuesEnum;
            }
        }
        throw new IllegalArgumentException("Invalid value for CommissionValuesEnum : " + code + " error ");
    }
}
