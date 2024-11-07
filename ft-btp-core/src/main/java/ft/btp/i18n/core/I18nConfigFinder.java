package ft.btp.i18n.core;

import ft.btp.i18n.api.I18nCodeMapper;
import ft.btp.i18n.api.I18nHandler;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @description: 国际化消息code查找器，根据message查找
 * @author: ErHu.Zhao
 * @create: 2024-08-14
 **/
public class I18nConfigFinder {

    private final Map<Class<? extends I18nCodeMapper>, Map<String, String>> codeMapperContainer = new HashMap<>();

    private final List<I18nHandler> handlers;

    private final List<I18nCodeMapper> codeMappers;

    private final I18nMessagesLoader i18nMessagesLoader;

    public I18nConfigFinder(List<I18nCodeMapper> codeMappers,
                            I18nMessagesLoader i18nMessagesLoader,
                            List<I18nHandler> handlers) {
        this.i18nMessagesLoader = i18nMessagesLoader;
        this.codeMappers = codeMappers;
        this.handlers = handlers;
        refreshCodeMapperContainer();
    }

    /**
     * 刷新资源
     */
    public void refreshCodeMapperContainer() {
        if (!CollectionUtils.isEmpty(codeMappers)) {
            codeMappers.stream().forEach(mapper -> codeMapperContainer.put(mapper.getClass(), mapper.provideI18nCodeMapper(i18nMessagesLoader.obtainI18nMessages())));
        }
    }

    /**
     * 根据message查找code
     *
     * @param clazz
     * @param message
     * @return
     */
    public String findCode(Class<? extends I18nCodeMapper> clazz, String message) {
        Map<String, String> container = findCodeMapping(clazz);
        if (CollectionUtils.isEmpty(container)) {
            return null;
        }
        String code = container.get(message);
        if (StringUtils.hasText(code)) {
            return code;
        }
        return null;
    }

    /**
     * 返回目标配置类对应的 message-code 映射容器
     *
     * @param clazz
     * @return
     */
    public Map<String, String> findCodeMapping(Class<? extends I18nCodeMapper> clazz) {
        Assert.isTrue(codeMapperContainer.containsKey(clazz), "cannot find I18nCodeMapper instance in spring container");
        Map<String, String> container = codeMapperContainer.get(clazz);
        if (CollectionUtils.isEmpty(container)) {
            return Collections.emptyMap();
        }
        return container;
    }

    /**
     * 查找 I18nHandler 实例
     *
     * @param clazz
     * @return
     */
    public I18nHandler findHandler(Class<? extends I18nHandler> clazz) {
        if (Objects.nonNull(clazz) && Objects.nonNull(handlers)) {
            return handlers.stream().filter(e -> clazz.isInstance(e)).findFirst().orElse(null);
        }
        return null;
    }
}
