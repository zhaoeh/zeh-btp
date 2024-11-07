package ft.btp.i18n.api;

/**
 * @description: i18n 消息转换器
 * @author: ErHu.Zhao
 * @create: 2024-08-14
 **/
public interface I18nMessageConvert {

    /**
     * 将message进行国际化转换
     *
     * @param message 原始message
     * @return 国际化转换后的message
     */
    String convertMessage(String message);
}
