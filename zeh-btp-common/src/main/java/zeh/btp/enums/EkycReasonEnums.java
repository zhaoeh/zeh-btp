package zeh.btp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EkycReasonEnums {

    // 提交完善扩展信息审核通过提示
    SUCCESS("Success", "update extend success", " update extend success", " update extend success"),
    // 提交完善扩展信息审核拒绝提示
    EXTEND_REJECT_REASON("update extend reject", "ID or face authentication failed", " ID or face authentication failed", " ID or face authentication failed"),
    ;
    /**
     * 类型
     */
    private final String reasonType;

    /**
     * 提示信息*
     */
    private final String displayMsg;

    /**
     * push内容
     */
    private final String pushMsg;
    /**
     * sms内容
     */
    private final String smsMsg;

}
