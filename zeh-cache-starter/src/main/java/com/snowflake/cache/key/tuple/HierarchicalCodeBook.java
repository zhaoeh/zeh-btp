package com.snowflake.cache.key.tuple;

import com.snowflake.cache.view.play.AllPlayView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 层级编码字典：
 * - 叶子：fullPath -> leafId(1..N)
 * - 前缀：prefixPath(depth=1..4/5) -> LongRange(连续区间)
 */
public final class HierarchicalCodeBook {

    private final Map<String, LotteryPlayKeyTuple> leafIdMap;           // fullPath -> leafId
    private final Map<String, String> prefixRangeMap; // prefixPath(depth 1..5) -> range
    private final long totalLeafCount;

    private HierarchicalCodeBook(Map<String, LotteryPlayKeyTuple> leafIdMap,
                                 Map<String, String> prefixRangeMap,
                                 long totalLeafCount) {
        this.leafIdMap = leafIdMap;
        this.prefixRangeMap = prefixRangeMap;
        this.totalLeafCount = totalLeafCount;
    }

    public long totalLeafCount() {
        return totalLeafCount;
    }

    /**
     * 查询叶子编码（五元组唯一）
     * @param k 五元组key
     * @return 五元组key对应的描述实例
     */
    public String leafId(LotteryPlayKeyTuple k) {
        LotteryPlayKeyTuple data = leafIdMap.get(LotteryPlayKeyTupleHelper.toFullKey(k));
        return Objects.isNull(data) ? "" : data.getId();
    }

    /**
     * 查询任意前缀对应的连续区间
     * @param merchantId
     * @param lotteryCode
     * @param playTypeCode
     * @param playCode
     * @param handicapCode
     * @param depth
     * @return
     */
    public String rangeOfPrefix(Long merchantId, String lotteryCode,
                                String playTypeCode, String playCode,
                                String handicapCode, int depth) {
        String key = prefixPath(merchantId, lotteryCode, playTypeCode, playCode, handicapCode, depth);
        return prefixRangeMap.get(key);
    }


    private static String prefixPath(Long merchantId, String lotteryCode,
                                     String playTypeCode, String playCode,
                                     String handicapCode, int depth) {
        List<String> parts = new ArrayList<>(depth);
        parts.add(String.valueOf(Objects.requireNonNull(merchantId)));
        if (depth >= 2) parts.add(Objects.requireNonNull(lotteryCode));
        if (depth >= 3) parts.add(Objects.requireNonNull(playTypeCode));
        if (depth >= 4) parts.add(Objects.requireNonNull(playCode));
        if (depth >= 5) parts.add(Objects.requireNonNull(handicapCode));
        return String.join(":", parts);
    }

    /**
     * 构建器：按五元组字典序分配 1..N，顺带统计各前缀区间
     * @param data
     * @return
     */
    public static AllPlayView build(List<LotteryPlayKeyTuple> data) {
        Objects.requireNonNull(data);
        Map<String, LotteryPlayKeyTuple> leafIdMap = new HashMap<>(data.size());
        Map<String, String> prefixRangeMap = new HashMap<>(data.size()); // 粗估
        KeyTupleIndex[] index = KeyTupleDimension.SORTED_TUPLE3.getIndices();
        String prefix = Arrays.stream(index).map(i -> String.valueOf(i.getCode())).collect(Collectors.joining(":"));
        long id = 0;
        for (LotteryPlayKeyTuple k : data) {
            long leafId = ++id;
            if (!Objects.equals(Objects.toString(leafId), k.getId())) {
                throw new IllegalArgumentException("传入的LotteryPlayKeyTuple id必须从1开始自增排序");
            }
            leafIdMap.put(LotteryPlayKeyTupleHelper.toFullKey(k), k);
            String prefixKey = LotteryPlayKeyTupleHelper.toKey(k, index);
            if (!prefixRangeMap.containsKey(prefixKey)) {
                // 只存第一次：三元组固定字段顺序组合前缀，value为对应首次出现的五元组id编号，加上对应三元组索引顺序组合，构成唯一的三元组映射编号
                prefixRangeMap.put(prefixKey, String.join(":", k.getId(), prefix));
            }
        }
        return new AllPlayView(
                Collections.unmodifiableMap(leafIdMap),
                Collections.unmodifiableMap(prefixRangeMap)
        );
    }
}
