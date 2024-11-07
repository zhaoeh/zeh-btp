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
@TableName("bi_messages_i18n")
@KeySequence("bi_messages_i18n_seq")
@Data
@Builder
public class MessagesDO {

    @TableId
    private Long id;

    private String code;

    private String message;

    /**
     * 多语言标识
     */
    private String locale;

}
