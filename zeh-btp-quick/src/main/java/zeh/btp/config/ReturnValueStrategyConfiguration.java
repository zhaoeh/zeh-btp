package zeh.btp.config;

import java.util.Map;

/**
 * @program: riskcontrol-common
 * @description:
 * @author: Erhu.Zhao
 * @create: 2023-12-01 15:22
 */
public interface ReturnValueStrategyConfiguration {

    /**
     * 是否打开该策略
     *
     * @return true：打开  false：关闭
     */
    boolean supportsDoThis();

    /**
     * 返回待替换的 HandlerMethodReturnValueHandler 名称和对应的返回值处理策略
     *
     * @return 待替换的HandlerMethodReturnValueHandler名称和对应的处理策略
     */
    Map<String, ReturnValueStrategy> replacedHandlerAndStrategy();

    @FunctionalInterface
    interface ReturnValueStrategy {
        /**
         * 包装对应的web返回值
         *
         * @param returnValue 原始返回值
         * @return 包装后的返回值
         */
        Object wrapperReturnValue(Object returnValue);
    }
}