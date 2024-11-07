package ft.btp.utils;

import org.apache.commons.lang3.RandomUtils;

public class CacheUtil {

    private static Integer workId = RandomUtils.nextInt(0, 31);

    private static Integer dateCenterId = RandomUtils.nextInt(0, 31);

    public static Integer getWorkId() {
        return workId;
    }

    public static Integer getDateCenterId() {
        return dateCenterId;
    }


}
