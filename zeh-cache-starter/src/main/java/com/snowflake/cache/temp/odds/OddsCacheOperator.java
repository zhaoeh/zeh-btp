package com.snowflake.cache.temp.odds;//package com.snowflake.proxy.cache.temp.odds;
//
//import com.snowflake.proxy.data.entities.Agent;
//import com.snowflake.proxy.data.entities.FastLotteryOdds;
//import com.snowflake.proxy.exception.BusinessException;
//import com.snowflake.proxy.fast.odds.FastOddsAwards;
//import com.snowflake.proxy.fast.odds.context.WeaverContext;
//import com.snowflake.proxy.fast.odds.weaver.FastAwardsWeaverImpl;
//import com.snowflake.proxy.fast.odds.weaver.FastOddsWeaverImpl;
//import org.apache.commons.lang3.StringUtils;
//import org.redisson.api.RList;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import static com.snowflake.framework.enums.BusinessExceptionEnum.*;
//import static com.snowflake.proxy.constants.CommonConst.CACHE_SPLIT;
//import static com.snowflake.proxy.constants.CommonConst.HYPHEN;
//
//@Component
//public class OddsCacheOperator {
//
//    private final String NODE_VERSION_KEY = "NODE_V";
//
//    private final String ODDS_KEY = "LOTTERY_ODDS_KEY";
//
//    private final String AWARDS_KEY = "LOTTERY_AWARDS_KEY";
//
//    @Autowired
//    private FastOddsWeaverImpl fastOddsWeaver;
//
//    @Autowired
//    private FastAwardsWeaverImpl fastAwardsWeaver;
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Autowired
//    @Qualifier("jacksonRedisson")
//    private RedissonClient jacksonRedissonClient;
//
//
//    /**
//     * 自增目标节点版本好
//     * @param targetAgentId 目标代理节点
//     */
//    public void incrementProxyIdVersion(String targetAgentId) {
//        if (StringUtils.isBlank(targetAgentId)) {
//            throw new BusinessException(TARGET_AGENT_NOT_EXISTS).
//                    desc("目标代理ID不存在，无法修改节点版本号，请检查数据");
//        }
//        // 目标节点版本号原子自增，目的是为了使子孙节点的组合版本立即失效
//        String versionKey = buildVersionKeyWithProxy(targetAgentId);
//        redisTemplate.opsForValue().increment(versionKey);
//    }
//
//
//    /**
//     * 获取代理彩种维度的赔率缓存
//     * @param targetAgent
//     * @param merchantId
//     * @param lotteryCode
//     * @return
//     */
//    public List<FastLotteryOdds> getAgentOddsCacheByLottery(Agent targetAgent, Long merchantId, String lotteryCode) {
//        if (Objects.isNull(targetAgent) || Objects.isNull(merchantId) || StringUtils.isBlank(lotteryCode)) {
//            return Collections.emptyList();
//        }
//        String key = buildAgentOddsCacheKeyByLottery(targetAgent, merchantId, lotteryCode);
//        RList<FastLotteryOdds> cacheOddsHolder = jacksonRedissonClient.getList(key);
//        List<FastLotteryOdds> result;
//        if (cacheOddsHolder.isEmpty()) {
//            // 缓存内容为空，从DB加载
//            Long proxyId = targetAgent.getId();
//            WeaverContext context = WeaverContext.generate().setTargetNodeId(proxyId).setProxyIds(proxyId).setMerchantIds(merchantId).setLotteryCodes(lotteryCode);
//            result = FastOddsAwards.FO.findViewOdds(fastOddsWeaver, context);
//            if (!CollectionUtils.isEmpty(result)) {
//                cacheOddsHolder.addAll(result);
//                // 设置30分钟后过期
//                cacheOddsHolder.expire(Duration.ofMinutes(30));
//            }
//        } else {
//            result = cacheOddsHolder.readAll();
//        }
//        return result;
//    }
//
//
//    /**
//     * 构建赔率的缓存key
//     * @param targetAgent
//     * @param merchantId
//     * @param lotteryCode
//     * @return
//     */
//    private String buildAgentOddsCacheKeyByLottery(Agent targetAgent, Long merchantId, String lotteryCode) {
//        if (Objects.isNull(targetAgent)) {
//            throw new BusinessException(TARGET_AGENT_NOT_EXISTS).
//                    desc("目标代理不存在，无法组合赔率缓存键，请检查数据");
//        }
//        Long agentId = targetAgent.getId();
//        if (Objects.isNull(agentId)) {
//            throw new BusinessException(TARGET_AGENT_NOT_EXISTS).
//                    desc("目标代理ID不存在，无法组合赔率缓存键，请检查数据");
//        }
//        // 获取目标代理祖先链路，/1/1024/格式，包含目标代理自己
//        String targetAncestorChainWithSelf = targetAgent.getPath();
//        if (StringUtils.isBlank(targetAncestorChainWithSelf)) {
//            throw new BusinessException(TARGET_AGENT_ERROR_PATH).desc(String.format("目标代理：%s 的path字段为空，无法组合赔率缓存键，请检查数据", agentId));
//        }
//        String[] ancestorChainsWithSelf = targetAncestorChainWithSelf.split("/+");
//        List<String> ancestorAgentIdsWithSelf = Arrays.stream(ancestorChainsWithSelf).filter(StringUtils::isNotBlank).toList();
//
//        if (CollectionUtils.isEmpty(ancestorAgentIdsWithSelf)) {
//            throw new BusinessException(AGENT_INFO_ERROR).desc(String.format("目标代理：%s 的path字段拆分的祖先链路为空，无法组合赔率缓存键，请检查数据", agentId));
//        }
//        List<String> versionKeys = ancestorAgentIdsWithSelf.stream().map(this::buildVersionKeyWithProxy).toList();
//        String version = buildNodesVersion(versionKeys);
//        return ODDS_KEY + CACHE_SPLIT + merchantId + HYPHEN + agentId + HYPHEN + lotteryCode + CACHE_SPLIT + version;
//    }
//
//    /**
//     * 根据节点各自的版本号组合查询版本号链
//     * @param nodes 节点id集合，从上级到下级有序
//     * @return 有序节点集合在缓存中各自对应的version组合而成的版本key
//     */
//    private String buildNodesVersion(List<String> nodes) {
//        List<String> versions = redisTemplate.opsForValue().multiGet(nodes);
//        if (CollectionUtils.isEmpty(versions)) {
//            return "1";
//        }
//        return versions.stream().map(v -> {
//            if (StringUtils.isBlank(v)) {
//                return "1";
//            }
//            return v;
//        }).collect(Collectors.joining(HYPHEN));
//
//    }
//
//
//    /**
//     * 构建节点版本key
//     * @param proxyId
//     * @return
//     */
//    private String buildVersionKeyWithProxy(String proxyId) {
//        return NODE_VERSION_KEY + CACHE_SPLIT + proxyId;
//    }
//
//}
