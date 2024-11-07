package jp.co.futech.module.insight.controller.admin.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.co.futech.framework.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.security.PermitAll;

import java.util.Map;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

/**
 * 健康检查
 */
@Tag(name = "insight 模块 - 健康检查")
@RestController
@RequestMapping("/insight/health")
@Validated
public class HealthController {

    @Value("${spring.application.name}")
    private String serverName;

    @Autowired
    protected GitProperties gitProperties;

    @GetMapping()
    @Operation(summary = "健康检查")
    @PermitAll
    public CommonResult<?> health() {
        return success(Map.of(
                "serverName", serverName,
                "version", gitProperties.getCommitId()
        ));
    }
}
