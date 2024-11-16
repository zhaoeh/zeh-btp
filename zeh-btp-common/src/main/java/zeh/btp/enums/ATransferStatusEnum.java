package zeh.btp.enums;

/**
 * 代理转账状态值 --无审核逻辑
 */
public enum ATransferStatusEnum {

    Pending(0, "Pending"),
    Success(1, "Success"),
    Failure(2, "Failure");

    private Integer code;
    private String name;

    ATransferStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Integer getCodeByName(String name) {
        for (ATransferStatusEnum status : values()) {
            if (status.getName().equals(name)) {
                return status.getCode();
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ATransferStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return null;
    }
}