package jp.co.futech.module.insight.mapper.bh.risk;

import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.common.util.object.PageUtils;
import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.config.RiskQueryConditionsConfig;
import jp.co.futech.module.insight.entity.bh.risk.AccountRiskListEntity;
import jp.co.futech.module.insight.po.req.risk.AccountRiskListQueryReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: AccountRiskStatMapper
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Mapper
public interface AccountRiskListMapper extends BaseMapperX<AccountRiskListEntity> {

    /**
     * 分页查询风控明细
     *
     * @param req 请求
     * @return 明细分页结果
     */
    default PageResult<AccountRiskListEntity> selectPage(AccountRiskListQueryReq req) {
        return selectPage(resettingReq(req), new LambdaQueryWrapperX<AccountRiskListEntity>().
                likeIfPresent(AccountRiskListEntity::getLoginName, req.getLoginName()).
                eqIfHasText(AccountRiskListEntity::getPrimaryRiskType, req.getPrimaryRiskTypeFormat()).
                eqIfHasText(AccountRiskListEntity::getSecondaryRiskType, req.getSecondaryRiskTypeFormat()).
                geIfHasText(AccountRiskListEntity::getDate, req.gStartDate()).
                leIfHasText(AccountRiskListEntity::getDate, req.gEndDate()));
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
                        List.of(PageUtils.buildSortingField(AccountRiskListEntity::getDate, AccountRiskListEntity::getDate),
                                PageUtils.buildSortingField(AccountRiskListEntity::getIp, AccountRiskListEntity::getIp),
                                PageUtils.buildSortingFieldOfTableField(AccountRiskListEntity::getDeviceId, AccountRiskListEntity::getDeviceId))
                );
        return req;
    }

}
