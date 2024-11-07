package jp.co.futech.module.insight.enums.anomaly;

import java.util.Arrays;

/**
 * @description: dimension params enums
 * @author: ErHu.Zhao
 * @create: 2024-06-18
 **/
public enum DimensionParamEnums {

    SITE_ID("site_id", "site id 标志"), DIMENSION_ID("dimension_id", "dimension 标志");

    DimensionParamEnums(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    private String name;

    private String desc;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean matches(String name) {
        return Arrays.stream(DimensionParamEnums.values()).anyMatch(dimensionEnums -> dimensionEnums.getName().equals(name));
    }
}
