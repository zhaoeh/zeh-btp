package jp.co.futech.module.insight.mapper.bh.anomaly;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.entity.bh.anomaly.BiDailyGetTreeDataEntity;
import jp.co.futech.module.insight.po.req.anomaly.AnomalyGetTreeDataReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @description: BiNoteDotMapper
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Mapper
public interface BiDailyGetTreeDataMapper extends BaseMapperX<BiDailyGetTreeDataEntity> {

    /**
     * 根据条件查询树图
     *
     * @param req 请求参数
     * @return 树图列表
     */
    default List<BiDailyGetTreeDataEntity> selectTreeDataForList(AnomalyGetTreeDataReq req) {
        final LambdaQueryWrapperX<BiDailyGetTreeDataEntity> lambda = new LambdaQueryWrapperX<BiDailyGetTreeDataEntity>().eqIfPresent(BiDailyGetTreeDataEntity::getMetric, req.getMetric()).
                eqIfPresent(BiDailyGetTreeDataEntity::getSite, req.getSite());
        List<String> date = req.getDate();
        return Optional.ofNullable(date).map(i -> {
            if (i.size() == 1) {
                return selectList(lambda.eqIfHasText(BiDailyGetTreeDataEntity::getDt, i.get(0)));
            } else if (date.size() > 1) {
                return selectList(lambda.inIfPresent(BiDailyGetTreeDataEntity::getDt, date));
            } else {
                return selectList(lambda);
            }
        }).orElseGet(() -> selectList(lambda));

    }
}
