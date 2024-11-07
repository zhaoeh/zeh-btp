package jp.co.futech.framework.jackson.core.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @description: jackson自定义序列化器，用于处理BigDecimal字段保持0位精度，即去除小数位
 * @author: ErHu.Zhao
 * @create: 2024-07-17
 **/
public class BigDecimalZeroSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.setScale(0, RoundingMode.DOWN));
    }
}
