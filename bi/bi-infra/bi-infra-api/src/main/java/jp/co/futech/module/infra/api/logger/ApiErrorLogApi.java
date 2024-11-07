package jp.co.futech.module.infra.api.logger;

import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import jp.co.futech.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(name = ApiConstants.NAME, url = ApiConstants.URL) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - API 异常日志")
public interface ApiErrorLogApi {

    String PREFIX = ApiConstants.PREFIX + "/api-error-log";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建 API 异常日志")
    CommonResult<Boolean> createApiErrorLog(@Valid @RequestBody ApiErrorLogCreateReqDTO createDTO);

}
