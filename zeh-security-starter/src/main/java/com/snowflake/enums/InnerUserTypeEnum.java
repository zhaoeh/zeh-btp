package com.snowflake.enums;

import cn.hutool.core.util.ArrayUtil;
import com.snowflake.validation.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum InnerUserTypeEnum implements IntArrayValuable {

    MEMBER(1, "会员"),
    ADMIN(2, "管理员");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(InnerUserTypeEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    public static InnerUserTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue().equals(value), InnerUserTypeEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
