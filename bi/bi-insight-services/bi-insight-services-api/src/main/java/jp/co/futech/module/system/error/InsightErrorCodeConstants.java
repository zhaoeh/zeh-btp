package jp.co.futech.module.system.error;

import jp.co.futech.framework.common.exception.ErrorCode;

/**
 * Insight 错误码枚举类
 * <p>
 * Insight 系统，使用 1-002-050-000 段
 */
public interface InsightErrorCodeConstants {

    /**
     * insight anomaly模块
     */
    ErrorCode DIMENSION_SIZE_NOT_EQUALS_SUB_ITEMS = new ErrorCode(1_002_050_000, "dimension size 与 sub_items size 不相等", "{200000055}");

    ErrorCode DIMENSION_PARAMS_WRONG = new ErrorCode(1_002_050_001, "dimension param 错误", "{200000056}");

    ErrorCode SQL_NAME_BEAN_NOT_BE_NULL = new ErrorCode(1_002_050_001, "SqlNameBean 不能是 null,请检查 index 参数和 anomaly.indexes.index_map 配置项", "{200000057}");

    /**
     * insight risk模块
     */
    ErrorCode DATE_PARAMS_WRONG = new ErrorCode(1_002_060_000, "startDate or endDate param 错误", "{200000058}");
}
