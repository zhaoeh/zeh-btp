package ft.btp.i18n.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @description: 国际化消息封装对象
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class I18nMessage {

    private String locale;

    private Map<String, String> messages;

}
