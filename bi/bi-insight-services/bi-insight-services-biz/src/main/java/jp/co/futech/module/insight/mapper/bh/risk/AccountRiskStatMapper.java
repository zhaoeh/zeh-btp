package jp.co.futech.module.insight.mapper.bh.risk;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.entity.bh.risk.AccountRiskStatsEntity;
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
public interface AccountRiskStatMapper extends BaseMapperX<AccountRiskStatsEntity> {

    /**
     * 查询柱状图数据集合
     *
     * @param req 请求
     * @return 柱状图数据集
     */
    default List<AccountRiskStatsEntity> selectBarChartData(AccountRiskBarChartQueryReq req) {
        return selectList(new LambdaQueryWrapperX<AccountRiskStatsEntity>().
                geIfHasText(AccountRiskStatsEntity::getDate, req.getStartDate()).
                leIfHasText(AccountRiskStatsEntity::getDate, req.getEndDate()).
                eqIfHasText(AccountRiskStatsEntity::getTimeRange, req.getTimeRangeFormat()).
                orderByAsc(AccountRiskStatsEntity::getDate));
    }

    /**
     * 查询饼图数据集合
     *
     * @param req 请求
     * @return 饼图数据集
     */
    AccountRiskPieChartQueryRes selectPieChartData(@Param("req") AccountRiskPieChartQueryReq req);

}
