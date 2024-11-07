package jp.co.futech.module.system.dal.dataobject.messages;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 消息描述体
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
@TableName("bi_messages_i18n_history")
@KeySequence("bi_messages_i18n_history_seq")
@Data
@Builder
public class MessagesHistoryDO {

    @TableId
    private Long id;

    private String code;

    private String message;

    private String locale;

}
