package zeh.btp.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * kyc 对应的状态枚举
 */
public enum KYCStatusEnum {

    DISTRIBUTED(0, "distributed"),
    APPROVED(1, "approved"),
    REJECTED2(3, "rejected"),
    REJECTED(2, "rejected");

    private int kycStatus;
    private String kycStatusString;

    public static final String KYC_PBC_NONE = "-1";
    public static final String KYC_STATUS_DISTRIBUTE = "0";
    public static final String KYC_STATUS_APPROVED = "1";
    public static final String KYC_STATUS_REJECTED = "3";

    KYCStatusEnum(int kycStatus, String kycStatusString) {
        this.kycStatus = kycStatus;
        this.kycStatusString = kycStatusString;
    }

    public static KYCStatusEnum getKYCEnumByStaus(Integer status) {
        KYCStatusEnum[] kycEnums = KYCStatusEnum.values();
        for (KYCStatusEnum kycEnum : kycEnums) {
            if (kycEnum.getKycStatus() == status) {
                return kycEnum;
            }
        }
        return null;
    }

    public static KYCStatusEnum getKYCStatusByStr(String statusStr) {
        KYCStatusEnum[] kycEnums = KYCStatusEnum.values();
        for (KYCStatusEnum kycEnum : kycEnums) {
            if (StringUtils.equals(kycEnum.getKycStatusString(), statusStr)) {
                return kycEnum;
            }
        }
        return null;
    }

    public int getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(int kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getKycStatusString() {
        return kycStatusString;
    }

    public void setKycStatusString(String kycStatusString) {
        this.kycStatusString = kycStatusString;
    }
}
