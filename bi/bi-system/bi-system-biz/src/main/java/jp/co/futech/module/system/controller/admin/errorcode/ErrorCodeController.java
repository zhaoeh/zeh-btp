package jp.co.futech.module.system.controller.admin.errorcode;

import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.common.pojo.PageParam;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.framework.excel.core.util.ExcelUtils;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.module.system.controller.admin.errorcode.vo.ErrorCodePageReqVO;
import jp.co.futech.module.system.controller.admin.errorcode.vo.ErrorCodeRespVO;
import jp.co.futech.module.system.controller.admin.errorcode.vo.ErrorCodeSaveReqVO;
import jp.co.futech.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import jp.co.futech.module.system.service.errorcode.ErrorCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

import static jp.co.futech.framework.common.pojo.CommonResult.success;
import static jp.co.futech.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 错误码")
@RestController
@RequestMapping("/system/error-code")
@Validated
public class ErrorCodeController {

    @Resource
    private ErrorCodeService errorCodeService;

    @PostMapping("/create")
    @Operation(summary = "创建错误码")
    @PreAuthorize("@ss.hasPermission('system:error-code:create')")
    public CommonResult<Long> createErrorCode(@Valid @RequestBody ErrorCodeSaveReqVO createReqVO) {
        return success(errorCodeService.createErrorCode(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新错误码")
    @PreAuthorize("@ss.hasPermission('system:error-code:update')")
    public CommonResult<Boolean> updateErrorCode(@Valid @RequestBody ErrorCodeSaveReqVO updateReqVO) {
        errorCodeService.updateErrorCode(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除错误码")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:error-code:delete')")
    public CommonResult<Boolean> deleteErrorCode(@RequestParam("id") Long id) {
        errorCodeService.deleteErrorCode(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得错误码")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:error-code:query')")
    public CommonResult<ErrorCodeRespVO> getErrorCode(@RequestParam("id") Long id) {
        ErrorCodeDO errorCode = errorCodeService.getErrorCode(id);
        return success(BeanUtils.toBean(errorCode, ErrorCodeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得错误码分页")
    @PreAuthorize("@ss.hasPermission('system:error-code:query')")
    public CommonResult<PageResult<ErrorCodeRespVO>> getErrorCodePage(@Valid ErrorCodePageReqVO pageVO) {
        PageResult<ErrorCodeDO> pageResult = errorCodeService.getErrorCodePage(pageVO);
        return success(BeanUtils.toBean(pageResult, ErrorCodeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出错误码 Excel")
    @PreAuthorize("@ss.hasPermission('system:error-code:export')")
    @OperateLog(type = EXPORT)
    public void exportErrorCodeExcel(@Valid ErrorCodePageReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ErrorCodeDO> list = errorCodeService.getErrorCodePage(exportReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "错误码.xls", "数据", ErrorCodeRespVO.class,
                BeanUtils.toBean(list, ErrorCodeRespVO.class));
    }

}
