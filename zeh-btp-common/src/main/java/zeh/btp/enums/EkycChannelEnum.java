package zeh.btp.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author: yueds
 * @desc: ekyc渠道信息
 * @date: 2024/10/8 14:44
 */
@AllArgsConstructor
@Getter
public enum EkycChannelEnum {
    // 3:GLIFE,4:GPO,5:LAZADA,6:MAYA,7:PERYAGAME,98:人工,99WEBSITE
    GLIFE("3", "GLIFE"),
    GPO("4", "GPO"),
    LAZADA("5", "LAZADA"),
    MAYA("6", "MAYA"),
    PERYAGAME("7", "PERYAGAME"),
    MANUAL("98", "MANUAL"),
    WEBSITE("99", "WEBSITE");

    private final String channelId;

    private final String channelName;

    /**
     * 获取所有的渠道对象
     */
    public static List<EkycChannel> getAllChannel() {
        return Arrays.stream(EkycChannelEnum.values()).map(x -> new EkycChannelEnum.EkycChannel(x.channelId, x.channelName)).toList();
    }

    /**
     * 渠道返回对象
     */
    @AllArgsConstructor
    @Data
    public static class EkycChannel implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String id;
        private final String channel;
    }
}
