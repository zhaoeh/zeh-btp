package jp.co.futech.module.insight.service.risk;

import jp.co.futech.framework.common.pojo.PageObjectResult;
import jp.co.futech.module.insight.po.req.risk.AccountRisk2ModelNewReq;
import jp.co.futech.module.insight.po.req.risk.AccountRisk2ModelReq;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelNewShowRes;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelRes;

/**
 * @description: 账号风控2service
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
public interface AccountRisk2Service {

//    AccountRisk2ModelRes selectRisk2(AccountRisk2ModelReq params);

//    PageObjectResult<AccountRisk2ModelRes> selectRisk2Page(AccountRisk2ModelReq params);

    AccountRisk2ModelNewShowRes selectRisk2New(AccountRisk2ModelNewReq params);

    PageObjectResult<AccountRisk2ModelNewShowRes> selectRisk2NewPage(AccountRisk2ModelNewReq params);

}
