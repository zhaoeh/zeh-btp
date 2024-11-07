package jp.co.futech.module.insight.controller.admin.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.module.insight.mapper.pg.TestPgMapper;
import jp.co.futech.module.insight.po.req.demo.DemoShowReq;
import jp.co.futech.module.insight.service.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

/**
 * @description: help帮助文档控制器
 * @author: ErHu.Zhao
 * @create: 2024-06-07
 **/
@Tag(name = "insight模块 - demo演示")
@RestController
@RequestMapping("/insight/demo")
@Validated
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private TestPgMapper testPgMapper;

    @PostMapping("/show")
    @Operation(summary = "演示demo")
    @PermitAll
    @OperateLog(enable = false)
    public CommonResult<Long> showDemo(@Validated(DemoShowReq.CreateHelpDocument.class) @RequestBody DemoShowReq createReqVO) {
        Long menuId = demoService.testDemo(createReqVO);
        return success(menuId);
    }

    @GetMapping("/pg")
    @Operation(summary = "测试pg")
    @PermitAll
    @OperateLog(enable = false)
    public CommonResult<Integer> pg() {
        return success(testPgMapper.selectCount());
    }

}
