package com.snowflake.cache.view;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class LotteryPlayViewHelper {

    /**
     * 构建玩法维度的节点版本值
     * @param nodeVersions 节点版本链
     * @return 节点版本链按照传入顺序构建拼接的版本value
     */
    public static String buildLotteryPlayNodeVersion(List<String> nodeVersions) {
        if (CollectionUtils.isEmpty(nodeVersions)) {
            return null;
        }
        return nodeVersions.stream().filter(StringUtils::isNotBlank).collect(Collectors.joining("-"));
    }

}
