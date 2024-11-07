package ft.btp.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * pbc的禁用状态 枚举类
 *
 * @author Heng.zhang
 */
@AllArgsConstructor
@Getter
public enum PbcBannedEnum {
    //禁用状态（0解除；1禁用）
    RELEASE(0, "release"),
    DISABLE(1, "disable"),
    ;
    private final int id;
    private final String name;

    /**
     * 获取所有的来源
     */
    public static List<PbcBanned> getAllPbcSource() {
        return Arrays.stream(PbcBannedEnum.values()).map(x -> new PbcBannedEnum.PbcBanned(x.id, x.name)).toList();
    }

    /**
     * 渠道返回对象
     */
    @AllArgsConstructor
    @Data
    public static class PbcBanned implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int id;
        private final String name;
    }
}
