package ft.btp.utils;

import com.riskcontrol.common.constants.Constant;
import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;

public class LogUtils {

    /**
     * 生成并设置uuid
     */
    public static void generatedThreadLogUUID() {
        cleanLogUUID();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put(Constant.MDC_UUID_KEY, uuid);
    }

    /**
     * 生成uuid并返回
     * @return
     */
    public static String generatedThreadUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 清理uuid
     */
    public static void cleanLogUUID() {
        MDC.remove(Constant.MDC_UUID_KEY);
    }

    /**
     * 设置MDC上下文信息
     * @param mdcContextMap
     */
    public static void setMDCContextMap(Map<String, String> mdcContextMap) {
        if(mdcContextMap!=null){
            MDC.setContextMap(mdcContextMap);
        }
    }

    /**
     * 获取复制MDC上下问信息，可用于跨线程之间传递MDC
     * @return
     */
    public static Map<String, String> getMDCContextMap() {
        return MDC.getCopyOfContextMap();
    }
}
