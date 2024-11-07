package jp.co.futech.module.system.api.social;

import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.module.system.api.social.dto.SocialUserBindReqDTO;
import jp.co.futech.module.system.api.social.dto.SocialUserRespDTO;
import jp.co.futech.module.system.api.social.dto.SocialUserUnbindReqDTO;
import jp.co.futech.module.system.service.social.SocialUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class SocialUserApiImpl implements SocialUserApi {

    @Resource
    private SocialUserService socialUserService;

    @Override
    public CommonResult<String> bindSocialUser(SocialUserBindReqDTO reqDTO) {
        return success(socialUserService.bindSocialUser(reqDTO));
    }

    @Override
    public CommonResult<Boolean> unbindSocialUser(SocialUserUnbindReqDTO reqDTO) {
        socialUserService.unbindSocialUser(reqDTO.getUserId(), reqDTO.getUserType(),
                reqDTO.getSocialType(), reqDTO.getOpenid());
        return success(true);
    }

    @Override
    public CommonResult<SocialUserRespDTO> getSocialUserByUserId(Integer userType, Long userId, Integer socialType) {
        return success(socialUserService.getSocialUserByUserId(userType, userId, socialType));
    }

    @Override
    public CommonResult<SocialUserRespDTO> getSocialUserByCode(Integer userType, Integer socialType, String code, String state) {
        return success(socialUserService.getSocialUserByCode(userType, socialType, code, state));
    }

}
