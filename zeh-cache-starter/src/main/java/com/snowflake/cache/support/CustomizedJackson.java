package com.snowflake.cache.support;

import cn.hutool.core.util.ArrayUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class CustomizedJackson {

    static final ObjectMapper INSTANCE = new ObjectMapper();

    static {
        // 空对象不报错
        INSTANCE.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 未知属性不报错，直接忽略
        INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        INSTANCE.registerModules(new JavaTimeModule()); // 解决 LocalDateTime 的序列化
        INSTANCE.activateDefaultTyping(new CustomizedPolymorphicTypeValidator("com.snowflake", "com.baomidou"), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    private static class CustomizedPolymorphicTypeValidator extends PolymorphicTypeValidator.Base {

        /**
         * 允许的包前缀
         */
        private final String[] basePackages;

        public CustomizedPolymorphicTypeValidator(String... allowedBasePackage) {
            basePackages = allowedBasePackage;
        }

        @Override
        public Validity validateBaseType(MapperConfig<?> mapperConfig, JavaType javaType) {
            return Validity.INDETERMINATE;
        }

        @Override
        public Validity validateSubClassName(MapperConfig<?> mapperConfig, JavaType baseType, String subClassName) throws JsonMappingException {
            if (ArrayUtil.isEmpty(basePackages)) {
                return Validity.ALLOWED;
            }
            if (isAllowed(subClassName)) {
                return Validity.ALLOWED;
            }
            if (subClassName.startsWith("java.")) {
                return Validity.INDETERMINATE;
            }
            return Validity.DENIED;
        }

        @Override
        public Validity validateSubType(MapperConfig<?> mapperConfig, JavaType baseType, JavaType subType) throws JsonMappingException {
            return Validity.ALLOWED;
        }

        /**
         * 目标子类型是否允许反序列化
         *
         * @param subClassName 子类名称
         * @return 是否允许被
         */
        private boolean isAllowed(String subClassName) {
            if (ArrayUtil.isNotEmpty(basePackages) && StringUtils.isNotBlank(subClassName)) {
                return Arrays.stream(basePackages).anyMatch(subClassName::startsWith);
            }
            return false;
        }
    }

}
