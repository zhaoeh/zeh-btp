package zeh.btp.utils;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统编号工具类
 */
@Slf4j
public class BillNoUtil {

    /**
     * 获取随机生成code
     *
     * @return
     */
    public static String getBillNo() {
        String num = "";
        try {
            num = IdUtil.getSnowflake(CacheUtil.getWorkId(), CacheUtil.getDateCenterId()).nextIdStr();
        } catch (Exception e) {
            return num;
        }
        return num;
    }

}
 