package jp.co.futech.module.insight.mapper.bh.anomaly;

import jp.co.futech.module.insight.po.req.anomaly.AnomalyGetAnomalyReq;
import jp.co.futech.module.insight.po.req.anomaly.AnomalySaveNoteRemarkReq;
import jp.co.futech.module.insight.po.req.anomaly.AnomalySelectDimensionsReq;
import jp.co.futech.module.insight.po.res.anomaly.AnomalyGetAnomalyRes;
import jp.co.futech.module.insight.po.res.anomaly.AnomalyGetDimensionRes;
import jp.co.futech.module.insight.po.res.anomaly.AnomalySelectDimensionsRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: AnomalyCommonMapper
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Mapper
public interface AnomalyCommonMapper {

    List<AnomalyGetDimensionRes> getDimension();

    List<AnomalyGetAnomalyRes.Anomaly> getAnomaly(@Param("req") AnomalyGetAnomalyReq anomalyGetAnomalyReq);

    List<AnomalyGetAnomalyRes.Point> getAnomalyOfPoints(@Param("req") AnomalyGetAnomalyReq anomalyGetAnomalyReq);

    List<AnomalySelectDimensionsRes> selectDimensions(@Param("req") AnomalySelectDimensionsReq anomalySelectDimensionsReq);

    int insertNotes(@Param("req") AnomalySaveNoteRemarkReq req);

}
