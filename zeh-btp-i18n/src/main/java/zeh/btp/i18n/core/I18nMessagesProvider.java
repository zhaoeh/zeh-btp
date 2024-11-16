package zeh.btp.i18n.core;

import zeh.btp.i18n.info.I18nMessages;

/**
 * @description: 国际化消息供应者
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
@FunctionalInterface
public interface I18nMessagesProvider {

    /**
     * 供应国际化消息
     *
     * @return 国际化消息对象源
     */
    I18nMessages provideMessages();
}
