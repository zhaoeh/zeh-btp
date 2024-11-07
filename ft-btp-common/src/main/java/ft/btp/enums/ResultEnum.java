package ft.btp.enums;

/**
 * @ClassName ResultEnum
 * @Description 返回结果枚举
 * @Author Erhu.Zhao
 * @Date 2023/10/18 15:42
 * @Version 1.0
 **/
public enum ResultEnum implements ResultCode {

    // 基本异常枚举
    SUCCESS(Boolean.TRUE, "200", "Request Success"),
    BAD_REQUEST(Boolean.FALSE, "400", "Parameter Error"),
    UNAUTHORIZED(Boolean.FALSE, "401", "Unauthorized, illegal access"),
    FORBIDDEN(Boolean.FALSE, "403", "No permission to access"),
    NOT_EXIST(Boolean.FALSE, "404", "Access address does not exist"),
    FAIL(Boolean.FALSE, "500", "Business exception, request failed"),

    UNEXPECTED_FAIL(Boolean.FALSE, "501", "UNEXPECTED_FAIL"),

    DATA_IS_NULL(Boolean.FALSE, "502", "DATA_IS_NULL"),
    OUT_SYSTEM_ERROR(Boolean.FALSE, "503", "OUT_SYSTEM_ERROR"),

    EXCEL_EXPORT_IO_ERROR(Boolean.FALSE, "504", "EXCEL_EXPORT_IO_ERROR"),
    ENCRYPT_DECRYPT_ERROR(Boolean.FALSE, "505", "encrypt/decrypt error"),

    FRAMEWORK_ERROR(Boolean.FALSE, "506", "Framework error"),

    WS_EXCEPTION(Boolean.FALSE, "507", "Call WS failed! Please check if the ws service is ok!"),
    CRON_EXCEPTION(Boolean.FALSE, "507", "Call RiskCron failed! Please check if the cron service is ok!"),
    UC_EXCEPTION(Boolean.FALSE, "507", "Call userCenter failed! Please check if the userCenter service is ok!"),
    GATEWAY_EXCEPTION(Boolean.FALSE, "507", "Call gateway api failed! Please check if the gateway api is ok!"),
    RISK_API_EXCEPTION(Boolean.FALSE, "508", "Call RiskControl-API failed! Please check if the riskApi service is ok!"),

    PHONE_NUMBER_BLACKLIST_NOT_EXIST(Boolean.FALSE, "400100", "phone number blacklist not exist!"),
    PHONE_NUMBER_BLACKLIST_ID_NOT_EXIST(Boolean.FALSE, "400101", "phone number blacklist id not exist!"),
    PHONE_NUMBER_BLACKLIST_EXIST(Boolean.FALSE, "400102", "phone number blacklist exist!"),
    OLD_PHONE_MD5_IS_NULL(Boolean.FALSE, "400103", "oldPhoneMd5 is null!"),
    /**
     * 风控标签接口相关错误代码*
     */
    RISK_LABEL_ID_IS_EMPTY(Boolean.FALSE, "400104", "risk label id is empty!"),
    PRODUCT_ID_IS_EMPTY(Boolean.FALSE, "400105", "product id is empty!"),
    LOGIN_NAME_IS_EMPTY(Boolean.FALSE, "400106", "login name is empty!"),

    ID_OR_PHONE_NUMBER_ONLY_ONE(Boolean.FALSE, "400110", "Only one value can be passed in for ID and phone md5 at the same time."),
    UPDATE_HISTORY_TOO_LONG_ERROR(Boolean.FALSE, "400200", "update phone number blacklist history too long Error."),


    RISK_API_RETURN_ERROR(Boolean.FALSE, "400300", "RiskControl-API response error!"),

