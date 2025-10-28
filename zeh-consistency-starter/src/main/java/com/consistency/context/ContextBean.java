package com.consistency.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class ContextBean {

    private String key;

    private Object body;

    private MessageExt messageExt;

    public <T> T getBody(Class<T> clazz) {
        if (Objects.isNull(body)) {
            return null;
        }
        return clazz.isInstance(body) ? clazz.cast(body) : null;
    }
}
