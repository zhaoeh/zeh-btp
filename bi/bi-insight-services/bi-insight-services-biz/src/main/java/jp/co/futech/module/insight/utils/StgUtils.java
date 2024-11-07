package jp.co.futech.module.insight.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @description: 字符串工具类
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
public class StgUtils {

    public static String removeLast3(String str) {
        if (StringUtils.isBlank(str) || str.length() < 3) {
            return str;
        }
        return str.substring(0, str.length() - 3);
    }
}
