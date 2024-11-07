package jp.co.futech.module.system.dal.mysql.logger;

import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.system.api.logger.dto.OperateLogV2PageReqDTO;
import jp.co.futech.module.system.dal.dataobject.logger.OperateLogV2DO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateLogV2Mapper extends BaseMapperX<OperateLogV2DO> {

    default PageResult<OperateLogV2DO> selectPage(OperateLogV2PageReqDTO pageReqDTO) {
        return selectPage(pageReqDTO, new LambdaQueryWrapperX<OperateLogV2DO>()
                .eqIfPresent(OperateLogV2DO::getType, pageReqDTO.getBizType())
                .eqIfPresent(OperateLogV2DO::getBizId, pageReqDTO.getBizId())
                .eqIfPresent(OperateLogV2DO::getUserId, pageReqDTO.getUserId())
                .orderByDesc(OperateLogV2DO::getId));
    }

}
