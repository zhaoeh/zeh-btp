package jp.co.futech.module.system.service.i18n;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ft.btp.i18n.info.I18nMessages;

/**
 * @description: i18n国际化服务
 * @author: ErHu.Zhao
 * @create: 2024-07-18
 **/
public interface I18nService {

    /**
     * 组装I18nMessages
     *
     * @return 组装后的I18nMessages对象
     */
    I18nMessages encapI18nMessages(boolean isSnapshot);

    /**
     * 获取国际化资源
     *
     * @return
     */
    ObjectNode obtainMessages();

    /**
     * 更新资源为空串
     *
     * @return
     */
    int updateMessage2Empty(String code, boolean condition);

}
