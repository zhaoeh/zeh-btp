package ft.btp.enums;

import java.math.BigDecimal;

/**
 * @ClassName FundTypeEnum
 * @Description 账变类型枚举
 * @Author TJSAlex
 * @Date 2023/5/19 16:49
 * @Version 1.0
 **/
public enum FundTypeEnum {

    /**
     * 0:Deposit 1:Withdrawal 2:Transfer 3:Received 4:Paid Commission 5:Received Commission 6:Append Commission
     */
    Deposit(0, "Deposit", "存款", IncomeEnum.In.code),
    Withdrawal(1, "Withdrawal", "取款", IncomeEnum.OUT.code),
    Transfer(2, "Transfer", "转账", IncomeEnum.OUT.code),
    Received(3, "Received", "接收到转账", IncomeEnum.In.code),
    PaidCommission(4, "Paid Commission", "支付佣金", IncomeEnum.OUT.code),
    ReceivedCommission(5, "Received Commission", "接收佣金", IncomeEnum.In.code),
    AppendCommission(6, "Append Commission", "补发佣金", IncomeEnum.OUT.code);

    private final Integer code;
    private final String name;
    private final String desc;
    private final Integer isIncome;

    FundTypeEnum(Integer code, String name, String desc, Integer isIncome) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.isIncome = isIncome;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getIsIncome() {
        return isIncome;
    }

    public static String getNameByCode(Integer code) {
        for (FundTypeEnum value : values()) {
            if(value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return null;
    }

    public static Integer getCodeByName(String name) {
        for (FundTypeEnum value : values()) {
            if (value.getName().equals(name)) {
                return value.getCode();
            }
        }
        return null;
    }

    //设置金额正负号
    public static String setAmount(String amount,Integer code){
//        if(code.equals(Deposit.getCode())||code.equals(Received.getCode())||code.equals(ReceivedCommission.getCode())){
//            return "+"+amount;
//        }
        return amount;
    }
}
