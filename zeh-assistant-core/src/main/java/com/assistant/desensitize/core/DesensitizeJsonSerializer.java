package com.assistant.desensitize.core;

import com.assistant.desensitize.annotation.Desensitize;
import com.assistant.desensitize.core.factory.DesensitizeStrategyFactory;
import com.assistant.desensitize.core.strategy.DesensitizeStrategy;
import com.assistant.desensitize.core.strategy.impl.DefaultStrategy;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 基于jackson实现的脱敏序列化器：支持内置序列化策略和业务侧自定义的序列化策略
 */
@Slf4j
public class DesensitizeJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏注解实例
     */
    private Desensitize annotation;

    public DesensitizeJsonSerializer() {
    }

    public DesensitizeJsonSerializer(Desensitize ann) {
        this.annotation = ann;
    }

    /**
     * 实现的序列化回调
     * @param value 原始值
     * @param gen jackson处理器
     * @param serializers 序列化供应者
     * @throws IOException 异常
     */
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (DesensitizeContext.shouldSkip()) {
            gen.writeString(value);
            return;
        }
        if (annotation == null || value == null) {
            gen.writeString(value);
            return;
        }

        String result = value;
        DesensitizeStrategy strategy = null;
        // 优先用自定义 strategy
        if (annotation.strategy() != DefaultStrategy.class) {
            // 根据注入容器的类名从工厂中获取对应的策略器实例
            strategy = DesensitizeStrategyFactory.getByClass(annotation.strategy());
        }
        if (strategy != null) {
            result = strategy.apply(value);
        }
        gen.writeString(result);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
            throws JsonMappingException {
        if (property != null) {
            Desensitize ann = property.getAnnotation(Desensitize.class);
            if (ann == null) {
                ann = property.getContextAnnotation(Desensitize.class);
            }
            if (ann != null) {
                return new DesensitizeJsonSerializer(ann);
            }
            return prov.findValueSerializer(property.getType(), property);
        }
        return prov.findNullValueSerializer(property);
    }
}
