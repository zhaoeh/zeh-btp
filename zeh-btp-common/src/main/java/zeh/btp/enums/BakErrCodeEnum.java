package zeh.btp.enums;

/**
 * 兜底异常码：当业务抛出的异常无法获取异常码时，使用此异常码
 */
public enum BakErrCodeEnum implements ResultCode {

    PHONE_NUMBER_BLACKLIST_NOT_EXIST(Boolean.FALSE, "400100", "phone number blacklist not exist!"),
    PHONE_NUMBER_BLACKLIST_ID_NOT_EXIST(Boolean.FALSE, "400100", "phone number blacklist id not exist!"),
    PHONE_NUMBER_BLACKLIST_EXIST(Boolean.FALSE, "400101", "phone number blacklist exist!"),

    ID_OR_PHONE_NUMBER_ONLY_ONE(Boolean.FALSE, "400110", "Only one value can be passed in for ID and mobile phone number at the same time."),
    UPDATE_HISTORY_TOO_LONG_ERROR(Boolean.FALSE, "400200", "update phone number blacklist history too long Error."),
    PRODUCT_CONSTANTS_NOT_EXISTS(Boolean.FALSE, "400201", "product constant does not exists."),
    ;


    private Boolean success;
    private String code;
    private String message;

    BakErrCodeEnum(Boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public Boolean getSuccess(Object... actualParams) {
        return success;
    }

    @Override
    public String getCode(Object... actualParams) {
        return code;
    }

    @Override
    public String getMessage(Object... actualParams) {
        return message;
    }
}
