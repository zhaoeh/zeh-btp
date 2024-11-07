package ft.btp.i18n.api;

import ft.btp.i18n.info.I18nMessages;

import java.util.Map;

/**
 * @description: 默认的i18n code映射器实现
 * @author: ErHu.Zhao
 * @create: 2024-08-14
 **/
public class DefaultI18nCodeMapper implements I18nCodeMapper {

    @Override
    public Map<String, String> provideI18nCodeMapper(I18nMessages messages) {
        return defaultCodeMapper(messages, null);
    }
}
