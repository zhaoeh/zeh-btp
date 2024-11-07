package jp.co.futech.module.insight.service.risk;

import jp.co.futech.framework.common.exception.util.ServiceExceptionUtil;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.common.util.date.DateUtils;
import jp.co.futech.module.insight.config.RiskQueryConditionsConfig;
import jp.co.futech.module.insight.constant.InsightConstants;
import jp.co.futech.module.insight.convert.BarChartConvert;
import jp.co.futech.module.insight.entity.pg.risk.PgAccountRiskListEntity;
import jp.co.futech.module.insight.entity.pg.risk.PgAccountRiskStatsEntity;
import jp.co.futech.module.insight.enums.risk.ChooseTypeEnums;
import jp.co.futech.module.insight.mapper.pg.risk.PgAccountRiskListMapper;
import jp.co.futech.module.insight.mapper.pg.risk.PgAccountRiskStatMapper;
import jp.co.futech.module.insight.po.req.risk.AccountRiskBarChartQueryReq;
import jp.co.futech.module.insight.po.req.risk.AccountRiskBaseReq;
import jp.co.futech.module.insight.po.req.risk.AccountRiskListQueryReq;
import jp.co.futech.module.insight.po.req.risk.AccountRiskPieChartQueryReq;
import jp.co.futech.module.insight.po.res.risk.AccountRiskBarChartQueryRes;
import jp.co.futech.module.insight.po.res.risk.AccountRiskPieChart;
import jp.co.futech.module.insight.po.res.risk.AccountRiskPieChartQueryRes;
import jp.co.futech.module.insight.utils.StgUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static jp.co.futech.module.system.error.InsightErrorCodeConstants.DATE_PARAMS_WRONG;

/**
 * @description: 账号风控service impl
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Service
public class AccountRiskServiceImpl implements AccountRiskService {

    @Autowired
    private PgAccountRiskListMapper pgAccountRiskListMapper;

    @Autowired
    private PgAccountRiskStatMapper pgAccountRiskStatMapper;

    @Override
    public PageResult<PgAccountRiskListEntity> selectRiskListByPage(AccountRiskListQueryReq req) {
        return pgAccountRiskListMapper.selectPage(req.setBaseQuery(refactorReq(req.getBaseQuery(), null, null)));
    }

    @Override
    public List<AccountRiskBarChartQueryRes> selectBarChartData(AccountRiskBarChartQueryReq req) {
        AccountRiskBaseReq refactorReq = refactorReq(req, StgUtils::removeLast3, StgUtils::removeLast3);
        List<PgAccountRiskStatsEntity> riskStatEntities = new ArrayList<>();
        if(ChooseTypeEnums.DAY.getCode().equals(req.getTimeRange())){
            riskStatEntities = pgAccountRiskStatMapper.selectBarChartDayData((AccountRiskBarChartQueryReq) refactorReq);
        } else {
            riskStatEntities = pgAccountRiskStatMapper.selectBarChartMonthData((AccountRiskBarChartQueryReq) refactorReq);
        }
        return BarChartConvert.INSTANCE.convertList(riskStatEntities);
    }

    @Override
    public List<AccountRiskPieChart> selectPieChartData(AccountRiskPieChartQueryReq req) {
        List<AccountRiskPieChartQueryRes> res = new ArrayList<>();
        if(ChooseTypeEnums.DAY.getCode().equals(req.getTimeRange())){
            res =   pgAccountRiskStatMapper.selectPieChartDayData((AccountRiskPieChartQueryReq) refactorReq(req, StgUtils::removeLast3, StgUtils::removeLast3));
        } else {
            res =   pgAccountRiskStatMapper.selectPieChartMonthData((AccountRiskPieChartQueryReq) refactorReq(req, StgUtils::removeLast3, StgUtils::removeLast3));
        }
        Map<String, Integer> ruleUserSums = res.stream()
                .map(AccountRiskPieChartQueryRes::getRuleUserCnt)
                .flatMap(cntMap -> cntMap.entrySet().stream())
                .collect(Collectors.toMap(entry ->renameKey(entry.getKey()),entry -> Integer.parseInt(entry.getValue()),Integer::sum
                ));
        // 将结果转换为所需的结构
        List<AccountRiskPieChart> result = ruleUserSums.entrySet().stream()
                .map(entry -> new AccountRiskPieChart(entry.getKey(),getDisplayText(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
        return result;
    }

    private static String renameKey(String key) {
        String[] split = key.split("_");
        return  split[0].replace("r","R");
    }
    private static String getDisplayText(String key) {
        switch (key) {
            case InsightConstants.RULE1:
                return InsightConstants.RULE1_VALUE;
            case InsightConstants.RULE2:
                return InsightConstants.RULE2_VALUE;
            case InsightConstants.RULE3:
                return InsightConstants.RULE3_VALUE;
            case InsightConstants.RULE4:
                return InsightConstants.RULE4_VALUE;
            case InsightConstants.RULE5:
                return InsightConstants.RULE5_VALUE;
            default:
                return ""; // 默认值
        }
    }

    /**
     * 构建动态数组
     *
     * @param pieChart 入参实例
     * @return 动态数组
     */
