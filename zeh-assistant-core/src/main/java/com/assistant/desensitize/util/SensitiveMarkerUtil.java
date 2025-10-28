package com.assistant.desensitize.util;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;
import java.util.regex.Matcher;

import static com.assistant.desensitize.constants.DesensitizeConstants.*;

public class SensitiveMarkerUtil {

    /**
     * 给代理账号加上统一标记
     */
    public static String markSensitiveForProxyAccount(String proxyAccount) {
        if (StringUtils.isBlank(proxyAccount)) {
            return proxyAccount;
        }
        return PROXY_ACCOUNT_MARKER + proxyAccount + PROXY_ACCOUNT_MARKER;
    }

    /**
     * 给玩家账号加上统一标记
     */
    public static String markSensitiveForPlayerAccount(String playerAccount) {
        if (StringUtils.isBlank(playerAccount)) {
            return playerAccount;
        }
        return PLAYER_ACCOUNT_MARKER + playerAccount + PLAYER_ACCOUNT_MARKER;
    }

    /**
     * 对标记过的内容进行脱敏
     * @param input 原始字符串
     * @param strategy 脱敏策略（输入原始内容 -> 输出脱敏结果）
     */
    public static String desensitizeMarked(String input, Function<String, String> strategy) {
        if (StringUtils.isBlank(input)) {
            return input;
        }

        Matcher matcher = MARKED_PATTERN.matcher(input);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String original = matcher.group(2); // 捕获的内容
            String masked = strategy.apply(original); // 应用脱敏策略
            matcher.appendReplacement(sb, Matcher.quoteReplacement(masked));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
