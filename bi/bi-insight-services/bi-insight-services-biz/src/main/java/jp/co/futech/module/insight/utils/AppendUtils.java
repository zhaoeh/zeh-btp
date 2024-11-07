package jp.co.futech.module.insight.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mkt-agent
 * @description:
 * @author: Erhu.Zhao
 * @create: 2023-12-14 14:03
 */
public class AppendUtils {
    /**
     * 前后拼接单引号
     *
     * @return 拼接单引号后的字符串
     */
    public static String appendSingleQuote(String source) {
        return "'".concat(source).concat("'");
    }

    public static String appendConvertSingleQuote(String source) {
        return "\\'".concat(source).concat("\\'");
    }

    /**
     * 前后拼接单引号
     *
     * @param sources 每个元素前后拼接单引号
     * @return
     */
    public static List<String> appendSingleQuote(List<String> sources) {
        return sources.stream().map(AppendUtils::appendSingleQuote).collect(Collectors.toList());
    }

    /**
     * 前后拼接单引号后用,连接为字符串
     *
     * @param sources
     * @return
     */
    public static String appendList(List<String> sources) {
        return sources.stream().map(AppendUtils::appendSingleQuote).collect(Collectors.joining(","));
    }

    public static String appendList(String prefix, List<String> sources, String suffix) {
        return prefix + sources.stream().map(AppendUtils::appendSingleQuote).collect(Collectors.joining(",")) + suffix;
    }

    /**
     * 前后拼接单引号
     *
     * @param sources 每个元素前后拼接单引号
     * @return
     */
    public static List<String> appendSingleQuoteConvert(List<String> sources) {
        return sources.stream().map(AppendUtils::appendConvertSingleQuote).collect(Collectors.toList());
    }

    /**
     * 前后拼接单引号后用,连接为字符串
     *
     * @param sources
     * @return
     */
    public static String appendListConvert(List<String> sources) {
        return sources.stream().map(AppendUtils::appendConvertSingleQuote).collect(Collectors.joining(", "));
    }

    public static String appendListConvert(String prefix, List<String> sources, String suffix) {
        return prefix + sources.stream().map(AppendUtils::appendSingleQuote).collect(Collectors.joining(", ")) + suffix;
    }
}