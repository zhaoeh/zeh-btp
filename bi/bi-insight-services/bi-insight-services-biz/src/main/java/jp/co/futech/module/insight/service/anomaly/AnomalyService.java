package jp.co.futech.module.insight.service.anomaly;

import cn.hutool.json.JSONObject;
import jp.co.futech.module.insight.po.req.anomaly.*;
import jp.co.futech.module.insight.po.res.anomaly.*;

import java.util.List;

/**
 * @description: Anomaly service
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
public interface AnomalyService {

    AnomalyGetInsight1Res getInsight1(AnomalyGetInsight2Req anomalyGetInsight2Req);

    AnomalyGetInsight2Res getInsight2(AnomalyGetInsight2Req anomalyGetInsight2Req);

    List<AnomalyGetDimensionRes> getDimensions();

    AnomalyGetAnomalyRes getAnomaly(AnomalyGetAnomalyReq anomalyGetAnomalyReq);

    List<AnomalySelectDimensionsRes> selectDimensions(AnomalySelectDimensionsReq anomalySelectDimensionsReq);

    boolean saveNotes(List<AnomalySaveNoteReq> saveNoteReqs);

    boolean saveNoteRemark(List<AnomalySaveNoteRemarkReq> saveNoteReqs);

    JSONObject getTreeData(AnomalyGetTreeDataReq treeDataReq);

    AnomalyGetTrendDataRes getTrendData(AnomalyGetTrendDataReq trendDataReq);

}
