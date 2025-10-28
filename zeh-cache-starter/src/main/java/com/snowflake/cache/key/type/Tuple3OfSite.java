package com.snowflake.cache.key.type;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Tuple3OfSite {

    private String site;
    public String merchantId;
    public String lotteryCode;
    public String playTypeCode;
}
