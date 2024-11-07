package jp.co.futech.module.insight.mapper.bh.risk;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.module.insight.entity.bh.risk.AccountRisk2ModelEntity;
import jp.co.futech.module.insight.po.req.risk.AccountRisk2ModelNewReq;
import jp.co.futech.module.insight.po.req.risk.AccountRisk2ModelReq;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelNewRes;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: AccountRiskStatMapper
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Mapper
public interface AccountRisk2ModelMapper extends BaseMapperX<AccountRisk2ModelEntity> {

    List<AccountRisk2ModelRes.UserAction> selectRisk2(@Param("req") AccountRisk2ModelReq params);

    IPage<AccountRisk2ModelRes.UserAction> selectRisk2(Page page, @Param("req") AccountRisk2ModelReq params);

    AccountRisk2ModelNewRes selectRisk2Features(@Param("req") AccountRisk2ModelNewReq params);

    List<AccountRisk2ModelNewRes.UserAction> selectRisk2Actions(@Param("req") AccountRisk2ModelNewReq params);

    IPage<AccountRisk2ModelNewRes.UserAction> selectRisk2Actions(Page page, @Param("req") AccountRisk2ModelNewReq params);

}
