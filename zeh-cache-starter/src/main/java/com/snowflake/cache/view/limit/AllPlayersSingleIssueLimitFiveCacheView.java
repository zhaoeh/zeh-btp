package com.snowflake.cache.view.limit;

import com.snowflake.cache.view.construction.Tuple5CacheView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 某个代理节点内部五元组key对应的唯一限额配置视图
 *
 * 某个代理旗下所有玩家单期投注某个小玩法的累计投注限额，目前业务只设计到包网即分公司设计一套单期限额配置，那么分公司的所有子孙级代理的玩家在某期累计投注某个玩法额度，共享该配置阈值
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AllPlayersSingleIssueLimitFiveCacheView extends Tuple5CacheView implements Serializable {

    /**
     * 所有玩家单期单个玩法累计投注限额
     */
    private BigDecimal allPlayersSingleIssueLimit;
}
