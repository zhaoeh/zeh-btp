package jp.co.futech.module.insight.controller.admin.anomaly;

import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.module.insight.po.req.anomaly.*;
import jp.co.futech.module.insight.po.res.anomaly.*;
import jp.co.futech.module.insight.service.anomaly.AnomalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

/**
 * @description: Anomaly  模块controller
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Tag(name = "insight模块 - Anomaly智能分析")
@RestController
@RequestMapping("/insight/anomaly")
public class AnomalyController {

    @Autowired
    private AnomalyService anomalyService;

    /**
     * 智能分析一期异常分析页面
     *
     * @param anomalyGetInsight2Req
     * @return
     */
    @PostMapping("/get_insight1")
    @Operation(summary = "获取异常分析页面(一期)")
    @OperateLog(enable = false)
    public CommonResult<AnomalyGetInsight1Res> getInsight1(@Validated @RequestBody AnomalyGetInsight2Req anomalyGetInsight2Req) {
        return success(anomalyService.getInsight1(anomalyGetInsight2Req));
    }

    /**
     * 智能分析一期维度字典
     *
     * @return
     */
    @PostMapping("/get_dimensions")
    @Operation(summary = "获取异常分析维度字典(一期)")
    @OperateLog(enable = false)
    public CommonResult<List<AnomalyGetDimensionRes>> getDimensions() {
        return success(anomalyService.getDimensions());
    }

    @PostMapping("/get_anomaly")
    @Operation(summary = "获取异常点(一期)")
    @OperateLog(enable = false)
    public CommonResult<AnomalyGetAnomalyRes> getAnomaly(@Validated @RequestBody AnomalyGetAnomalyReq anomalyGetAnomalyReq) {
        return success(anomalyService.getAnomaly(anomalyGetAnomalyReq));
    }

    @PostMapping("/select_dimensions")
    @Operation(summary = "选择下钻维度(一期)")
    @OperateLog(enable = false)
    public CommonResult<List<AnomalySelectDimensionsRes>> selectDimensions(@Validated @RequestBody AnomalySelectDimensionsReq anomalySelectDimensionsReq) {
        return success(anomalyService.selectDimensions(anomalySelectDimensionsReq));
    }

    @PostMapping("/save_note_remark")
    @Operation(summary = "保存note和remark")
    @OperateLog(enable = false)
    public CommonResult<Boolean> saveNoteRemark(@Validated @RequestBody AnomalySaveNoteRemarkReq saveNoteReq) {
        return success(anomalyService.saveNoteRemark(List.of(saveNoteReq)));
    }

    @PostMapping("/get_insight2")
    @Operation(summary = "获取异常分析页面")
    @OperateLog(enable = false)
    public CommonResult<AnomalyGetInsight2Res> getInsight2(@Validated @RequestBody AnomalyGetInsight2Req anomalyGetInsight2Req) {
        return success(anomalyService.getInsight2(anomalyGetInsight2Req));
    }

    @PostMapping("/save_note")
    @Operation(summary = "保存note")
    @OperateLog(enable = false)
    public CommonResult<Boolean> saveNote(@Validated @RequestBody AnomalySaveNoteReq saveNoteReq) {
        return success(anomalyService.saveNotes(List.of(saveNoteReq)));
    }

    @PostMapping("/get_tree_data")
    @Operation(summary = "获取树图数据")
    @OperateLog(enable = false)
    public CommonResult<JSONObject> getTreeData(@Validated @RequestBody AnomalyGetTreeDataReq treeDataReq) {
        return success(anomalyService.getTreeData(treeDataReq));
    }

    @PostMapping("/get_trend_data")
    @Operation(summary = "获取趋势图数据")
    @OperateLog(enable = false)
    public CommonResult<AnomalyGetTrendDataRes> getTrendData(@Validated @RequestBody AnomalyGetTrendDataReq trendDataReq) {
        return success(anomalyService.getTrendData(trendDataReq));
    }
}
