package zeh.btp.constants;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: EnumValid 注解的内容容器，仅支持设置Integer和String类型的接收值范围
 * @author: ErHu.Zhao
 * @create: 2024-01-03
 **/
public class EnumValidContents {

    // 默认message前缀信息
    private String defaultMessagePrefix = "Please enter the correct %s";

    // 允许接受的value范围
    private Object[] acceptValues;

    // 当校验失败，用于提示的message
    private StringBuffer buffer;

    private EnumValidContents(Object[] acceptValues, Boolean useDefaultPrefix, String message) {
        this.acceptValues = acceptValues;
        if (useDefaultPrefix) {
            this.buffer = new StringBuffer(String.format(this.defaultMessagePrefix, formatMessage(message)));
        } else {
            this.buffer = new StringBuffer(formatMessage(message));
        }
    }

    public static PrefixConfigurer values(Integer[] intValues) {
        return new PrefixConfigurer(intValues);
    }

    public static PrefixConfigurer values(String[] strValues) {
        return new PrefixConfigurer(strValues);
    }

    public EnumValidContents message(String message) {
        if (Objects.isNull(this.acceptValues) || this.acceptValues.length == 0) {
            return this;
        }
        this.buffer.append(formatMessage(message));
        return this;
    }

    public Object[] getAcceptValues() {
        return acceptValues;
    }

    public String getMessage() {
        return this.buffer.toString();
    }

    private String formatMessage(String message) {
        if (StringUtils.isBlank(message)) {
            return message;
        }
        // 处理包含 %& 占位符的message
        final String format = "%&";
        int firstIndex = message.indexOf(format);
        if (firstIndex > 0) {
            int secondIndex = message.indexOf(format, firstIndex + 1);
            if (secondIndex > 0) {
                throw new IllegalArgumentException("[%&] 在当前message中只能出现一次");
            }
            message = message.replace(format, convertFlag());
        }

        // 处理包含 %s 占位符的message
        final String strFormat = "%s";
        if (message.indexOf(strFormat) > 0) {
            message = String.format(message, this.acceptValues);
        }

        // 处理包含 {0} 之类占位符的message
        return MessageFormat.format(message, this.acceptValues);
    }

    private String convertFlag() {
        if (Objects.isNull(this.acceptValues) || this.acceptValues.length == 0) {
            return "";
        }
        return Arrays.stream(this.acceptValues).map(e -> Objects.toString(e)).collect(Collectors.joining(","));
    }

    public static class PrefixConfigurer {

        private Object[] acceptValues;

        private PrefixConfigurer(Object[] acceptValues) {
            this.acceptValues = acceptValues;
        }

        public MessageConfigurer prefix(boolean useDefaultPrefix) {
            return new MessageConfigurer(this.acceptValues, useDefaultPrefix);
        }
    }

    public static class MessageConfigurer {
        private Object[] acceptValues;

        private Boolean useDefaultPrefix;

        private MessageConfigurer(Object[] acceptValues, Boolean useDefaultPrefix) {
            this.acceptValues = acceptValues;
            this.useDefaultPrefix = useDefaultPrefix;
        }

        public EnumValidContents message(String message) {
            return new EnumValidContents(this.acceptValues, this.useDefaultPrefix, message);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(acceptValues) + "\n" + buffer;
    }
}
