package jp.co.futech.module.insight.controller.admin.risk;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.common.pojo.PageObjectResult;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.module.insight.po.req.risk.AccountRisk2ModelNewReq;
import jp.co.futech.module.insight.po.req.risk.AccountRisk2ModelReq;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelNewShowRes;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelRes;
import jp.co.futech.module.insight.service.risk.AccountRisk2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

/**
 * @description: 账号风控二期
 * @author: ErHu.Zhao
 * @create: 2024-07-16
 **/
@Tag(name = "insight模块 - 账号风控2")
@RestController
@RequestMapping("/insight/risk2")
public class AccountRisk2Controller {

    @Autowired
    private AccountRisk2Service accountRisk2Service;

//    @PostMapping("/get_list")
//    @Operation(summary = "获取账号风控详情")
//    @OperateLog(enable = false)
//    public CommonResult<AccountRisk2ModelRes> getRiskList(@RequestBody @Valid AccountRisk2ModelReq req) {
//        return success(accountRisk2Service.selectRisk2(req));
//    }

//    @PostMapping("/get_list_page")
//    @Operation(summary = "获取账号风控详情分页")
//    @OperateLog(enable = false)
//    public CommonResult<PageObjectResult<AccountRisk2ModelRes>> getRiskListPage(@RequestBody @Valid AccountRisk2ModelReq req) {
//        return success(accountRisk2Service.selectRisk2Page(req));
//    }

    @PostMapping("/get_list_new")
    @Operation(summary = "获取账号风控详情")
    @OperateLog(enable = false)
    public CommonResult<AccountRisk2ModelNewShowRes> getRiskListNew(@RequestBody @Valid AccountRisk2ModelNewReq req) {
        return success(accountRisk2Service.selectRisk2New(req));
    }

    @PostMapping("/get_list_page_new")
    @Operation(summary = "获取账号风控详情分页")
    @OperateLog(enable = false)
    public CommonResult<PageObjectResult<AccountRisk2ModelNewShowRes>> getRiskListNewPage(@RequestBody @Valid AccountRisk2ModelNewReq req) {
        return success(accountRisk2Service.selectRisk2NewPage(req));
    }
}
