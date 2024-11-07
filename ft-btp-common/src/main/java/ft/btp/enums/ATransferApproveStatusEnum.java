package ft.btp.enums;

/**
 * 代理转账 -- 有审核逻辑 审核状态
 */
public enum ATransferApproveStatusEnum {

    //0 process，-1 Pending，-2 Pending2，1 Approved，2 Rejected

    //-1 Pending 刚创建，等待一审
    Pending(-1, "Pending"),

    //0 Process 一审通过/二审通过
    Process(0, "Process"),

    //1 Approved 订单成功完成
    Approved(1, "Approved"),

    //-2 Pending2 一审失败，等待二审
    Pending2(-2, "Pending2"),

    //2 Rejected 订单失败
    Rejected(2, "Rejected");

    private Integer code;
    private String name;

    ATransferApproveStatusEnum(Integer code, String name) {
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
        for (ATransferApproveStatusEnum status : values()) {
            if (status.getName().equals(name)) {
                return status.getCode();
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ATransferApproveStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return null;
    }
}