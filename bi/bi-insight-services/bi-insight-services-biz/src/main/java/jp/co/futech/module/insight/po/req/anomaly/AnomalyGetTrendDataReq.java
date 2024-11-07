package jp.co.futech.module.insight.po.req.anomaly;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jp.co.futech.framework.common.exception.util.ServiceExceptionUtil;
import jp.co.futech.module.insight.constant.InsightConstants;
import jp.co.futech.module.insight.enums.anomaly.DimensionEnums;
import jp.co.futech.module.insight.enums.anomaly.DimensionParamEnums;
import jp.co.futech.module.insight.utils.AppendUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jp.co.futech.module.system.error.InsightErrorCodeConstants.DIMENSION_PARAMS_WRONG;
import static jp.co.futech.module.system.error.InsightErrorCodeConstants.DIMENSION_SIZE_NOT_EQUALS_SUB_ITEMS;

/**
 * @description: get trend data request
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
public class AnomalyGetTrendDataReq {

    @JsonProperty("metric")
    private String metric;

    @NotBlank(message = "index {000000004}")
    @JsonProperty("index")
    private String index;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    /**
     * 动态字段
     */
    private String dynamicColumnName;

    @JsonProperty("dimension")
    private List<String> dimension;

    @JsonProperty("sub_items")
    private List<String> subItems;

    @JsonProperty("anomal_dt")
    private String anomalDt;

    @JsonProperty("site")
    private String site;

    private String siteId;

    @JsonProperty("dimension_str")
    private String dimensionStr;

    @JsonProperty("sub_items_str")
    private String subItemsStr;

    private String platform;

    private String gamekind;

    private Map<String, String> mapping;

    public AnomalyGetTrendDataReq refactor() {
        return Optional.ofNullable(check()).map(AnomalyGetTrendDataReq::init).orElse(this);
    }

    private AnomalyGetTrendDataReq check() {
        if (CollUtil.isEmpty(subItems)) {
            return null;
        }
        if (CollUtil.isEmpty(dimension)) {
            return null;
        }
        ServiceExceptionUtil.isTrue(dimension.size() == subItems.size(), DIMENSION_SIZE_NOT_EQUALS_SUB_ITEMS);
        boolean checkResult = dimension.stream().allMatch(e -> DimensionParamEnums.matches(e));
        ServiceExceptionUtil.isTrue(checkResult, DIMENSION_PARAMS_WRONG);
        return this;
    }

    private AnomalyGetTrendDataReq init() {
        rebuildDimension();
        mapping = IntStream.range(0, dimension.size()).boxed().collect(Collectors.toMap(dimension::get, subItems::get));
        if (CollUtil.isNotEmpty(mapping)) {
            siteId = convert(mapping.get(DimensionEnums.SITE_ID.getName()));
            platform = convert(mapping.get(DimensionEnums.PLATFORM.getName()));
            gamekind = convert(mapping.get(DimensionEnums.GAMEKIND.getName()));
        }
        return this;
    }

    /**
     * 重建dimension
     */
    private void rebuildDimension() {
        if (CollUtil.isEmpty(subItems)) {
            return;
        }
        if (subItems.size() == 1) {
            dimension = List.of(DimensionEnums.SITE_ID.getName());
            return;
        }
        String subItemOfIndex1 = subItems.get(1);
        if (subItemOfIndex1.endsWith(InsightConstants.SUB_ITEMS_PLAY_SUFFIX)) {
            dimension = List.of(DimensionEnums.SITE_ID.getName(), DimensionEnums.GAMEKIND.getName());
            return;
        }
        if (subItemOfIndex1.endsWith(InsightConstants.SUB_ITEMS_PLAT_SUFFIX)) {
            dimension = List.of(DimensionEnums.SITE_ID.getName(), DimensionEnums.PLATFORM.getName());
            return;
        }
    }

    private String convert(String source) {
        if (Objects.isNull(source)) {
            return "";
        }
        return source;
    }

    /**
     * platform 和  gamekind 根据逻辑转换
     *
     * @return
     */
    private AnomalyGetTrendDataReq convert() {
        if (StringUtils.isNotBlank(platform)) {
            gamekind = "";
            return this;
        }
        if (StringUtils.isNotBlank(gamekind)) {
            platform = "";
            return this;
        }
        if (StringUtils.isBlank(platform) && StringUtils.isBlank(gamekind)) {
            platform = "";
            gamekind = "";
        }
        return this;
    }

    public AnomalyGetTrendDataReq formatDimensionStr$() {
        if (StringUtils.isNotBlank(dimensionStr)) {
            return this;
        }
        if (CollUtil.isEmpty(dimension)) {
            return this;
        }
        dimensionStr = AppendUtils.appendListConvert("'[", dimension, "]'");
        return this;
    }

    public AnomalyGetTrendDataReq formatDimensionStr() {
        if (StringUtils.isNotBlank(dimensionStr)) {
            return this;
        }
        if (CollUtil.isEmpty(dimension)) {
            return this;
        }
//        dimensionStr = JSONUtil.toJsonStr(dimension);
        dimensionStr = AppendUtils.appendListConvert("[", dimension, "]");
        return this;
    }

    public AnomalyGetTrendDataReq formatSubItems$() {
        if (StringUtils.isNotBlank(subItemsStr)) {
            return this;
        }
        if (CollUtil.isEmpty(subItems)) {
            return this;
        }
//        subItemsStr = JSONUtil.toJsonStr(subItems);
        subItemsStr = AppendUtils.appendListConvert("'[", subItems, "]'");
        return this;
    }

    public AnomalyGetTrendDataReq formatSubItems() {
        if (StringUtils.isNotBlank(subItemsStr)) {
            return this;
        }
        if (CollUtil.isEmpty(subItems)) {
            return this;
        }
//        subItemsStr = JSONUtil.toJsonStr(subItems);
        subItemsStr = AppendUtils.appendListConvert("[", subItems, "]");
        return this;
    }

}