//    private AccountRiskPieChart[] buildDynamicPies(AccountRiskPieChartQueryRes pieChart) {
//        return Objects.isNull(pieChart) ? new AccountRiskPieChart[0] : new AccountRiskPieChart[]{
//                AccountRiskPieChart.builder().name(InsightConstants.RULE1).value(pieChart.getRule1Users()).display(InsightConstants.RULE1_VALUE).build(),
//                AccountRiskPieChart.builder().name(InsightConstants.RULE2).value(pieChart.getRule2Users()).display(InsightConstants.RULE2_VALUE).build(),
//                AccountRiskPieChart.builder().name(InsightConstants.RULE3).value(pieChart.getRule3Users()).display(InsightConstants.RULE3_VALUE).build(),
//                AccountRiskPieChart.builder().name(InsightConstants.RULE4).value(pieChart.getRule4Users()).display(InsightConstants.RULE4_VALUE).build(),
//                AccountRiskPieChart.builder().name(InsightConstants.RULE5).value(pieChart.getRule5Users()).display(InsightConstants.RULE5_VALUE).build()
//        };
//    }

    /**
     * 重构req
     *
     * @param req 请求
     * @return 重构后的请求
     */
    private AccountRiskBaseReq refactorReq(AccountRiskBaseReq req, Function<String, String> startDateMapper, Function<String, String> endDateMapper) {
        req.setTimeRangeFormat(RiskQueryConditionsConfig.findTimeRangeMaps(req.getTimeRange()));
        if (StringUtils.isBlank(req.getEndDate())) {
            req.setEndDate(DateUtils.getCurrentDate());
        }
        ServiceExceptionUtil.isTrue(!DateUtils.isAfter(req.getStartDate(), req.getEndDate()), DATE_PARAMS_WRONG);
        ServiceExceptionUtil.isTrue(!DateUtils.isAfter(req.getEndDate(), DateUtils.getCurrentDate()), DATE_PARAMS_WRONG);
        Optional.ofNullable(req.getTimeRange()).ifPresent(t -> {
            if (ChooseTypeEnums.MONTH.getCode().equals(t)) {
                refactorStartDate(req, startDateMapper);
                refactorEndDate(req, endDateMapper);
            }
        });
        return req;
    }

    private void refactorStartDate(AccountRiskBaseReq req, Function<String, String> startDateMapper) {
        if (StringUtils.isBlank(req.getStartDate())) {
            return;
        }
        req.setStartDate(Objects.isNull(startDateMapper) ?
                DateUtils.dateToString(DateUtils.getFirstDayOfMonth(DateUtils.stringToDate(req.getStartDate()))) : startDateMapper.apply(req.getStartDate()));
    }

    private void refactorEndDate(AccountRiskBaseReq req, Function<String, String> endDateMapper) {
        if (StringUtils.isBlank(req.getEndDate())) {
            return;
        }
        if (Objects.isNull(endDateMapper)) {
            // 历史月份
            if (DateUtils.isBefore(req.getEndDate(), DateUtils.dateToString(DateUtils.getFirstDayOfMonth(DateUtils.stringToDate(DateUtils.getCurrentDate()))))) {
                req.setEndDate(DateUtils.dateToString(DateUtils.getLastDayOfMonth(DateUtils.stringToDate(req.getEndDate()))));
            } else {
                // 当前月份
                req.setEndDate(DateUtils.getCurrentDate());
            }
        } else {
            req.setEndDate(endDateMapper.apply(req.getEndDate()));
        }
    }

}
