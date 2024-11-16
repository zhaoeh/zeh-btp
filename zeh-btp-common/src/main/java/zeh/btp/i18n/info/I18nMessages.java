package zeh.btp.i18n.info;

import java.util.ArrayList;
import java.util.List;


/**
 * @description: 国际化消息封装对象
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
public class I18nMessages extends ArrayList<I18nMessage> {

    public boolean addMessage(I18nMessage message) {
        return super.add(message);
    }

    public boolean addMessages(List<I18nMessage> messages) {
        return super.addAll(messages);
    }

}
