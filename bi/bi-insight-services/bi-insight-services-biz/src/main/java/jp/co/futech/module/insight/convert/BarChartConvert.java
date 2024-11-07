package jp.co.futech.module.insight.convert;

import jp.co.futech.module.insight.entity.bh.risk.AccountRiskStatsEntity;
import jp.co.futech.module.insight.entity.pg.risk.PgAccountRiskStatsEntity;
import jp.co.futech.module.insight.po.res.risk.AccountRiskBarChartQueryRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BarChartConvert {

    BarChartConvert INSTANCE = Mappers.getMapper(BarChartConvert.class);

    List<AccountRiskBarChartQueryRes> convertList(List<PgAccountRiskStatsEntity> list);

    AccountRiskBarChartQueryRes convert(PgAccountRiskStatsEntity bean);

}
