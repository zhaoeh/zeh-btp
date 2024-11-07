package ft.btp.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @ClassName TaskNameEnum
 * @Description 任务名称枚举
 * @Author Amida
 * @Date 2023/5/20 10：00
 * @Version 1.0
 **/
public enum TaskNameEnum {

    CommissionMonthByAllHandler("commissionMonthByAllHandler", "commissionMonthByAllHandler", 1),

    CommissionMonthByGameHandler("commissionMonthByGameHandler", "commissionMonthByGameHandler", 1),
    CustomerPlayInfoMonthHandler("customerPlayInfoMonthHandler", "customerPlayInfoMonthHandler", 1),

    CustomerPlayInfoDayHandler("customerPlayInfoDayHandler", "customerPlayInfoDayHandler", 1),


    CustomerPlayInfoDayOnceHandler("customerPlayInfoDayOnceHandler", "customerPlayInfoDayOnceHandler", 0),

    CustomerPlayInfoMonthOnceHandler("customerPlayInfoMonthOnceHandler", "customerPlayInfoMonthOnceHandler", 0),

    OrgCodeAgentHandler("orgCodeAgentHandler", "orgCodeAgentHandler", 0),


    ;

    private String code;

    private String value;

    private Integer taskType;

    TaskNameEnum(String code, String value, Integer taskType) {
        this.code = code;
        this.value = value;
        this.taskType = taskType;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public Integer getTaskType() {
        return taskType;
    }

    /**
     * 根据枚举class类,枚举值获取枚举
     *
     * @author Austin
     * @data 2023-05-15
     **/
    public static TaskNameEnum getByCode(String code) {

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Invalid value for CommissionValuesEnum : " + code + "is not blank");
        }

        for (TaskNameEnum settlementPeriodEnum : TaskNameEnum.values()) {
            if (settlementPeriodEnum.getCode().equals(code)) {
                return settlementPeriodEnum;
            }
        }
        throw new IllegalArgumentException("Invalid value for CommissionValuesEnum : " + code + " error ");
    }


    public static String getNameByDayNum(Integer value) {

        String result = "";
        for (TaskNameEnum typeEnum : TaskNameEnum.values()) {
            if (Objects.equals(value, typeEnum.taskType)) {
                result = typeEnum.code;
            }
        }
        return result;
    }

}
