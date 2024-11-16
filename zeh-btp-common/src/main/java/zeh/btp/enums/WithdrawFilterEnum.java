package zeh.btp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: sanji
 * @desc: TODO
 * @date: 2024/6/26 11:14
 */
@AllArgsConstructor
@Getter
public enum WithdrawFilterEnum {
    WITHDRAW_RISK_CLOSE("-1", "withdraw risk close", "取款风控开关关闭"),
    THEN_90_DAY("-3", "The time interval between creation and withdrawal is greater than 90 days", "距离上一次取款超过90天"),

    DIFFERENCE("1", "The current access difference is: %spiso ; > threshold %s piso", "存取差超过阈值"),
    PROFIT("2", "The current profitability value is: %s%% ; >threshold %s%%", "盈利率超过阈值"),
    BET_RATE("3", "The current betting ratio is:%s%% ; >threshold %s%%", "投注比率超过阈值"),
    TODAY_LIMIT("4", "The daily withdrawal amount is:%s piso ; >threshold %s piso", "超过日累计取款额阈值"),
    NO_BET("5", "No bet", "没有投注"),
    CONSECUTIVE_PASSES_AND_AMOUNT("6", "The system makes : %s; withdrawals in a row, with a total amount of %s piso", "连续通过次数及总金额超过阈值"),
    PROFIT_DIFFERENCE("7", "The current profit-difference value is:%s; >winAmount %s", "盈利额差超过阈值"),
    MAX_CONSECUTIVE_MANUAL("8", "Continuous pass >= %s must be transferred to manual", "连续通过次数超过阈值"),
    FIRST_WITHDRAWAL("9", "first withdrawal max Amount value:%s > %s", "首次取款金额超过阈值"),
    HIT_LABEL("10", "Risk control label interception：%s", "命中标签"),
    AUTO_APPROVE_CLOSE("11", "withdraw auto approve is close", "自动审批开关关闭"),
    AUTO_APPROVE_FAIL("12", "withdraw amount:%s > auto approve max amount %s", "自动审批单次金额超过阈值"),
    WD_MANUAL_MIN_NUM("13", "Same-day withdrawal > %s, single transaction amount >= %s", "取款次数及单次金额超过阈值"),
    NEXT_ENTER_MANUALLY("-4", "The circuit breaker passed, and next time it will be done manually ", "熔断流程被触发，下一次订单将直接进入手动审批"),
    CURRENT_ENTER_MANUALLY("14", "The last withdrawal circuit breaker passed automatically, and this time requires manual approval", "上一次取款熔断流程被触发，当前订单进入手动审批"),
    WITHDRAWAL_GREATER_DEPOSIT_RATIO_FAIL("15", "The withdrawal amount must be less than or equal to %s times the total deposit amount during the period", "提现金额必须小于等于该期间总充值金额的xx倍"),
    ;

    private String type;
    private String filterMsg;
    private String remark;
}
