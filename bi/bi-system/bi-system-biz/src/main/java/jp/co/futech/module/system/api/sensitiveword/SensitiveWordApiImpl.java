package jp.co.futech.module.system.api.sensitiveword;

import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.module.system.service.sensitiveword.SensitiveWordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class SensitiveWordApiImpl implements SensitiveWordApi {

    @Resource
    private SensitiveWordService sensitiveWordService;

    @Override
    public CommonResult<List<String>> validateText(String text, List<String> tags) {
        return success(sensitiveWordService.validateText(text, tags));
    }

    @Override
    public CommonResult<Boolean> isTextValid(String text, List<String> tags) {
        return success(sensitiveWordService.isTextValid(text, tags));
    }

}
