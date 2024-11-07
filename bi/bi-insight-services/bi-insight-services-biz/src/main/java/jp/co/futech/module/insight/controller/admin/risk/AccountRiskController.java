package jp.co.futech.module.insight.controller.admin.risk;

import cn.hutool.core.date.format.FastDateFormat;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.common.pojo.PageParam;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.common.util.date.DateUtils;
import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.framework.excel.core.util.ExcelUtils;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.module.insight.config.RiskQueryConditionsConfig;
import jp.co.futech.module.insight.entity.bh.risk.AccountRiskListEntity;
import jp.co.futech.module.insight.entity.pg.risk.PgAccountRiskListEntity;
import jp.co.futech.module.insight.po.req.risk.AccountRiskBarChartQueryReq;
import jp.co.futech.module.insight.po.req.risk.AccountRiskListQueryReq;
import jp.co.futech.module.insight.po.req.risk.AccountRiskPieChartQueryReq;
import jp.co.futech.module.insight.po.res.risk.AccountRiskBarChartQueryRes;
import jp.co.futech.module.insight.po.res.risk.AccountRiskConditionsQueryRes;
import jp.co.futech.module.insight.po.res.risk.AccountRiskPieChart;
import jp.co.futech.module.insight.service.risk.AccountRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.framework.common.pojo.CommonResult.success;
import static jp.co.futech.module.insight.exception.ErrorCodeConstants.TASK_META_TYPE_ERROR;

/**
 * @description: Anomaly 模块controller
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Tag(name = "insight模块 - 账号风控")
@RestController
@RequestMapping("/insight/risk")
@Slf4j
public class AccountRiskController {

    @Autowired
    private AccountRiskService accountRiskService;

    @Autowired
    private RiskQueryConditionsConfig config;

    @GetMapping("/get_conditions")
    @Operation(summary = "获取查询条件集")
    @OperateLog(enable = false)
    public CommonResult<AccountRiskConditionsQueryRes> getQueryConditions() {
        return success(BeanUtils.toBean(config, AccountRiskConditionsQueryRes.class));
    }

    @PostMapping("/get_list")
    @Operation(summary = "获取账号风控详情分页")
    @OperateLog(enable = false)
    public CommonResult<PageResult<PgAccountRiskListEntity>> getRiskList(@Validated @RequestBody AccountRiskListQueryReq req) {
        return success(accountRiskService.selectRiskListByPage(req));
    }

    @PostMapping("/export")
    @Operation(summary = "导出账号风控详情分页")
    @OperateLog(enable = false)
    public void exportRiskList(@Validated @RequestBody AccountRiskListQueryReq req,
                                     HttpServletResponse response) throws IOException {
        List<PgAccountRiskListEntity> list = accountRiskService.selectRiskListByPage((AccountRiskListQueryReq) req.setSortingWhenNonePaging(true).setPageSize(PageParam.PAGE_SIZE_NONE)).getList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fileName = "risk_"+LocalDateTime.now().format(formatter) + ".csv";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename*=utf-8''"+fileName);

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PgAccountRiskListEntity.class).build();


        excelWriter.write(list,EasyExcel.writerSheet("Risk Event").build());
        excelWriter.finish();
    }

    @PostMapping("/get_bar_chart")
    @Operation(summary = "获取账号风控柱状图")
    @OperateLog(enable = false)
    public CommonResult<List<AccountRiskBarChartQueryRes>> getBarChart(@Validated @RequestBody AccountRiskBarChartQueryReq req) {
        return success(accountRiskService.selectBarChartData(req));
    }

    @PostMapping("/get_pie_chart")
    @Operation(summary = "获取账号风控饼状图")
    @OperateLog(enable = false)
    public CommonResult<List<AccountRiskPieChart>> getPieChart(@Validated @RequestBody AccountRiskPieChartQueryReq req) {
        return success(accountRiskService.selectPieChartData(req));
    }
}
