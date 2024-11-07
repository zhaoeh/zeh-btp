package jp.co.futech.module.system.api.logger;

import cn.hutool.core.collection.CollUtil;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.module.system.api.logger.dto.OperateLogCreateReqDTO;
import jp.co.futech.module.system.api.logger.dto.OperateLogV2CreateReqDTO;
import jp.co.futech.module.system.api.logger.dto.OperateLogV2PageReqDTO;
import jp.co.futech.module.system.api.logger.dto.OperateLogV2RespDTO;
import jp.co.futech.module.system.convert.logger.OperateLogConvert;
import jp.co.futech.module.system.dal.dataobject.logger.OperateLogV2DO;
import jp.co.futech.module.system.dal.dataobject.user.AdminUserDO;
import jp.co.futech.module.system.service.logger.OperateLogService;
import jp.co.futech.module.system.service.user.AdminUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.util.List;

import static jp.co.futech.framework.common.pojo.CommonResult.success;
import static jp.co.futech.framework.common.util.collection.CollectionUtils.convertSet;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;
    @Resource
    private AdminUserService adminUserService;

    @Override
    public CommonResult<Boolean> createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
        return success(true);
    }

    @Override
    public CommonResult<Boolean> createOperateLogV2(OperateLogV2CreateReqDTO createReqDTO) {
        operateLogService.createOperateLogV2(createReqDTO);
        return success(true);
    }

    @Override
    public CommonResult<PageResult<OperateLogV2RespDTO>> getOperateLogPage(OperateLogV2PageReqDTO pageReqVO) {
        PageResult<OperateLogV2DO> operateLogPage = operateLogService.getOperateLogPage(pageReqVO);
        if (CollUtil.isEmpty(operateLogPage.getList())) {
            return success(PageResult.empty());
        }

        // 获取用户
        List<AdminUserDO> userList = adminUserService.getUserList(
                convertSet(operateLogPage.getList(), OperateLogV2DO::getUserId));
        return success(OperateLogConvert.INSTANCE.convertPage(operateLogPage, userList));
    }

}
