package jp.co.futech.framework.jackson.core.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description: jackson自定义序列化器，用于处理List<JsonNode>类型字段的问题
 * @author: ErHu.Zhao
 * @create: 2024-07-17
 **/
public class ListJsonNodeSerializer extends JsonSerializer<List<JsonNode>> {
    @Override
    public void serialize(List<JsonNode> jsonNodeList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (!CollectionUtils.isEmpty(jsonNodeList)) {
            jsonGenerator.writeStartArray();
            for (JsonNode node : jsonNodeList) {
                jsonGenerator.writeObject(changeJsonNode(node));
            }
            jsonGenerator.writeEndArray();
        }
    }

    private JsonNode changeJsonNode(JsonNode jsonNode) {
        if (Objects.nonNull(jsonNode) && jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            ObjectNode changeNode = objectNode.objectNode();
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey().replace("_", " ");
                JsonNode value = entry.getValue();
                changeNode.set(key, value);
            }
            return changeNode;
        }
        return jsonNode;
    }
}
