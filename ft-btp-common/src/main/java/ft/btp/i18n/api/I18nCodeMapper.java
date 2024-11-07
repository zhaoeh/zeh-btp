package ft.btp.i18n.api;

import ft.btp.i18n.info.I18nMessages;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: i18n code查找器
 * @author: ErHu.Zhao
 * @create: 2024-08-14
 **/
@FunctionalInterface
public interface I18nCodeMapper {

    /**
     * 提供i18n code的映射器逻辑
     *
     * @param messages i18n messages
     * @return message和code映射器
     */
    Map<String, String> provideI18nCodeMapper(I18nMessages messages);

    /**
     * 默认code 映射器，方便外部使用
     *
     * @param messages  i18n messages
     * @param predicate 条件表达式
     * @return 返回符合条件表达式的 message和code映射器
     */
    default Map<String, String> defaultCodeMapper(I18nMessages messages, Predicate<Map.Entry<String, String>> predicate) {
        Map<String, String> codeMapper = new HashMap<>(12);
        if (CollectionUtils.isEmpty(messages)) {
            return codeMapper;
        }
        return messages.stream().flatMap(message -> {
            Map<String, String> megs = message.getMessages();
            if (CollectionUtils.isEmpty(megs)) {
                return Stream.empty();
            }
            Stream<Map.Entry<String, String>> stream = message.getMessages().entrySet().stream().
                    filter(entry -> StringUtils.hasText(entry.getValue())).map(entry -> {
                        entry.setValue(entry.getValue().trim());
                        return entry;
                    });
            if (Objects.nonNull(predicate)) {
                return stream.filter(entry -> predicate.test(entry));
            } else {
                return stream;
            }
        }).collect(Collectors.toMap(
                Map.Entry::getValue,
                Map.Entry::getKey,
                (exist, replace) -> replace
        ));
    }
}
