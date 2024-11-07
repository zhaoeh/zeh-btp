package jp.co.futech.framework.jackson.core.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * @description: jackson自定义序列化器，用于处理JsonNode类型字段的问题
 * @author: ErHu.Zhao
 * @create: 2024-07-17
 **/
public class JsonNodeSerializer extends JsonSerializer<JsonNode> {
    @Override
    public void serialize(JsonNode jsonNode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.nonNull(jsonNode) && jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            jsonGenerator.writeStartObject();
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey().replace("_", " ");
                jsonGenerator.writeFieldName(key);
                jsonGenerator.writeObject(entry.getValue());
            }
            jsonGenerator.writeEndObject();

        } else {
            jsonGenerator.writeObject(jsonNode);
        }
    }
}
