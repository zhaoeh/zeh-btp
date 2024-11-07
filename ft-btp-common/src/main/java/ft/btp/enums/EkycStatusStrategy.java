package ft.btp.enums;

import ft.btp.pojo.EkycQueryStatusRequest;
import org.apache.commons.lang3.BooleanUtils;

/**
 * @description: ekyc状态策略枚举
 * @author: ErHu.Zhao
 * @create: 2024-10-08
 **/
public enum EkycStatusStrategy {
    STRATEGY_ONE("STRATEGY1", ""),
    STRATEGY_TWO("STRATEGY2", ""),
    STRATEGY_THREE("STRATEGY3", ""),
    STRATEGY_FOUR("STRATEGY4", ""),
    ;

    private String flag;

    private String desc;

    EkycStatusStrategy(String flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据条件获取策略
     *
     * @param query 条件
     * @return 策略枚举
     */
    public static EkycStatusStrategy obtainStrategy(EkycQueryStatusRequest query) {
        if (BooleanUtils.isFalse(query.getNeedOldKycStatus()) && BooleanUtils.isFalse(query.getNoNeedEx())) {
            return STRATEGY_ONE;
        }
        if (BooleanUtils.isFalse(query.getNeedOldKycStatus()) && BooleanUtils.isTrue(query.getNoNeedEx())) {
            return STRATEGY_TWO;
        }
        if (BooleanUtils.isTrue(query.getNeedOldKycStatus()) && BooleanUtils.isFalse(query.getNoNeedEx())) {
            return STRATEGY_THREE;
        }
        if (BooleanUtils.isTrue(query.getNeedOldKycStatus()) && BooleanUtils.isTrue(query.getNoNeedEx())) {
            return STRATEGY_FOUR;
        }
        return null;
    }
}
