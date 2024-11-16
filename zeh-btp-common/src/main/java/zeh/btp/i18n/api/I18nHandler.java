package zeh.btp.i18n.api;

/**
 * @description: i18n 处理器
 * @author: ErHu.Zhao
 * @create: 2024-08-14
 **/
@FunctionalInterface
public interface I18nHandler {

    /**
     * 对响应结果进行国际化处理
     *
     * @param result  返回值
     * @param convert 国际化消息转转换器
     */
    void handleResult(Object result, I18nMessageConvert convert);

}
