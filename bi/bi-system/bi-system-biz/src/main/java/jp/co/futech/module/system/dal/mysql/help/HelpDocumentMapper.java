package jp.co.futech.module.system.dal.mysql.help;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.system.controller.admin.help.vo.HelpReqVO;
import jp.co.futech.module.system.dal.dataobject.help.HelpDocumentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HelpDocumentMapper extends BaseMapperX<HelpDocumentDO> {

    default List<HelpDocumentDO> selectList(HelpReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<HelpDocumentDO>().
                eqIfPresent(HelpDocumentDO::getId, reqVO.getId()).
                eqIfPresent(HelpDocumentDO::getMenuId, reqVO.getMenuId()).
                eqIfPresent(HelpDocumentDO::getStatus, reqVO.getStatus()).
                eqIfHasText(HelpDocumentDO::getLocale, reqVO.getLocale()));
    }

    default boolean exists(HelpReqVO reqVO) {
        return exists(new LambdaQueryWrapperX<HelpDocumentDO>().
                eqIfPresent(HelpDocumentDO::getMenuId, reqVO.getMenuId()).eqIfHasText(HelpDocumentDO::getLocale, reqVO.getLocale()));
    }

}
