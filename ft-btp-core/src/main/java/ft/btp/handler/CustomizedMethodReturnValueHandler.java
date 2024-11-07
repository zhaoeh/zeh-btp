package ft.btp.handler;

import ft.btp.config.ReturnValueStrategyConfiguration;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @description: 自定义 HandlerMethodReturnValueHandler，自定义的返回值处理器实现类，需要注册到spring容器中去
 * 在注册时，需要替换调容器中默认的某个返回值处理器才能生效，否则尽管注册到spring容器去了，在HandlerAdapter中也不会查询到该处理器，因为spring mvc默认查询返回值处理器的策略是固定的*
 * @author: Erhu.Zhao
 * @create: 2023-12-01 15:00
 */
public class CustomizedMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;

    /**
     * 逻辑策略
     */
    private ReturnValueStrategyConfiguration.ReturnValueStrategy strategy;

    /**
     * 构造器，注入容器中默认的handlerMethodReturnValueHandler实例，请注意，spring mvc中的HandlerMethodReturnValueHandler接口本身就是一个策略接口，它在HandlerAdapter的handleReturnValue方法中被触发*
     * 在处理handler响应值时，根据目标handler的目标方法上的规则，来选择对应的策略实现类来处理handler响应值*
     * 比如，如果目标方法上标注了@ResponseBody注解，则会默认调用RequestResponseBodyMethodProcessor这个策略实现类来处理处理器响应值*
     * 本初将容器中默认的handlerMethodReturnValueHandler实例注册进来，即不修改框架原始的策略逻辑*
     *
     * @param handlerMethodReturnValueHandler
     * @param strategy
     */
    public CustomizedMethodReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler,
                                              ReturnValueStrategyConfiguration.ReturnValueStrategy strategy) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
        this.strategy = strategy;
    }

    /**
     * 支持拦截的处理器响应值的类型，只有符合该类型的响应才会触发该钩子接口的handleReturnValue方法执行*
     * 此处的实现，是委托容器中默认的策略实现类来判断当前的策略类是否该被触发执行*
     * 比如，如果目标方法有@ResponseBody注解，则当前的CustomizedMethodReturnValueHandler策略实现类就会被触发*
     *
     * @param returnType 返回值类型
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return handlerMethodReturnValueHandler.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        handlerMethodReturnValueHandler.handleReturnValue(strategy.wrapperReturnValue(returnValue), returnType, mavContainer, webRequest);
    }
}