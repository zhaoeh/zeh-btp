package zeh.btp.enums;

import lombok.Getter;

@Getter
public enum WithdrawalRequestFlagEnum {
    PENDING(0),
    /**
     * 门店审核通过
     */
    PROCESSING(1),
    /**
     * 成功
     */
    SUCCESS(2),
    /**
     * office 取消
     */
    CANCEL_BY_OFFICE(-2),
    /**
     * 系统错误
     */
    SYSTEM_FAILED(-4);

    private final int val;

    WithdrawalRequestFlagEnum(int val) {
        this.val = val;
    }

    public boolean is(int val) {
        return this.val == val;
    }
}
