package jp.co.futech.module.insight.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: 风控查询条件配置
 * @author: ErHu.Zhao
 * @create: 2024-06-26
 **/
@ConfigurationProperties(prefix = "risk.conditions")
@Data
@Configuration
@RefreshScope
public class RiskQueryConditionsConfig {

    private List<RiskQueryCondition> primaryRiskTypes;

    private List<RiskQueryCondition> secondaryRiskTypes;

    private List<RiskQueryCondition> timeRanges;

    private List<RiskQueryCondition> riskLevels;

    private static Map<String, RiskQueryCondition> primaryRiskTypeMaps;

    private static Map<String, RiskQueryCondition> secondaryRiskTypeMaps;

    private static Map<String, RiskQueryCondition> timeRangeMaps;

    private static Map<String, RiskQueryCondition> riskLevelMaps;

    @PostConstruct
    public void check() {
        Assert.notEmpty(primaryRiskTypes, "primary_risk_types cannot be empty in config");
        Assert.notEmpty(secondaryRiskTypes, "secondary_risk_types cannot be empty in config");
        Assert.notEmpty(timeRanges, "time_ranges cannot be empty in config");
        Assert.notEmpty(riskLevels, "risk_level cannot be empty in config");
        initMaps();
    }

    private void initMaps() {
        primaryRiskTypeMaps = primaryRiskTypes.stream().collect(Collectors.toMap(RiskQueryCondition::getNum, Function.identity()));
        secondaryRiskTypeMaps = secondaryRiskTypes.stream().collect(Collectors.toMap(RiskQueryCondition::getNum, Function.identity()));
        timeRangeMaps = timeRanges.stream().collect(Collectors.toMap(RiskQueryCondition::getNum, Function.identity()));
        riskLevelMaps = riskLevels.stream().collect(Collectors.toMap(RiskQueryCondition::getNum, Function.identity()));
    }

    public static String findPrimaryRiskType(String num) {
        if (StringUtils.isBlank(num)) {
            return null;
        }
        return primaryRiskTypeMaps.containsKey(num) ? primaryRiskTypeMaps.get(num).getValue() : null;
    }

    public static String findSecondaryRiskType(String num) {
        if (StringUtils.isBlank(num)) {
            return null;
        }
        return secondaryRiskTypeMaps.containsKey(num) ? secondaryRiskTypeMaps.get(num).getValue() : null;
    }

    public static String findTimeRangeMaps(String num) {
        if (StringUtils.isBlank(num)) {
            return null;
        }
        return secondaryRiskTypeMaps.containsKey(num) ? timeRangeMaps.get(num).getValue() : null;
    }

}
