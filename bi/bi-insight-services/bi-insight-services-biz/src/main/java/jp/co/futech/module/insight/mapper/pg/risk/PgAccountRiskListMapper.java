package jp.co.futech.module.insight.mapper.pg.risk;

import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.common.util.object.PageUtils;
import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.config.RiskQueryConditionsConfig;
import jp.co.futech.module.insight.entity.bh.risk.AccountRiskListEntity;
import jp.co.futech.module.insight.entity.pg.risk.PgAccountRiskListEntity;
import jp.co.futech.module.insight.po.req.risk.AccountRiskListQueryReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: AccountRiskStatMapper
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Mapper
public interface PgAccountRiskListMapper extends BaseMapperX<PgAccountRiskListEntity> {

    /**
     * 分页查询风控明细
     *
     * @param req 请求
     * @return 明细分页结果
     */
    default PageResult<PgAccountRiskListEntity> selectPage(AccountRiskListQueryReq req) {
        return selectPage(resettingReq(req), new LambdaQueryWrapperX<PgAccountRiskListEntity>().
                likeIfPresent(PgAccountRiskListEntity::getLoginName, req.getLoginName()).
                eqIfHasText(PgAccountRiskListEntity::getPrimaryRiskType, req.getPrimaryRiskTypeFormat()).
                eqIfHasText(PgAccountRiskListEntity::getSecondaryRiskType, req.getSecondaryRiskTypeFormat()).
                eqIfHasText(PgAccountRiskListEntity::getRiskRevel, req.getRiskLevel()).
                geIfHasText(PgAccountRiskListEntity::getDate, req.gStartDate()).
                leIfHasText(PgAccountRiskListEntity::getDate, req.gEndDate()));
    }

    /**
     * 重设请求
     *
     * @param req 原始请求
     * @return 重设后的请求
     */
    default AccountRiskListQueryReq resettingReq(AccountRiskListQueryReq req) {
        req.setPrimaryRiskTypeFormat(RiskQueryConditionsConfig.findPrimaryRiskType(req.getPrimaryRiskType())).
                setSecondaryRiskTypeFormat(RiskQueryConditionsConfig.findSecondaryRiskType(req.getSecondaryRiskType())).
                setSortingFields(
                        List.of(PageUtils.buildSortingField(PgAccountRiskListEntity::getDate, PgAccountRiskListEntity::getDate),
                                PageUtils.buildSortingField(PgAccountRiskListEntity::getIp, PgAccountRiskListEntity::getIp),
                                PageUtils.buildSortingFieldOfTableField(PgAccountRiskListEntity::getGroupId, PgAccountRiskListEntity::getGroupId))
                );
        return req;
    }

}
