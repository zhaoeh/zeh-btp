package com.snowflake.cache.view.play;

import com.snowflake.cache.key.tuple.LotteryPlayKeyTuple;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class AllPlayView implements Serializable {

    /**
     * 五元组数据
     * key：五元组唯一键
     * value：数据
     */
    private Map<String, LotteryPlayKeyTuple> base;

    /**
     * 其他元组层级映射
     */
    private Map<String, String> tupleMapping;

}
