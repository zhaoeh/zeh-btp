package jp.co.futech.module.system.service.logger;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.framework.common.util.string.StrUtils;
import jp.co.futech.module.system.api.logger.dto.OperateLogCreateReqDTO;
import jp.co.futech.module.system.api.logger.dto.OperateLogV2CreateReqDTO;
import jp.co.futech.module.system.api.logger.dto.OperateLogV2PageReqDTO;
import jp.co.futech.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import jp.co.futech.module.system.dal.dataobject.logger.OperateLogDO;
import jp.co.futech.module.system.dal.dataobject.logger.OperateLogV2DO;
import jp.co.futech.module.system.dal.dataobject.user.AdminUserDO;
import jp.co.futech.module.system.dal.mysql.logger.OperateLogMapper;
import jp.co.futech.module.system.dal.mysql.logger.OperateLogV2Mapper;
import jp.co.futech.module.system.service.user.AdminUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

import static jp.co.futech.framework.common.util.collection.CollectionUtils.convertSet;
import static jp.co.futech.module.system.dal.dataobject.logger.OperateLogDO.JAVA_METHOD_ARGS_MAX_LENGTH;
import static jp.co.futech.module.system.dal.dataobject.logger.OperateLogDO.RESULT_MAX_LENGTH;

/**
 * 操作日志 Service 实现类
 *
 * @author futech.co.jp
 */
@Service
@Validated
@Slf4j
public class OperateLogServiceImpl implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;
    @Resource
    private OperateLogV2Mapper operateLogV2Mapper;

    @Resource
    private AdminUserService userService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        OperateLogDO log = BeanUtils.toBean(createReqDTO, OperateLogDO.class);
        log.setJavaMethodArgs(StrUtils.maxLength(log.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
        log.setResultData(StrUtils.maxLength(log.getResultData(), RESULT_MAX_LENGTH));
        operateLogMapper.insert(log);
    }

    @Override
    public PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO pageReqVO) {
        // 处理基于用户昵称的查询
        Collection<Long> userIds = null;
        if (StrUtil.isNotEmpty(pageReqVO.getUserNickname())) {
            userIds = convertSet(userService.getUserListByNickname(pageReqVO.getUserNickname()), AdminUserDO::getId);
            if (CollUtil.isEmpty(userIds)) {
                return PageResult.empty();
            }
        }
        // 查询分页
        return operateLogMapper.selectPage(pageReqVO, userIds);
    }

    // ======================= LOG V2 =======================

    @Override
    public void createOperateLogV2(OperateLogV2CreateReqDTO createReqDTO) {
        OperateLogV2DO log = BeanUtils.toBean(createReqDTO, OperateLogV2DO.class);
        operateLogV2Mapper.insert(log);
    }

    @Override
    public PageResult<OperateLogV2DO> getOperateLogPage(OperateLogV2PageReqDTO pageReqDTO) {
        return operateLogV2Mapper.selectPage(pageReqDTO);
    }

}
