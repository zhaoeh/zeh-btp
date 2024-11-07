package jp.co.futech.module.insight.service.anomaly;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.PostConstruct;
import jp.co.futech.framework.common.exception.util.ServiceExceptionUtil;
import jp.co.futech.framework.common.util.collection.CollectionUtils;
import jp.co.futech.framework.common.util.date.DateUtils;
import jp.co.futech.framework.common.util.json.JsonUtils;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.config.IndexConfig;
import jp.co.futech.module.insight.convert.AnomalyGetInsight1ResConvert;
import jp.co.futech.module.insight.convert.AnomalyGetInsight2ResConvert;
import jp.co.futech.module.insight.entity.bh.anomaly.BiAnomalDotAllEntity;
import jp.co.futech.module.insight.entity.bh.anomaly.BiDailyGetTreeDataEntity;
import jp.co.futech.module.insight.entity.bh.anomaly.BiNoteDotEntity;
import jp.co.futech.module.insight.mapper.bh.anomaly.*;
import jp.co.futech.module.insight.po.req.anomaly.*;
import jp.co.futech.module.insight.po.res.anomaly.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static jp.co.futech.module.system.error.InsightErrorCodeConstants.SQL_NAME_BEAN_NOT_BE_NULL;

/**
 * @description: Anomaly service impl
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@EnableConfigurationProperties(IndexConfig.class)
@Service
public class AnomalyServiceImpl implements AnomalyService {

    @Autowired
    private IndexConfig indexConfig;

    private Map<String, IndexConfig.SqlNameBean> indexMap;

    @Autowired
    private AnomalyGetInsightMapper anomalyGetInsightMapper;

    @Autowired
    private BiDailyGetTreeDataMapper dailyGetTreeDataMapper;

    @Autowired
    private BiNoteDotMapper biNoteDotMapper;

    @Autowired
    private BiDailyGetTrendDataMapper dailyGetTrendDataMapper;

    @Autowired
    private AnomalyCommonMapper anomalyCommonMapper;

    @Autowired
    private AnomalySaveNoteMapper anomalySaveNoteMapper;

    @PostConstruct
    public void init() {
        indexMap = indexConfig.getIndexMap();
        Assert.notEmpty(indexMap, "indexMap cannot be empty");
    }

    @Override
    public AnomalyGetInsight1Res getInsight1(AnomalyGetInsight2Req anomalyGetInsight2Req) {
        List<AnomalyGetInsight1Res> insights1 = anomalyGetInsightMapper.getInsights1(anomalyGetInsight2Req);
        List<AnomalyGetInsight1Res.Point> points = AnomalyGetInsight1ResConvert.INSTANCE.
                converts(CollectionUtils.filterList(insights1, AnomalyGetInsight1Res::hasText1));
        List<AnomalyGetInsight1Res.Anomalie> anomalies = AnomalyGetInsight1ResConvert.INSTANCE.
                converts2(CollectionUtils.filterList(insights1, AnomalyGetInsight1Res::hasText2));
        return AnomalyGetInsight1Res.builder().points(points).anomalies(anomalies).build();
    }

    @Override
    public AnomalyGetInsight2Res getInsight2(AnomalyGetInsight2Req anomalyGetInsight2Req) {
        List<AnomalyGetInsight2Res> insights2 = anomalyGetInsightMapper.getInsight2(anomalyGetInsight2Req);
        List<AnomalyGetInsight2Res.Point> points = AnomalyGetInsight2ResConvert.INSTANCE.
                converts(CollectionUtils.filterList(insights2, AnomalyGetInsight2Res::hasText1));
        List<AnomalyGetInsight2Res.Anomalie> anomalies = AnomalyGetInsight2ResConvert.INSTANCE.
                converts2(CollectionUtils.filterList(insights2, AnomalyGetInsight2Res::hasText2));
        return AnomalyGetInsight2Res.builder().points(points).anomalies(anomalies).build();
    }

    @Override
    public List<AnomalyGetDimensionRes> getDimensions() {
        List<AnomalyGetDimensionRes> res = anomalyCommonMapper.getDimension();
        if (CollUtil.isNotEmpty(res)) {
            List<AnomalyGetDimensionRes> result = new ArrayList<>();
            res.stream().collect(Collectors.groupingBy(AnomalyGetDimensionRes::getDimension,
                            Collectors.mapping(AnomalyGetDimensionRes::getSubItemStr, Collectors.toList()))).
                    forEach((k, v) -> result.add(AnomalyGetDimensionRes.builder().dimension(k).subItems(v).build()));
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public AnomalyGetAnomalyRes getAnomaly(AnomalyGetAnomalyReq anomalyGetAnomalyReq) {
        final AnomalyGetAnomalyRes.AnomalyGetAnomalyResBuilder resBuilder = AnomalyGetAnomalyRes.builder();
        List<AnomalyGetAnomalyRes.Anomaly> anomalies = anomalyCommonMapper.getAnomaly(anomalyGetAnomalyReq);
        if (CollUtil.isNotEmpty(anomalies)) {
            return anomalies.stream().findFirst().map(ano -> {
                List<AnomalyGetAnomalyRes.Point> points = anomalyCommonMapper.
                        getAnomalyOfPoints(anomalyGetAnomalyReq.setDimension(ano.getDimension()).setSubDimension(ano.getSubDimension()));
                return resBuilder.dimension(ano.getDimension()).subDimension(ano.getSubDimension()).anomaly(ano).points(points);
            }).orElse(resBuilder).build();
        }
        return resBuilder.build();
    }

    @Override
    public List<AnomalySelectDimensionsRes> selectDimensions(AnomalySelectDimensionsReq anomalySelectDimensionsReq) {
        return anomalyCommonMapper.selectDimensions(anomalySelectDimensionsReq);
    }

    @Override
    public boolean saveNotes(List<AnomalySaveNoteReq> saveNoteReqs) {
        if (CollectionUtils.isAnyEmpty(saveNoteReqs)) {
            return false;
        }
        List<BiNoteDotEntity> inserts = saveNoteReqs.stream().map(req ->
                        BiNoteDotEntity.builder().metric(req.getMetric()).site(req.getSite()).
                                user_name(req.getUserName()).dt(req.getDate()).note(req.getNote()).
                                remark(req.getRemark()).create_time(DateUtils.getCurrentDateTime()).build()).
                collect(Collectors.toList());
        return biNoteDotMapper.insertBatch(inserts);
    }

    @Override
    public boolean saveNoteRemark(List<AnomalySaveNoteRemarkReq> saveNoteReqs) {
        if (CollectionUtils.isAnyEmpty(saveNoteReqs)) {
            return false;
        }
        saveNoteReqs.stream().forEach(req -> {
            List<BiAnomalDotAllEntity> exits = anomalySaveNoteMapper.selectList(new LambdaQueryWrapperX<BiAnomalDotAllEntity>().
                    eqIfHasText(BiAnomalDotAllEntity::getMetric, req.getMetric()).
                    eqIfHasText(BiAnomalDotAllEntity::getDt, req.getDate()));
            if (CollUtil.isNotEmpty(exits)) {
                exits.stream().findFirst().map(e -> e.setNote(req.getNote()).setRemark(req.getRemark()).
                                setCreateTime(DateUtils.getCurrentDateTime()).setUserName(req.getUserName())).
                        ifPresent(anomalySaveNoteMapper::insert);
            }
        });
        return true;
    }

    @Override
    public JSONObject getTreeData(AnomalyGetTreeDataReq treeDataReq) {
        List<BiDailyGetTreeDataEntity> treeDataEntities = dailyGetTreeDataMapper.selectTreeDataForList(treeDataReq);
        return Optional.ofNullable(treeDataEntities).orElse(Collections.emptyList()).stream().findFirst().map(BiDailyGetTreeDataEntity::getTreeData).
                map(str -> (JSONObject) JSONUtil.parse(str)).orElse(new JSONObject());
    }

    @Override
    public AnomalyGetTrendDataRes getTrendData(AnomalyGetTrendDataReq trendDataReq) {
        List<AnomalyGetTrendDataRes.TrendData> trendData = dailyGetTrendDataMapper.
                selectTrendDataForListByMapper(trendDataReq.refactor(), obtainSqlNameBean(trendDataReq.getIndex()));
        List<AnomalyGetTrendDataRes.AnomalData> anomalData = dailyGetTrendDataMapper.selectAnomalDataForListByMapper(trendDataReq);
        return AnomalyGetTrendDataRes.builder().trendDatas(trendData).anomalDatas(refactorAnomalData(anomalData)).build();
    }

    private IndexConfig.SqlNameBean obtainSqlNameBean(String index) {
        IndexConfig.SqlNameBean sqlMap = indexMap.get(index);
        ServiceExceptionUtil.notNull(sqlMap, SQL_NAME_BEAN_NOT_BE_NULL);
        return sqlMap;
    }

    private List<AnomalyGetTrendDataRes.AnomalData> refactorAnomalData(List<AnomalyGetTrendDataRes.AnomalData> anomalData) {
        if (CollUtil.isEmpty(anomalData)) {
            return anomalData;
        }
        return anomalData.stream().map(t -> {
            t.setDimensionRateAnomal(JsonUtils.findDeepValue(t.getAnomalyInfo(), "dirll_data", "网站总ggr环比"));
            return t;
        }).collect(Collectors.toList());
    }

}
