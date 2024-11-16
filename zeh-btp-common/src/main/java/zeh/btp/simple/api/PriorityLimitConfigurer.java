package zeh.btp.simple.api;

/**
 * @description: 限流拦截配置器
 * @author: ErHu.Zhao
 * @create: 2024-01-12
 **/
public interface PriorityLimitConfigurer {

    /**
     * 是否强制禁止，当该值为true时，则目标方法始终被禁止访问
     *
     * @return
     */
    default boolean forceLimit() {
        return false;
    }

    /**
     * 配置拦截Key
     *
     * @param target 标注 @RequestLimit 注解所在的目标类对象
     * @return 拦截key
     */
    String limitKeyConfigurer(Object target);

    /**
     * 限制时间 单位：秒
     *
     * @param target 标注 @RequestLimit 注解所在的目标类对象
     * @return 限制时间
     */
    Long limitPeriodConfigurer(Object target);

    /**
     * 允许请求的次数
     *
     * @param target 标注 @RequestLimit 注解所在的目标类对象
     * @return 允许请求的次数
     */
    Long limitCountConfigurer(Object target);
}
