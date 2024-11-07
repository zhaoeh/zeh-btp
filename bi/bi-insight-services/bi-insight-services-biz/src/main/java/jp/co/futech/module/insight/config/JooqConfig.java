package jp.co.futech.module.insight.config;

import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.ExecuteListenerProvider;
import org.jooq.TransactionProvider;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.autoconfigure.jooq.JooqProperties;
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @description: jooq自定义配置
 * @author: ErHu.Zhao
 * @create: 2024-07-25
 **/
@Configuration
public class JooqConfig {
    @Bean
    @ConditionalOnMissingBean({ConnectionProvider.class})
    public DataSourceConnectionProvider dataSourceConnectionProvider(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    @ConditionalOnBean({PlatformTransactionManager.class})
    @ConditionalOnMissingBean({TransactionProvider.class})
    public SpringTransactionProvider transactionProvider(PlatformTransactionManager txManager) {
        return new SpringTransactionProvider(txManager);
    }

    @Bean
    @Order(0)
    public DefaultExecuteListenerProvider jooqExceptionTranslatorExecuteListenerProvider() {
        return new DefaultExecuteListenerProvider(new JooqExceptionTranslator());
    }

    @Configuration(
            proxyBeanMethods = false
    )
    @ConditionalOnMissingBean({DSLContext.class})
    @EnableConfigurationProperties({JooqProperties.class})
    public static class DslContextConfiguration {
        public DslContextConfiguration() {
        }

        @Bean
        public DefaultDSLContext dslContext(org.jooq.Configuration configuration) {
            return new DefaultDSLContext(configuration);
        }

        @Bean
        @ConditionalOnMissingBean({org.jooq.Configuration.class})
        public DefaultConfiguration jooqConfiguration(JooqProperties properties, ConnectionProvider connectionProvider, @Qualifier("mysqlDataSource") DataSource dataSource, ObjectProvider<TransactionProvider> transactionProvider, ObjectProvider<ExecuteListenerProvider> executeListenerProviders, ObjectProvider<DefaultConfigurationCustomizer> configurationCustomizers) {
            DefaultConfiguration configuration = new DefaultConfiguration();
            configuration.set(properties.determineSqlDialect(dataSource));
            configuration.set(connectionProvider);
            Objects.requireNonNull(configuration);
            transactionProvider.ifAvailable(configuration::set);
            configuration.set((ExecuteListenerProvider[]) executeListenerProviders.orderedStream().toArray((x$0) -> new ExecuteListenerProvider[x$0]));
            configurationCustomizers.orderedStream().forEach((customizer) -> {
                customizer.customize(configuration);
            });
            configuration.set(new Settings().withRenderFormatted(true));
            return configuration;
        }
    }
}
