package zeh.btp.enums;

public enum CommissionRecordStatusEnum {

    //一级代理佣金记录状态
    FIRST_PENDING("FIRST_PENDING", 0),
    FIRST_REJECTED("FIRST_REJECTED", 1),
    SECOND_PENDING("SECOND_PENDING", 2),
    SECOND_REJECTED("SECOND_REJECTED", 3),
    SECOND_AGREED("SECOND_AGREED", 4),

    //下级代理佣金记录状态
    PAID("AGREED", 5), //已支付
    UNPAID("PENDING", 8), //未支付

    //佣金审核
    AGREED("AGREE", 6),

    REJECTED("REJECTED", 7);

    private String des;

    private Integer value;

    CommissionRecordStatusEnum(String des, Integer value) {

        this.des = des;
        this.value = value;

    }

    public static String getNameByValue(Integer value) {
        switch (value) {
            case 0:
                return FIRST_PENDING.getDes();
            case 1:
                return FIRST_REJECTED.getDes();
            case 2:
                return SECOND_PENDING.getDes();
            case 3:
                return SECOND_REJECTED.getDes();
            case 4:
                return SECOND_AGREED.getDes();
            case 5:
                return PAID.getDes();
            case 6:
                return AGREED.getDes();
            case 7:
                return REJECTED.getDes();
            case 8:
                return UNPAID.getDes();
        }
        return "";
    }

    public String getDes() {
        return des;
    }

    public Integer getValue() {
        return value;
    }
}
