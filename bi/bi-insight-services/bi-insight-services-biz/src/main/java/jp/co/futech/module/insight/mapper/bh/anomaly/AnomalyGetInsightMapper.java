package jp.co.futech.module.insight.mapper.bh.anomaly;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.module.insight.entity.bh.anomaly.BiAnomalyGetInsightEntity;
import jp.co.futech.module.insight.po.req.anomaly.AnomalyGetInsight2Req;
import jp.co.futech.module.insight.po.res.anomaly.AnomalyGetInsight1Res;
import jp.co.futech.module.insight.po.res.anomaly.AnomalyGetInsight2Res;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: AnomalyMapper
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Mapper
public interface AnomalyGetInsightMapper extends BaseMapperX<BiAnomalyGetInsightEntity> {

    List<AnomalyGetInsight1Res> getInsights1(@Param("req") AnomalyGetInsight2Req req);

    List<AnomalyGetInsight1Res.Point> getInsight1OfPoints(@Param("req") AnomalyGetInsight2Req req);

    List<AnomalyGetInsight1Res.Anomalie> getInsight1OfAnomalies(@Param("req") AnomalyGetInsight2Req req);

    List<AnomalyGetInsight2Res> getInsight2(@Param("req") AnomalyGetInsight2Req req);

    List<AnomalyGetInsight2Res.Point> getInsight2OfPoints(@Param("req") AnomalyGetInsight2Req req);

    List<AnomalyGetInsight2Res.Anomalie> getInsight2OfAnomalies(@Param("req") AnomalyGetInsight2Req req);

}
