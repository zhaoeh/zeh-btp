package ft.btp.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * pagcor的来源 枚举类
 *
 * @author Heng.zhang
 */
@AllArgsConstructor
@Getter
public enum PbcSourceEnum {
    //pogcor禁用来源:GOVT:政府官员，GEL：从业者；Banned：自主申请
    GOVT(0, "GOVT"),
    GEL(1, "GEL"),
    BANNED(2, "Banned"),
    ;
    private final int id;
    private final String name;

    /**
     * 获取所有的来源
     */
    public static List<PbcSource> getAllPbcSource() {
        return Arrays.stream(PbcSourceEnum.values()).map(x -> new PbcSourceEnum.PbcSource(x.id, x.name)).toList();
    }

    /**
     * 渠道返回对象
     */
    @AllArgsConstructor
    @Data
    public static class PbcSource implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int id;
        private final String name;
    }
}
