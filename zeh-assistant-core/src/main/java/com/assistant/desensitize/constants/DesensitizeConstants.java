package com.assistant.desensitize.constants;

import java.util.regex.Pattern;

public class DesensitizeConstants {

    /**
     * 是否跳过脱敏
     */
    public static final String DESENSITIZE_SKIP = "DESENSITIZE_SKIP";

    public static final String PROXY_ACCOUNT_MARKER = "$&$";

    public static final String PLAYER_ACCOUNT_MARKER = "$#$";

    // 支持 $&$xxx$&$ 或 $#$xxx$#$，还能保证前后标记成对匹配
    public static final Pattern MARKED_PATTERN = Pattern.compile("(\\$&\\$|\\$#\\$)(.*?)(\\1)");

}
