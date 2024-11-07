package jp.co.futech.module.insight.convert;

import jp.co.futech.module.insight.po.res.anomaly.AnomalyGetInsight1Res;
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
public interface AnomalyGetInsight1ResConvert {
    AnomalyGetInsight1ResConvert INSTANCE = Mappers.getMapper(AnomalyGetInsight1ResConvert.class);

    @Mappings({
            @Mapping(source = "metric1", target = "metric"),
            @Mapping(source = "date1", target = "date"),
            @Mapping(source = "ggr1", target = "ggr"),
            @Mapping(source = "ggrTop1", target = "ggrTop"),
            @Mapping(source = "ggrBottom1", target = "ggrBottom")
    })
    AnomalyGetInsight1Res.Point convert(AnomalyGetInsight1Res bean);

    List<AnomalyGetInsight1Res.Point> converts(List<AnomalyGetInsight1Res> beans);

    @Mappings({
            @Mapping(source = "metric2", target = "metric"),
            @Mapping(source = "date2", target = "date"),
            @Mapping(source = "ggr2", target = "ggr"),
            @Mapping(source = "detetionTime2", target = "detetionTime"),
            @Mapping(source = "severity2", target = "severity"),
            @Mapping(source = "rate2", target = "rate"),
            @Mapping(source = "anomalyDescribe2", target = "anomalyDescribe"),
            @Mapping(source = "causeAnalysis2", target = "causeAnalysis"),
            @Mapping(source = "detail2", target = "detail"),
            @Mapping(source = "note2", target = "note"),
            @Mapping(source = "remark2", target = "remark")
    })
    AnomalyGetInsight1Res.Anomalie convert2(AnomalyGetInsight1Res bean);

    List<AnomalyGetInsight1Res.Anomalie> converts2(List<AnomalyGetInsight1Res> beans);
}
