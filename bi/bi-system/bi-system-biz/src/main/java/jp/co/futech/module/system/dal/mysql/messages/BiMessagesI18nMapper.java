package jp.co.futech.module.system.dal.mysql.messages;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.system.dal.dataobject.messages.MessagesDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @description: 国际化消息mapper
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
@Mapper
public interface BiMessagesI18nMapper extends BaseMapperX<MessagesDO> {

    /**
     * 查询所有国际化 messages
     *
     * @return all dict messages
     */
    default List<MessagesDO> selectAllMessages() {
        return selectList(new LambdaQueryWrapperX<MessagesDO>().orderByAsc(MessagesDO::getCode));
    }

    /**
     * 更新菜单表的国际化字段
     *
     * @return
     */
    @Update("update system_menu t1 join bi_messages_i18n t2 " +
            "on t1.name = t2.message and t2.code like '300%' set t1.i18n = concat('{',t2.code,'}') where t1.name is not null and t1.i18n is null;")
    int updateMenus();

    /**
     * 清空国际化资源表对应code的message
     *
     * @param code
     * @return
     */
    @Update("update bi_messages_i18n t set t.message = '' where t.code = #{code}")
    int updateMessages2Empty(@Param("code") String code);

    /**
     * 更新菜单表的国际化字段为空
     *
     * @return
     */
    @Update("update system_menu t set t.i18n = null;")
    int clearI18nOfMenu();

    /**
     * 暴力清表
     *
     * @return
     */
    @Update("delete from bi_messages_i18n")
    int clear();

}
