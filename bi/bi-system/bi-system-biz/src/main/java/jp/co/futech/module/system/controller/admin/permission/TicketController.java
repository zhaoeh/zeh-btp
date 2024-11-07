package jp.co.futech.module.system.controller.admin.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.ip.core.Area;
import jp.co.futech.framework.ip.core.utils.AreaUtils;
import jp.co.futech.framework.ip.core.utils.IPUtils;
import jp.co.futech.module.system.service.permission.TicketService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static jp.co.futech.framework.common.pojo.CommonResult.success;


/**
 * @author MYK-Heng.zhang
 */
@Tag(name = "管理后台 - 获取ticket")
@RestController
@RequestMapping("/system/ticket")
public class TicketController {

    @Resource
    private TicketService ticketService;

    @GetMapping("/get_ticket")
    @Operation(summary = "获取ticket")
    public CommonResult<Map<String,String>> getAreaByIp() throws ExecutionException, InterruptedException {
        // 格式化返回
        return success(ticketService.getTicket());
    }



}
