package zeh.btp.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName AgentTypeEnum
 * @Description 代理类型枚举
 * @Author TJSAustin
 * @Date 2023/5/24 10：00
 * @Version 1.0
 **/
public enum AgentTypeEnum {

    GENERAL_LINE(0, "GENERAL_LINE", "普通代理"),
    PROFESSIONAL_LINE(1, "PROFESSIONAL_LINE", "专业代理"),
    DIRECT_PLAYER(3, "DIRECT_PLAYER", "直属玩家");;

    private Integer index;

    private String code;

    private String value;

    AgentTypeEnum(Integer index, String code, String value) {
        this.index = index;
        this.code = code;
        this.value = value;
    }

    public Integer getIndex() {
        return index;
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
     * @data 2023-05-24
     **/
    public static AgentTypeEnum getByCode(String code) {

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Invalid value for AgentTypeEnum : " + code + "is not blank");
        }

        for (AgentTypeEnum commissionValuesEnum : AgentTypeEnum.values()) {
            if (commissionValuesEnum.getCode().equals(code)) {
                return commissionValuesEnum;
            }
        }
        throw new IllegalArgumentException("Invalid value for AgentTypeEnum : " + code + " error ");
    }

    /**
     * 根据枚举class类,枚举值获取枚举
     *
     * @author Austin
     * @data 2023-05-24
     **/
    public static AgentTypeEnum getByIndex(Integer index) {

        if (null == index) {
            throw new IllegalArgumentException("Invalid value for AgentTypeEnum : index is not blank");
        }

        for (AgentTypeEnum agentTypeEnum : AgentTypeEnum.values()) {
            if (agentTypeEnum.getIndex().equals(index)) {
                return agentTypeEnum;
            }
        }
        throw new IllegalArgumentException("Invalid value for AgentTypeEnum : index is error");
    }

}
