package jp.co.futech.module.system.service.i18n;

import cn.hutool.core.collection.CollectionUtil;
import jp.co.futech.module.system.dal.dataobject.messages.MessagesDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-08-01
 **/
@Component
@Slf4j
public class Syncer {

    @Autowired
    private MessageRefresher messageRefresher;

    @Async
    public void async(List<MessagesDO> dos) {
        log.info("开始异步刷新国际化，资源总数为{}", CollectionUtil.size(dos));
        messageRefresher.refresh(dos);
        log.info("结束异步刷新国际化");
    }
}
