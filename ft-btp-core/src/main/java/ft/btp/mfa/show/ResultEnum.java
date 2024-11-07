package ft.btp.mfa.show;

/**
 * 返回结果枚举
 *
 * @Author TJSAlex
 * @Date 2023/5/18 15:42
 * @Version 1.0
 **/
public enum ResultEnum {

    // 基本异常枚举
    SUCCESS(Boolean.TRUE, 200, "Request Success"),
    BAD_REQUEST(Boolean.FALSE, 400, "Parameter Error"),
    UNAUTHORIZED(Boolean.FALSE, 401, "Unauthorized, illegal access"),
    FORBIDDEN(Boolean.FALSE, 403, "No permission to access"),
    NOT_EXIST(Boolean.FALSE, 404, "Access address does not exist"),
    SYSTEM_BUSY(Boolean.FALSE, 405, "The system is busy now. Please try again."),
    PAGENUM_ERROR(Boolean.FALSE, 406, "There are not enough pages!"),
    DATE_RANGE_ERROR(Boolean.FALSE, 407, "The startDate or endDate input error!"),
    FAIL(Boolean.FALSE, 500, "Business exception, request failed");

    private Boolean success;
    private Integer code;
    private Integer mfaStatus;
    private String message;

    ResultEnum(Boolean success, Integer code, Integer mfaStatus, String message) {
        this.success = success;
        this.code = code;
        this.mfaStatus = mfaStatus;
        this.message = message;
    }

    ResultEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
