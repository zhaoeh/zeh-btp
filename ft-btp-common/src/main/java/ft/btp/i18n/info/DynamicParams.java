package ft.btp.i18n.info;

import java.util.Map;

public interface DynamicParams {

    /**
     * 返回动态参数，用于国际化消息中占位符的动态参数替换
     *
     * @return 动态参数
     */
    Map<String, Object> provideParams();
}
