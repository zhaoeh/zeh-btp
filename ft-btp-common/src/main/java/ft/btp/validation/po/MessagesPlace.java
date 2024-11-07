package ft.btp.validation.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @description: 自定义message实体
 * @author: ErHu.Zhao
 * @create: 2024-07-08
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessagesPlace {

    private String message;

    private Map<Object, Object> places;
}
