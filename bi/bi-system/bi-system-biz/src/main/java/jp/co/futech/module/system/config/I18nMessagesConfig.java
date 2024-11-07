package jp.co.futech.module.system.config;

import ft.btp.i18n.api.I18nCodeMapper;
import ft.btp.i18n.api.I18nHandler;
import ft.btp.i18n.api.I18nMessageConvert;
import ft.btp.i18n.core.I18nMessagesProvider;
import ft.btp.i18n.core.ValidLocalesProvider;
import ft.btp.i18n.info.I18nMessages;
import ft.btp.i18n.scope.I18nPeriodRefreshBeanScopeManager;
import ft.btp.scope.builder.IocBeanBuilder;
import ft.btp.scope.holder.RefreshScopeHolder;
import jp.co.futech.framework.common.exception.ServiceException;
import jp.co.futech.module.system.service.i18n.I18nService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static jp.co.futech.framework.common.exception.ErrorCodeConstants.LOCAL_INVALID;

/**
 * @description: 国际化消息供应者配置类
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
@Configuration
@Slf4j
public class I18nMessagesConfig {

    @Autowired
    private I18nMessagesResourceConfig i18nMessagesResourceConfig;

    @Autowired
    private I18nService i18nService;

    @Bean
    public I18nMessagesProvider i18nMessagesProvider() {
        return () -> i18nService.encapI18nMessages(false);
    }

    @Bean
    public I18nPeriodRefreshBeanScopeManager i18nPeriodRefreshBeanScopeManager() {
        return new I18nPeriodRefreshBeanScopeManager() {

            private final Map<String, Object> objectMap = new ConcurrentHashMap<>(4);

            @Override
            public Object get(String name, IocBeanBuilder iocBeanBuilder) {
                Object newInstance;
                RefreshScopeHolder.setScope("myScope");
                // 配置被刷新，则重新构建新对象
                if (i18nMessagesResourceConfig.isRefreshed()) {
                    log.info("nacos config is refreshed");
                    newInstance = iocBeanBuilder.buildBean();
                    objectMap.put(name, newInstance);
                } else {
                    newInstance = objectMap.get(name);
                }
                return newInstance;
            }

            @Override
            public Object remove(String name) {
                return null;
            }

            /**
             * 资源惰性刷新周期 1分钟
             * @return
             */
            @Override
            public long lazyAsyncRefreshPeriodMinutes() {
                return 0;
            }
        };
    }

    @Bean
    public ValidLocalesProvider validLocalesProvider() {
        return () -> ValidLocalesProvider.defaultLocaleSupports(new ServiceException(LOCAL_INVALID));
    }

    /**
     * 注册menu菜单的code mapper
     *
     * @return I18nCodeMapper instance
     */
    @Bean
    public I18nCodeMapper menuI18nCodeMapper() {
        return new MenuI18nCodeMapper();
    }

    public static class MenuI18nCodeMapper implements I18nCodeMapper {
        @Override
        public Map<String, String> provideI18nCodeMapper(I18nMessages messages) {
            return defaultCodeMapper(messages, entry -> entry.getKey().startsWith("300"));
        }
    }

    @Bean
    public I18nHandler testI18nHandler() {
        return new TestI18nHandler();
    }

    /**
     * 测试自定义国际化处理器，处理特殊的http response
     */
    public static class TestI18nHandler implements I18nHandler {
        @Override
        public void handleResult(Object result, I18nMessageConvert convert) {
            log.info("给你一个处理响应值的机会");
        }
    }

}
