package com.snowflake.cache.support;

import com.alicp.jetcache.support.AbstractValueDecoder;

public class Jackson2ValueDecoder extends AbstractValueDecoder {

    public static final Jackson2ValueDecoder INSTANCE = new Jackson2ValueDecoder(true);

    public Jackson2ValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    protected Object doApply(byte[] buffer) throws Exception {
        return CustomizedJackson.INSTANCE.readValue(buffer, Object.class);
    }
}
