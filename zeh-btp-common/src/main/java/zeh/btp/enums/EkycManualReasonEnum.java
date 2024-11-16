package zeh.btp.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 转人工原因枚举
 *
 * @author Heng.zhang
 */
@AllArgsConstructor
@Getter
public enum EkycManualReasonEnum {
    //0:其他原因 1:账号重复使用证件信息 2:识别信息修改 3:客服提交
    OTHER(0, "other"),
    ID_REPEATED(1, "id repeated"),
    MESSAGE_UPDATE(2, "information modification"),
    CS_SUBMISSION(3, "manual submit"),
    SAME_FACE(4, "Same face"),
    ;
    private final int id;
    private final String name;

    /**
     * 获取所有的渠道对象
     */
    public static List<EkycManualReason> getAllManualReason() {
        return Arrays.stream(EkycManualReasonEnum.values()).map(x -> new EkycManualReasonEnum.EkycManualReason(x.id, x.name)).toList();
    }

    /**
     * 渠道返回对象
     */
    @AllArgsConstructor
    @Data
    public static class EkycManualReason implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int id;
        private final String name;
    }
}
