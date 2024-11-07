package ft.btp.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author: sanji
 * @desc: ekyc/ekyc提案状态枚举类
 * @date: 2024/10/7 14:44
 */
@AllArgsConstructor
@Getter
public enum EkycStatusEnum {
    //KYC提案审批状态 0初始化，1待完善信息，2 人工审批，3通过，5 自动拒绝，6手动拒绝, 7失效
    //EKYC状态 -1待提交,0待审核，1通过，2自动拒绝，3手动拒绝,4待查询zoloz结果
    INIT_0(0, -1, "initialization", "wait submit"),
    INIT_1(1, 4, "improve information", "wait query zoloz result"),
    PENDING(2, 0, "manual approval", "wait approval"),
    APPROVAL(3, 1, "approval", "approval"),
    REJECTED_AUTO(5, 2, "auto reject", "auto reject"),
    REJECTED_MANUAL(6, 3, "manual reject", "manual reject"),
    FAILURE(7, -1, "invalid", "wait submit");

    private final int eKycReqStatus;
    private final int ekycStatus;
    private final String eKycReqStatusRemark;
    private final String ekycStatusRemark;

    /**
     * 根据request表status 映射 ekyc表status
     *
     * @param eKycReqStatus
     * @return
     */
    public static EkycStatusEnum findEnumByStatus(int eKycReqStatus) {
        for (EkycStatusEnum ekycStatusEnum : EkycStatusEnum.values()) {
            if (ekycStatusEnum.getEKycReqStatus() == eKycReqStatus) {
                return ekycStatusEnum;
            }
        }
        return null;
    }

    /**
     * 获取ekyc状态
     */
    public static List<EkycStatus> getAllEkycStatus() {
        return Arrays.stream(EkycStatusEnum.values()).map(x -> new EkycStatus(x.ekycStatus, x.ekycStatusRemark)).distinct().toList();
    }

    /**
     * 获取ekyc提案状态
     */
    public static List<EkycStatus> getAllEkycReqStatus() {
        return Arrays.stream(EkycStatusEnum.values()).map(x -> new EkycStatus(x.eKycReqStatus, x.eKycReqStatusRemark)).toList();
    }

    /**
     * ekyc/ekyc提案状态返回对象
     */
    @AllArgsConstructor
    @Data
    public static class EkycStatus implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int id;
        private final String status;
    }
}
