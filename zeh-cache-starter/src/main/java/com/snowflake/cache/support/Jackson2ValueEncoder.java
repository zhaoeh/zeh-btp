package com.snowflake.cache.support;

import com.alicp.jetcache.support.AbstractValueEncoder;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Jackson2ValueEncoder extends AbstractValueEncoder {

    public static final Jackson2ValueEncoder INSTANCE = new Jackson2ValueEncoder(true);

    public Jackson2ValueEncoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    public byte[] apply(Object o) {
        try {
            return CustomizedJackson.INSTANCE.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


