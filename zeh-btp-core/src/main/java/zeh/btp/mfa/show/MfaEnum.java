package zeh.btp.mfa.show;

/**
 * @description: mfa status enum
 * @author: ErHu.Zhao
 * @create: 2024-05-31
 **/
public enum MfaEnum {

    MFA_FAILED(3000, "mfa failed"),
    QR_CODE(3001, "generate qr code success"),
    CONFIRM_BINDING(3002, "confirm binding success"),
    ALREADY_BINDING(3003, "already binding success");

    private Integer mfaStatus;
    private String mfaDesc;


    MfaEnum(Integer mfaStatus, String mfaDesc) {
        this.mfaStatus = mfaStatus;
        this.mfaDesc = mfaDesc;
    }

    public Integer getMfaStatus() {
        return mfaStatus;
    }

    public String getMfaDesc() {
        return mfaDesc;
    }
}
