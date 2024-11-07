package jp.co.futech.module.insight.config;

import ft.btp.i18n.api.I18nCodeMapper;
import ft.btp.i18n.api.I18nHandler;
import ft.btp.i18n.api.I18nMessageConvert;
import ft.btp.i18n.core.I18nMessagePostProcessor;
import ft.btp.i18n.core.I18nMessagesProvider;
import ft.btp.i18n.core.ValidLocalesProvider;
import ft.btp.i18n.info.I18nMessages;
import ft.btp.i18n.scope.I18nPeriodRefreshBeanScopeManager;
import ft.btp.scope.builder.IocBeanBuilder;
import ft.btp.scope.holder.RefreshScopeHolder;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.futech.framework.common.exception.ServiceException;
import jp.co.futech.module.insight.config.rpc.RpcConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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

    /**
     * 确保当前类在RpcConfiguration初始化后执行
     */
    private final RpcConfiguration rpcConfiguration;

    @Autowired
    private I18nMessagesResourceConfig i18nMessagesResourceConfig;

    private I18nMessages i18nMessages;

    public I18nMessagesConfig(RpcConfiguration rpcConfiguration) {
        this.rpcConfiguration = rpcConfiguration;
        loadI18nMessages();
    }

    public void loadI18nMessages() {
        log.info("begin to load i18n messages");
        i18nMessages = rpcConfiguration.getI18nApi().getI18nMessages().getCheckedData();
        log.info("end to load i18n messages");
    }

    @Bean
    public I18nMessagesProvider i18nMessagesProvider() {
        return this::provideI18nMessages;
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

    private I18nMessages provideI18nMessages() {
        loadI18nMessages();
        return i18nMessages;
    }


    @Bean
    public I18nCodeMapper taskI18nCodeMapper() {
        return new TaskI18nCodeMapper();
    }

    public static class TaskI18nCodeMapper implements I18nCodeMapper {
        @Override
        public Map<String, String> provideI18nCodeMapper(I18nMessages messages) {
            return defaultCodeMapper(messages, entry -> {
                Integer code = Integer.valueOf(entry.getKey());
                return code >= 400000000 && code <= 400100000;
            });
        }
    }

    @Bean
    public I18nHandler taskI18nHandler() {
        return new TaskI18nHandler();
    }

    public static class TaskI18nHandler implements I18nHandler {
        @Override
        public void handleResult(Object result, I18nMessageConvert convert) {
            log.info("给你一个处理响应值的机会");
        }
    }

    @Bean
    public I18nMessagePostProcessor i18nMessagePostProcessor() {
        return new I18nMessagePostProcessor() {
            @Override
            public boolean supports(Locale currentLocale) {
                String expectUri = "/admin-api/insight/event_task/data_metadata";
                String expectLocale = "en_US";
                HttpServletRequest request = null;
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (Objects.nonNull(requestAttributes)) {
                    request = requestAttributes.getRequest();
                }
                String uri = Objects.isNull(request) ? null : request.getRequestURI();
                if (expectLocale.equals(currentLocale.toString()) && expectUri.equals(uri)) {
                    return true;
                }
                return false;
            }

            @Override
            public String postProcessAfterI18nConvert(Locale currentLocale, String code, String valueAfterI18n) {
                return StringUtils.trim(valueAfterI18n);
            }
        };

    }

}
