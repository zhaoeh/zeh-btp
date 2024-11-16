package zeh.btp.mfa.enums;

public enum MfaErrorEnum {
    UNAUTHORIZED(401, "Unauthorized, illegal access."),
    MFA_NOT_NULL(20001, "mfaContext cannot be null."),
    MFA_SECRET_KEY_NOT_BLANK(20002, "supplyMfaSecret method in MfaProcess cannot return blank when confirm binding."),
    MFA_SECRET_KEY_NOT_BLANK_VALIDATE(20003, "supplyMfaSecret method in MfaProcess cannot return blank when validate mfa code."),
    NOT_SUPPORT_NON_WEB(20004, "@MfaAuth cannot be used in non-web environments."),
    AUTH_HELPER_NOT_NULL(20005, "googleAuthHelper cannot be null"),
    AUTH_FLAG_NOT_BLANK(20006, "authFlag method in MfaProcess cannot return blank when generate mfa qrCode."),
    AUTH_USER_NOT_BLANK(20007, "authUser method in MfaProcess cannot return blank when generate mfa qrCode."),
    CHECK_FAILED(20008, "check mfa code failed."),
    GENERATE_QR_CODE_FAILED(20009, "generate qr code failed."),
    QR_CODE_IS_BLANK(20010, "generate qr code is blank."),
    CONFIRM_BINDING_FAILED(20011, "confirm binding failed.");


    MfaErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
