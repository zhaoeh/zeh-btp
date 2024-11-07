package jp.co.futech.module.insight.mapper.bh.anomaly;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.config.IndexConfig;
import jp.co.futech.module.insight.entity.bh.anomaly.BiDailyGetTrendDataEntity;
import jp.co.futech.module.insight.po.req.anomaly.AnomalyGetTrendDataReq;
import jp.co.futech.module.insight.po.res.anomaly.AnomalyGetTrendDataRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: BiNoteDotMapper
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Mapper
public interface BiDailyGetTrendDataMapper extends BaseMapperX<BiDailyGetTrendDataEntity> {

    /**
     * 根据条件查询树图
     *
     * @param req 请求参数
     * @return 树图列表
     */
    default List<BiDailyGetTrendDataEntity> selectTrendDataForList(AnomalyGetTrendDataReq req) {
        LambdaQueryWrapperX<BiDailyGetTrendDataEntity> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eqIfPresent(BiDailyGetTrendDataEntity::getMetric_trend, req.getMetric()).
                eqIfHasText(BiDailyGetTrendDataEntity::getIndex_trend, req.getIndex()).
                leIfHasText(BiDailyGetTrendDataEntity::getDate_trend, req.getEndDate()).
                geIfHasText(BiDailyGetTrendDataEntity::getDate_trend, req.getStartDate()).
                inIfPresent(BiDailyGetTrendDataEntity::getDimension_trend, req.getDimension()).
                inIfPresent(BiDailyGetTrendDataEntity::getSub_items_trend, req.getSubItems()).
                eqIfHasText(BiDailyGetTrendDataEntity::getSite_trend, req.getSite());
        return selectList(lambdaQueryWrapperX);
    }

    /**
     * 根据条件查询树图
     *
     * @param req 请求参数
     * @return 树图列表
     */
    default List<BiDailyGetTrendDataEntity> selectAnomalDataForList(AnomalyGetTrendDataReq req) {
        LambdaQueryWrapperX<BiDailyGetTrendDataEntity> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eqIfHasText(BiDailyGetTrendDataEntity::getMetric_anomal, req.getMetric()).
                inIfPresent(BiDailyGetTrendDataEntity::getDimension_trend, req.getDimension()).
                inIfPresent(BiDailyGetTrendDataEntity::getSub_items_trend, req.getSubItems()).
                eqIfHasText(BiDailyGetTrendDataEntity::getDate_anomal, req.getAnomalDt()).
                eqIfHasText(BiDailyGetTrendDataEntity::getSite_anomal, req.getSite());
        return selectList(lambdaQueryWrapperX);
    }

    List<AnomalyGetTrendDataRes.TrendData> selectTrendDataForListByXml(@Param("req") AnomalyGetTrendDataReq req);

    List<AnomalyGetTrendDataRes.TrendData> selectTrendDataForListByMapper(@Param("req") AnomalyGetTrendDataReq req, @Param("sqlName") IndexConfig.SqlNameBean bean);

    List<AnomalyGetTrendDataRes.AnomalData> selectAnomalDataForListByXml(@Param("req") AnomalyGetTrendDataReq req);

    List<AnomalyGetTrendDataRes.AnomalData> selectAnomalDataForListByMapper(@Param("req") AnomalyGetTrendDataReq req);

}