    ACCOUNT_NOT_EXIST_ERROR(Boolean.FALSE, "400115", "account not exist Error"),
    SWITCH_READ_ERROR(Boolean.FALSE, "400211", "user switch read Error"),
    CUSTOMER_MODIFY_FAILED_ERROR(Boolean.FALSE, "400111", "customer modify failed Error"),
    PBCSTATUS_REMARK_NOT_MATCH(Boolean.FALSE, "400116", "pubStaus and  remark not match"),
    BIRTHDAY_EMPTY_ERROR(Boolean.FALSE, "400122", "birthday empty Error"),
    BIRTHDAY_INVALID_ERROR(Boolean.FALSE, "400123", "birthday invalid Error"),
    LAST_NAME_EMPTY_ERROR(Boolean.FALSE, "400121", "last name empty Error"),
    FIRST_NAME_EMPTY_ERROR(Boolean.FALSE, "400120", "first name empty Error"),
    LOGIN_NAME_EMPTY_ERROR(Boolean.FALSE, "400106", "login name empty Error"),
    LOGIN_NAME_NOT_EXIST_ERROR(Boolean.FALSE, "400107", "login name not exist Error"),
    LOGIN_NAME_BLACK_ERROR(Boolean.FALSE, "400114", "login name black error"),
    COMPLETE_PLAYER_INFORMATION_ERROR(Boolean.FALSE, "400112", "complete player information Error"),
    PWD_DECRYPT_ERROR(Boolean.FALSE, "400102", "^password decrypt Error"),
    DEPOSIT_TRANS_TRAIL_FORBID_ERROR(Boolean.FALSE, "400113", "deposit trans trail forbid error"),
    FACE_IMG_NOT_EXIST_ERROR(Boolean.FALSE, "400403", "face image not exist Error"),
    UPLOAD_TO_S3_ERROR(Boolean.FALSE, "400401", "upload image Error"), //上传到S3服务失败,
    ERROR_VALID_KYC_ID(Boolean.FALSE, "400205", "Dear User, this ID number is already registered. Please use a different one. For help, contact support. Thank you!"),

    ENCRYPTION_FAIL(Boolean.FALSE, "100005", "Sensitive info encryption fail"), //敏感信息加密失败!
    DECRYPTION_FAIL(Boolean.FALSE, "100006", "Sensitive info decryption fail!"), //敏感信息解密失败!

    READ_EXCEL_FAIL(Boolean.FALSE, "100007", "Read excel data fail!"),

    EKYC_USER_OVER_AGE_ERROR(Boolean.FALSE, "50001", "Ekyc user over age Error"),
    EKYC_REQUEST_NOT_EXIST(Boolean.FALSE, "50002", "Ekyc request not exist Error"),
    EKYC_REQUEST_INVALIDATED_STATUS(Boolean.FALSE, "50003", "Ekyc request status Error,can not update info"),
    EKYC_REQUEST_MODIFY_ERROR(Boolean.FALSE, "50004", "Ekyc request invalidated action Error"),
    EKYC_NOT_EXISTS_ERROR(Boolean.FALSE, "50005", "Ekyc not exist Error"),
    EKYC_USER_NOT_EXISTS_ERROR(Boolean.FALSE, "50006", "Ekyc user not exist in ws Error"),
    EKYC_STRATEGY_ERROR(Boolean.FALSE, "50007", "Ekyc not have validated strategy,please check EkycApprovalStrategy enum"),
    EKYC_UPDATE_WS_ERROR(Boolean.FALSE, "50008", "Ekyc update ws user info failure"),
    EKYC_PHONE_DECRYPT_ERROR(Boolean.FALSE, "50009", "Ekyc phone of ws decrypt error"),
    EKYC_REQUEST_CREATE_STATUS_ERROR(Boolean.FALSE, "50010", "This user currently has a proposal waiting for approval. Please do not create it again."),
    EKYC_UPDATE_WS_CUSTOMER_ERROR(Boolean.FALSE, "50011", "Failed to update the user's information in WS."),
    EKYC_QUERY_DATE_CANNOT_SPAN_DAYS_ERROR(Boolean.FALSE, "50012", "The query time cannot span days."),
    EKYC_REQUEST_LOGIN_NAME_IS_BLANK(Boolean.FALSE, "50013", "Ekyc request login name is blank."),
    EKYC_GET_WS_BANK_ERROR(Boolean.FALSE, "50014", "Ekyc get bank no from ws error."),
    EKYC_LOGIN_NAME_IS_BLANK(Boolean.FALSE, "50015", "Ekyc or kyc login name is blank."),
    KYC_NOT_EXISTS_ERROR(Boolean.FALSE, "50016", "Kyc not exist Error."),
    KYC_ONLY_ONE_NULL_ERROR(Boolean.FALSE, "50017", "Ekyc or kyc record has only one null error."),
    KYC_MOST_ONE_NULL_ERROR(Boolean.FALSE, "50018", "Ekyc or kyc record at most one null error."),
    ;

    private Boolean success;
    private String code;
    private String message;

    ResultEnum(Boolean success, String code, String message) {
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
