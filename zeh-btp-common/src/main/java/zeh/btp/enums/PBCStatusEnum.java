package zeh.btp.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * pbc 对应的状态枚举
 */
public enum PBCStatusEnum {

    DISTRIBUTED(0, "distributed"),
    APPROVED(1, "approved"),
    REJECTED2(3, "rejected"),
    REJECTED(2, "rejected");

    private int pbcStatus;
    private String pbcStatusString;

    public static final int PBC_STATUS_DISTRIBUTE = 0;
    public static final int PBC_STATUS_APPROVED = 1;
    public static final int PBC_STATUS_REJECTED = 3;

    PBCStatusEnum(int pbcStatus, String pbcStatusString) {
        this.pbcStatus = pbcStatus;
        this.pbcStatusString = pbcStatusString;
    }

    public static PBCStatusEnum getPbcEnumByStatus(Integer status) {
        PBCStatusEnum[] pbcEnums = PBCStatusEnum.values();
        for (PBCStatusEnum pbcEnum : pbcEnums) {
            if (pbcEnum.getPbcStatus() == status) {
                return pbcEnum;
            }
        }
        return null;
    }

    public static PBCStatusEnum getPbcEnumByStr(String statusStr) {
        PBCStatusEnum[] pbcEnums = PBCStatusEnum.values();
        for (PBCStatusEnum pbcEnum : pbcEnums) {
            if (StringUtils.equals(pbcEnum.getPbcStatusString(), statusStr)) {
                return pbcEnum;
            }
        }
        return null;
    }


    public int getPbcStatus() {
        return pbcStatus;
    }

    public void setPbcStatus(int pbcStatus) {
        this.pbcStatus = pbcStatus;
    }

    public String getPbcStatusString() {
        return pbcStatusString;
    }

    public void setPbcStatusString(String pbcStatusString) {
        this.pbcStatusString = pbcStatusString;
    }
}
