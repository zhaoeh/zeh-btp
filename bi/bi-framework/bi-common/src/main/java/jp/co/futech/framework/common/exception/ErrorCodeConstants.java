package jp.co.futech.framework.common.exception;

/**
 * @description: 通用error code
 * @author: ErHu.Zhao
 * @create: 2024-07-11
 **/
public interface ErrorCodeConstants {

    // ========== 国际化 1-001-302-000 ==========
    ErrorCode LOCAL_INVALID = new ErrorCode(1_001_302_000, "当前locale非法", "{200001100}");
}
