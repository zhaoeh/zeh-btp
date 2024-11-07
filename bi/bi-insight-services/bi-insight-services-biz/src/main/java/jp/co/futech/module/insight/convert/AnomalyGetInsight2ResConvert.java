package jp.co.futech.module.insight.convert;

import jp.co.futech.module.insight.po.res.anomaly.AnomalyGetInsight2Res;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @description: 转换器
 * @author: ErHu.Zhao
 * @create: 2024-08-01
 **/
@Mapper
public interface AnomalyGetInsight2ResConvert {
    AnomalyGetInsight2ResConvert INSTANCE = Mappers.getMapper(AnomalyGetInsight2ResConvert.class);

    @Mappings({
            @Mapping(source = "metric1", target = "metric"),
            @Mapping(source = "subDimensionPoints1", target = "subDimensionPoints"),
            @Mapping(source = "datePoints1", target = "datePoints"),
            @Mapping(source = "ggrPoints1", target = "ggrPoints"),
            @Mapping(source = "ggrTopPoints1", target = "ggrTopPoints"),
            @Mapping(source = "ggrBottomPoints1", target = "ggrBottomPoints")
    })
    AnomalyGetInsight2Res.Point convert(AnomalyGetInsight2Res bean);

    List<AnomalyGetInsight2Res.Point> converts(List<AnomalyGetInsight2Res> beans);

    @Mappings({
            @Mapping(source = "metricAnomalies2", target = "metricAnomalies"),
            @Mapping(source = "siteAnomalies2", target = "siteAnomalies"),
            @Mapping(source = "dateAnomalies2", target = "dateAnomalies"),
            @Mapping(source = "ggrAnomalies2", target = "ggrAnomalies"),
            @Mapping(source = "detetionTimeAnomalies2", target = "detetionTimeAnomalies"),
            @Mapping(source = "severityAnomalies2", target = "severityAnomalies"),
            @Mapping(source = "rateAnomalies2", target = "rateAnomalies"),
            @Mapping(source = "anomalyDescribeAnomalies2", target = "anomalyDescribeAnomalies"),
            @Mapping(source = "causeAnalysisAnomalies2", target = "causeAnalysisAnomalies"),
            @Mapping(source = "detailAnomalies2", target = "detailAnomalies"),
            @Mapping(source = "note2", target = "note"),
            @Mapping(source = "remark2", target = "remark")
    })
    AnomalyGetInsight2Res.Anomalie convert2(AnomalyGetInsight2Res bean);

    List<AnomalyGetInsight2Res.Anomalie> converts2(List<AnomalyGetInsight2Res> beans);
}
