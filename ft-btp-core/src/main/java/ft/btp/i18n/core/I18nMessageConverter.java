package ft.btp.i18n.core;

import ft.btp.i18n.api.I18nCodeMapper;
import ft.btp.i18n.api.I18nMessageConvert;
import org.springframework.util.StringUtils;

/**
 * @description: i18n消息转换器
 * @author: ErHu.Zhao
 * @create: 2024-08-15
 **/
public class I18nMessageConverter implements I18nMessageConvert {

    private final I18nConfigFinder configFinder;

    private final I18nMessageWrapper i18nMessageWrapper;

    private final Class<? extends I18nCodeMapper> mapperConfigure;

    public I18nMessageConverter(I18nConfigFinder configFinder,
                                I18nMessageWrapper i18nMessageWrapper,
                                Class<? extends I18nCodeMapper> mapperConfigure) {
        this.configFinder = configFinder;
        this.i18nMessageWrapper = i18nMessageWrapper;
        this.mapperConfigure = mapperConfigure;
    }

    /**
     * 根据国际化转换message
     *
     * @param message 原始message
     * @return 国际化后的message
     */
    @Override
    public String convertMessage(String message) {
        String convert = null;
        String i18nCode = configFinder.findCode(mapperConfigure, message);
        if (StringUtils.hasText(i18nCode)) {
            convert = i18nMessageWrapper.getMessage(i18nCode);
        }
        if (StringUtils.hasText(convert)) {
            return convert;
        }
        return message;
    }
}
