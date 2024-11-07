package ft.btp.enums;

import ft.btp.constants.EnumValidContents;

/**
 * @description: 校验注解对应的枚举容器
 * @author: ErHu.Zhao
 * @create: 2023-12-30
 **/
public enum CustomizedValidationContents {
    // 业务枚举
    site_id_values(EnumValidContents.values(new Integer[]{1, 2, 3}).prefix(true).message("启用标识").message("[siteId]:%s:Bingoplus  %s:Arenaplus  %s:Gameplus")),
    sub_commission_plan_type_values(EnumValidContents.values(new String[]{"TURNOVER", "GGR"}).prefix(false).message("佣金计划类型只能是%&")),
    commission_plan_type_values(EnumValidContents.values(new String[]{"ALL", "TURNOVER", "GGR"}).prefix(false).message("佣金计划类型只能是%&")),
    sub_settlement_period_values(EnumValidContents.values(new String[]{"MONTH", "ONE-THIRD_MONTH", "WEEK", "DAY"}).prefix(true).message("结算周期:%&")),
    settlement_period_values(EnumValidContents.values(new String[]{"ALL", "MONTH", "ONE-THIRD_MONTH", "WEEK", "DAY"}).prefix(true).message("结算周期:%&")),
    product_site_id_values(EnumValidContents.values(new String[]{"ALL", "1", "2", "3"}).prefix(true).message("代理类型").message("[product site id]:%&({1}:Bingoplus  {2}:Arenaplus  {3}:Gameplus)")),
    sub_agent_type_values(EnumValidContents.values(new String[]{"0", "1"}).prefix(true).message("代理类型[AGENT_TYPE]:%& (0:普通,1:专业代理) ")),
    agent_type_values(EnumValidContents.values(new String[]{"ALL", "0", "1"}).prefix(true).message("代理类型[AGENT_TYPE]:%& (0:普通,1:专业代理) ")),
    sub_is_enable_values(EnumValidContents.values(new String[]{"0", "1"}).prefix(true).message("启用标识[FLAG]:%&(0禁用，1启用)")),
    is_enable_values(EnumValidContents.values(new String[]{"ALL", "0", "1"}).prefix(true).message("启用标识[FLAG]:%&(0禁用，1启用)")),
    developable_level_values(EnumValidContents.values(new Integer[]{1, 2, 3, 4, 5}).prefix(true).message("可发展最大下级代理层数[DEVELOPABLE_LEVEL]:%&")),
    developable_level_values2(EnumValidContents.values(new Integer[]{0, 1, 2, 3, 4}).prefix(true).message("可发展最大下级代理层数[DEVELOPABLE_LEVEL]:%&")),
    settlement_conditions_values(EnumValidContents.values(new Integer[]{0, 1}).prefix(true).message("结算条件[settlementConditions]:%&")),
    commission_values_values(EnumValidContents.values(new String[]{"ALL_GAME_TYPES", "BY_GAME_TYPE"}).prefix(true).message("结算类型[commissionValues]:%&")),
    team_summary_period(EnumValidContents.values(new String[]{"current", "last", "last_two"}).prefix(true).message("chosenPeriod:%&")),
    team_summary_type(EnumValidContents.values(new String[]{"Month", "Week", "Day"}).prefix(true).message("chosenType:%&"));


    private EnumValidContents contents;

    CustomizedValidationContents(EnumValidContents enumValidContents) {
        this.contents = enumValidContents;
    }

    public Object[] getAcceptValues() {
        return this.contents.getAcceptValues();
    }

    public String getMessage() {
        return this.contents.getMessage();
    }
}
