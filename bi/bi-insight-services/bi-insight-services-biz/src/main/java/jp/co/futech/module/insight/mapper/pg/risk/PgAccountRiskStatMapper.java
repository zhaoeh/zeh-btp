package jp.co.futech.module.insight.mapper.pg.risk;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.entity.pg.risk.PgAccountRiskStatsEntity;
import jp.co.futech.module.insight.po.req.risk.AccountRiskBarChartQueryReq;
import jp.co.futech.module.insight.po.req.risk.AccountRiskPieChartQueryReq;
import jp.co.futech.module.insight.po.res.risk.AccountRiskPieChartQueryRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: AccountRiskStatMapper
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Mapper
public interface PgAccountRiskStatMapper extends BaseMapperX<PgAccountRiskStatsEntity> {

    /**
     * 查询柱状图日数据集合
     *
     * @param req 请求
     * @return 柱状图数据集
     */
    default List<PgAccountRiskStatsEntity> selectBarChartDayData(AccountRiskBarChartQueryReq req) {
        return selectList(new LambdaQueryWrapperX<PgAccountRiskStatsEntity>().
                geIfHasText(PgAccountRiskStatsEntity::getDate, req.getStartDate()).
                leIfHasText(PgAccountRiskStatsEntity::getDate, req.getEndDate()).
                eqIfHasText(PgAccountRiskStatsEntity::getTimeRange, req.getTimeRangeFormat()).
                orderByAsc(PgAccountRiskStatsEntity::getDate));
    }
    /**
     * 查询柱状图和饼图月数据集合
     *
     * @param req 请求
     * @return 柱状图数据集
     */
    List<PgAccountRiskStatsEntity> selectBarChartMonthData(@Param("req") AccountRiskBarChartQueryReq req);
    /**
     * 查询饼图数据集合
     *
     * @param req 请求
     * @return 饼图数据集
     */
    List<AccountRiskPieChartQueryRes> selectPieChartDayData(@Param("req") AccountRiskPieChartQueryReq req);
    List<AccountRiskPieChartQueryRes> selectPieChartMonthData(@Param("req") AccountRiskPieChartQueryReq req);
}
