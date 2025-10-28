package com.consistency.hook;

import com.consistency.config.ConverterConfigure;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 一致性配置器
 */
public interface ConsistencyConfigure {

    /**
     * 消息实体转换器
     * @param topic
     * @param tag
     * @return
     */
    ConverterConfigure converter(String topic, String tag, String body);

    /**
     * 是否跳过调度，本质是幂等性设计，由业务侧指定
     * @return true：已经被调度，跳过本地调度保证幂等  false：未被调度，继续执行本地调度
     */
//    boolean skip(MessageExt messageExt, ConverterConfigure converterConfigure);


    /**
     * 调度成功后的执行
     */
    default void doAfterSuccess(MessageExt messageExt, ConverterConfigure converterConfigure) {
    }

    /**
     * 是否跳过异常抛出直接记录到失败表
     * @param messageExt 原始消息
     * @param e 抛出的异常
     * @return
     */
    boolean skipThrowAndDoFinallyFail(MessageExt messageExt, ConverterConfigure converterConfigure, Exception e);

    /**
     * 当最终失败后要做的事情：一般用于记录数据到失败表中，作为最终的人工介入兜底源
     * @param messageExt 原始消息
     * @param e 异常
     */
    void doFinallyFail(MessageExt messageExt, ConverterConfigure converterConfigure, Exception e);
}
