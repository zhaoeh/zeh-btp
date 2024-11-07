package jp.co.futech.module.anomaly.controller.admin.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.module.anomaly.service.DemoService;
import jp.co.futech.module.anomaly.vo.DemoReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

/**
 * @description: demo演示集成微服务到芋道
 * @author: ErHu.Zhao
 * @create: 2024-06-07
 **/
@Tag(name = "demo服务 - 演示")
@RestController
@RequestMapping("/demo/help")
@Validated
public class DemoController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/test")
    @Operation(summary = "演示demo")
//    @PermitAll
    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public CommonResult<Long> showDemo(@Validated(DemoReqVO.CreateHelpDocument.class) @RequestBody DemoReqVO createReqVO) {
        Long menuId = demoService.testDemo(createReqVO);
        return success(menuId);
    }

}
