package jp.co.futech.module.system.api.dept;

import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.module.system.service.dept.PostService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Collection;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public CommonResult<Boolean> validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
        return success(true);
    }

}
