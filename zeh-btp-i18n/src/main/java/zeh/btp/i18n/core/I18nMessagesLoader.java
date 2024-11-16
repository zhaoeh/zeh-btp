package zeh.btp.i18n.core;

import zeh.btp.i18n.info.I18nMessage;
import zeh.btp.i18n.info.I18nMessages;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * @description: 国际化消息加载器
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
@Data
@Slf4j
public class I18nMessagesLoader {

    private I18nMessagesProvider i18nMessagesProvider;

    private StaticMessageSource source;

    private final ObjectProvider<I18nConfigFinder> configFinderObjectProvider;

    public I18nMessagesLoader(I18nMessagesProvider i18nMessagesProvider,
                              ObjectProvider<I18nConfigFinder> configFinderObjectProvider) {
        this.i18nMessagesProvider = i18nMessagesProvider;
        this.configFinderObjectProvider = configFinderObjectProvider;
    }

    /**
     * 重新加载国际化消息
     */
    public void reloadMessages() {
        Assert.notNull(source, "StaticMessageSource instance cannot be null.");
        log.info("开始加载 messages ");
        refreshCodeMapper();
        I18nMessages messages = obtainI18nMessages();
        loadMessages(messages);
        log.info("结束加载 messages ");
    }

    /**
     * 手动添加国际化消息
     *
     * @param messages
     */
    public void addMessages(I18nMessages messages) {
        loadMessages(messages);
    }

    /**
     * 手动添加国际化消息
     *
     * @param message
     */
    public void addMessage(I18nMessage message) {
        loadMessage(message);
    }

    /**
     * 加载国际化消息到容器中
     *
     * @param messages 国际化消息集
     */
    private void loadMessages(I18nMessages messages) {
        if (Objects.nonNull(messages)) {
            Assert.notNull(source, "StaticMessageSource instance cannot be null.");
            messages.stream().forEach(this::loadMessage);
        }
    }

    /**
     * 加载单个国家化消息到容器中
     *
     * @param message 国际化消息
     */
    private void loadMessage(I18nMessage message) {
        if (Objects.nonNull(message)) {
            Assert.notNull(source, "StaticMessageSource instance cannot be null.");
            source.addMessages(message.getMessages(), Locale.forLanguageTag(message.getLocale()));
        }
    }

    /**
     * 获取国际化消息源对象
     *
     * @return 国际化消息源
     */
    public I18nMessages obtainI18nMessages() {
        if (Objects.nonNull(i18nMessagesProvider)) {
            I18nMessages messages = i18nMessagesProvider.provideMessages();
            return checkMessages(messages);
        }
        return new I18nMessages();
    }

    /**
     * 校验 messages
     *
     * @param messages messages
     * @return messages
     */
    private I18nMessages checkMessages(I18nMessages messages) {
        if (Objects.nonNull(messages)) {
            messages.stream().forEach(message -> {
                String locale = message.getLocale();
                if (!checkLocale(locale)) {
                    throw new IllegalArgumentException("there are invalid locale in I18nMessagesProvider impls.");
                }
            });
        }
        return messages;
    }

    private boolean checkLocale(String locale) {
        Locale lo = Locale.forLanguageTag(locale);
        if (Arrays.stream(Locale.getAvailableLocales()).anyMatch(l -> l.equals(lo))) {
            return true;
        }
        return false;
    }

    /**
     * 重新管理messageSource同时刷新容器中codeMapper映射器
     */
    private void refreshCodeMapper() {
        I18nConfigFinder finder = configFinderObjectProvider.getIfAvailable();
        Optional.ofNullable(finder).ifPresent(t -> t.refreshCodeMapperContainer());
    }

}
