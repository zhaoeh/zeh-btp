package jp.co.futech.module.system.service.errorcode;

import cn.hutool.core.collection.CollUtil;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.module.system.api.errorcode.dto.ErrorCodeAutoGenerateReqDTO;
import jp.co.futech.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import jp.co.futech.module.system.controller.admin.errorcode.vo.ErrorCodePageReqVO;
import jp.co.futech.module.system.controller.admin.errorcode.vo.ErrorCodeSaveReqVO;
import jp.co.futech.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import jp.co.futech.module.system.dal.mysql.errorcode.ErrorCodeMapper;
import jp.co.futech.module.system.enums.errorcode.ErrorCodeTypeEnum;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.framework.common.util.collection.CollectionUtils.convertMap;
import static jp.co.futech.framework.common.util.collection.CollectionUtils.convertSet;
import static jp.co.futech.module.system.enums.ErrorCodeConstants.ERROR_CODE_DUPLICATE;
import static jp.co.futech.module.system.enums.ErrorCodeConstants.ERROR_CODE_NOT_EXISTS;

/**
 * 错误码 Service 实现类
 *
 * @author dlyan
 */
@Service
@Validated
@Slf4j
public class ErrorCodeServiceImpl implements ErrorCodeService {

    @Resource
    private ErrorCodeMapper errorCodeMapper;

    @Override
    public Long createErrorCode(ErrorCodeSaveReqVO createReqVO) {
        // 校验 code 重复
        validateCodeDuplicate(createReqVO.getCode(), null);

        // 插入
        ErrorCodeDO errorCode = BeanUtils.toBean(createReqVO, ErrorCodeDO.class)
                .setType(ErrorCodeTypeEnum.MANUAL_OPERATION.getType());
        errorCodeMapper.insert(errorCode);
        // 返回
        return errorCode.getId();
    }

    @Override
    public void updateErrorCode(ErrorCodeSaveReqVO updateReqVO) {
        // 校验存在
        validateErrorCodeExists(updateReqVO.getId());
        // 校验 code 重复
        validateCodeDuplicate(updateReqVO.getCode(), updateReqVO.getId());

        // 更新
        ErrorCodeDO updateObj = BeanUtils.toBean(updateReqVO, ErrorCodeDO.class)
                .setType(ErrorCodeTypeEnum.MANUAL_OPERATION.getType());
        errorCodeMapper.updateById(updateObj);
    }

    @Override
    public void deleteErrorCode(Long id) {
        // 校验存在
        validateErrorCodeExists(id);
        // 删除
        errorCodeMapper.deleteById(id);
    }

    /**
     * 校验错误码的唯一字段是否重复
     *
     * 是否存在相同编码的错误码
     *
     * @param code 错误码编码
     * @param id 错误码编号
     */
    @VisibleForTesting
    public void validateCodeDuplicate(Integer code, Long id) {
        ErrorCodeDO errorCodeDO = errorCodeMapper.selectByCode(code);
        if (errorCodeDO == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的错误码
        if (id == null) {
            throw exception(ERROR_CODE_DUPLICATE);
        }
        if (!errorCodeDO.getId().equals(id)) {
            throw exception(ERROR_CODE_DUPLICATE.setParams(Map.of("id", id)), id);
        }
    }

    @VisibleForTesting
    void validateErrorCodeExists(Long id) {
        if (errorCodeMapper.selectById(id) == null) {
            throw exception(ERROR_CODE_NOT_EXISTS);
        }
    }

    @Override
    public ErrorCodeDO getErrorCode(Long id) {
        return errorCodeMapper.selectById(id);
    }

    @Override
    public PageResult<ErrorCodeDO> getErrorCodePage(ErrorCodePageReqVO pageReqVO) {
        return errorCodeMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional
    public void autoGenerateErrorCodes(List<ErrorCodeAutoGenerateReqDTO> autoGenerateDTOs) {
        if (CollUtil.isEmpty(autoGenerateDTOs)) {
            return;
        }
        // 获得错误码
        List<ErrorCodeDO> errorCodeDOs = errorCodeMapper.selectListByCodes(
                convertSet(autoGenerateDTOs, ErrorCodeAutoGenerateReqDTO::getCode));
        Map<Integer, ErrorCodeDO> errorCodeDOMap = convertMap(errorCodeDOs, ErrorCodeDO::getCode);

        // 遍历 autoGenerateBOs 数组，逐个插入或更新。考虑到每次量级不大，就不走批量了
        autoGenerateDTOs.forEach(autoGenerateDTO -> {
            ErrorCodeDO errorCode = errorCodeDOMap.get(autoGenerateDTO.getCode());
            // 不存在，则进行新增
            if (errorCode == null) {
                errorCode = BeanUtils.toBean(autoGenerateDTO, ErrorCodeDO.class)
                        .setType(ErrorCodeTypeEnum.AUTO_GENERATION.getType());
                errorCodeMapper.insert(errorCode);
                return;
            }
            // 存在，则进行更新。更新有三个前置条件：
            // 条件 1. 只更新自动生成的错误码，即 Type 为 ErrorCodeTypeEnum.AUTO_GENERATION
            if (!ErrorCodeTypeEnum.AUTO_GENERATION.getType().equals(errorCode.getType())) {
                return;
            }
            // 条件 2. 分组 applicationName 必须匹配，避免存在错误码冲突的情况
            if (!autoGenerateDTO.getApplicationName().equals(errorCode.getApplicationName())) {
                log.error("[autoGenerateErrorCodes][自动创建({}/{}) 错误码失败，数据库中已经存在({}/{})]",
                        autoGenerateDTO.getCode(), autoGenerateDTO.getApplicationName(),
                        errorCode.getCode(), errorCode.getApplicationName());
                return;
            }
            // 条件 3. 错误提示语存在差异
            if (autoGenerateDTO.getMessage().equals(errorCode.getMessage())) {
                return;
            }
            // 最终匹配，进行更新
            errorCodeMapper.updateById(new ErrorCodeDO().setId(errorCode.getId()).setMessage(autoGenerateDTO.getMessage()));
        });
    }

    @Override
    public List<ErrorCodeRespDTO> getErrorCodeList(String applicationName, LocalDateTime minUpdateTime) {
        List<ErrorCodeDO> list = errorCodeMapper.selectListByApplicationNameAndUpdateTimeGt(
                applicationName, minUpdateTime);
        return BeanUtils.toBean(list, ErrorCodeRespDTO.class);
    }

}

