package jp.co.futech.module.system.dal.mysql.messages;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.module.system.dal.dataobject.messages.MessagesHistoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 国际化消息历史mapper
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
@Mapper
public interface BiMessagesHistoryI18nMapper extends BaseMapperX<MessagesHistoryDO> {
}
