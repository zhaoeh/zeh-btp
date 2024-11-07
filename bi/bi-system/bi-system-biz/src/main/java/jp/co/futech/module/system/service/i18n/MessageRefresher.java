package jp.co.futech.module.system.service.i18n;

import cn.hutool.core.collection.CollectionUtil;
import jp.co.futech.module.system.dal.dataobject.messages.MessagesDO;
import jp.co.futech.module.system.dal.mysql.messages.BiMessagesI18nMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 消息刷新器
 * @author: ErHu.Zhao
 * @create: 2024-07-26
 **/
@Component
public class MessageRefresher {

    @Autowired
    private BiMessagesI18nMapper biMessagesI18nMapper;

    /**
     * 异步刷新
     *
     * @param dos
     */
    @Transactional(rollbackFor = Exception.class)
    public void refresh(List<MessagesDO> dos) {
        if (CollectionUtil.isNotEmpty(dos)) {
            biMessagesI18nMapper.clear();
            biMessagesI18nMapper.insertBatch(dos, 5000);
        }
    }
}
