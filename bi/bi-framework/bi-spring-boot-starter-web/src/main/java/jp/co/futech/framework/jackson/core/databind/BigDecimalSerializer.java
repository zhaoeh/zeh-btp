package jp.co.futech.framework.jackson.core.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @description: jackson自定义序列化器，用于处理BigDecimal字段保持2位精度
 * @author: ErHu.Zhao
 * @create: 2024-07-17
 **/
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.setScale(2, RoundingMode.DOWN));
    }
}
