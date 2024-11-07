package ft.btp.config;

import ft.btp.handler.CustomizedMethodReturnValueHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.*;

/**
 * @description: response body自定义配置
 * @author: Erhu.Zhao
 * @create: 2023-12-01 15:09
 */
@Configuration
public class ReturnValueConfiguration implements InitializingBean {

    /**
     * 注入RequestMappingHandlerAdapter，目的是通过请求映射器处理适配器获取当前spring mvc容器中已经注册的所有的HandlerMethodReturnValueHandler链*
     * 获取到对应的返回值处理器链之后，可以使用自定义的CustomizedMethodReturnValueHandler返回值处理器去替换链中的某个处理器*
     */
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /**
     * 注入自定义的返回值处理器策略*
     */
    @Autowired
    private ObjectProvider<ReturnValueStrategyConfiguration> replacedHandlerAndStrategy;

    /**
     * 当前配置类被初始化后（填充完属性后）执行如下方法*
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Optional.ofNullable(replacedHandlerAndStrategy.getIfAvailable()).filter(ReturnValueStrategyConfiguration::supportsDoThis).ifPresent(strategy -> {
            Map<String, ReturnValueStrategyConfiguration.ReturnValueStrategy> replacedHandlerAndStrategy = strategy.replacedHandlerAndStrategy();
            this.resetReturnValueHandlersIfNeed(replacedHandlerAndStrategy);
        });
    }

    private void resetReturnValueHandlersIfNeed(Map<String, ReturnValueStrategyConfiguration.ReturnValueStrategy> replacedHandlerAndStrategy) {
        if (CollectionUtils.isEmpty(replacedHandlerAndStrategy)) {
            return;
        }
        List<HandlerMethodReturnValueHandler> handlers = obtainReturnValueHandlers();
        if (CollectionUtils.isEmpty(handlers)) {
            return;
        }
        doResetReturnValueHandlers(handlers, replacedHandlerAndStrategy);
    }

    private void doResetReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers, Map<String, ReturnValueStrategyConfiguration.ReturnValueStrategy> replacedHandlerAndStrategy) {
        List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>(handlers.size());
        handlers.stream().forEach(h -> Optional.ofNullable(h).ifPresent(handler -> fillNewHandler(newHandlers, replacedHandlerAndStrategy, handler)));
        Optional.ofNullable(newHandlers).filter(e -> !CollectionUtils.isEmpty(newHandlers)).ifPresent(t -> this.requestMappingHandlerAdapter.setReturnValueHandlers(t));
    }

    private List<HandlerMethodReturnValueHandler> obtainReturnValueHandlers() {
        return Optional.ofNullable(requestMappingHandlerAdapter).map(RequestMappingHandlerAdapter::getReturnValueHandlers).orElse(Collections.emptyList());
    }

    private void fillNewHandler(List<HandlerMethodReturnValueHandler> newHandlers, Map<String, ReturnValueStrategyConfiguration.ReturnValueStrategy> replacedHandlerAndStrategy, HandlerMethodReturnValueHandler currentHandler) {

        if (replacedHandlerAndStrategy.containsKey(currentHandler.getClass().getName())) {
            ReturnValueStrategyConfiguration.ReturnValueStrategy strategy = replacedHandlerAndStrategy.get(currentHandler.getClass().getName());
            newHandlers.add(new CustomizedMethodReturnValueHandler(currentHandler, strategy));
        } else {
            newHandlers.add(currentHandler);
        }
    }

}