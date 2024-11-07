package ft.btp.validation.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @description: messageSource持有器
 * @author: ErHu.Zhao
 * @create: 2024-07-08
 **/
public class MessageSourceHolder implements MessageSourceAware {

    private static MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        MessageSourceHolder.messageSource = messageSource;
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }

    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
