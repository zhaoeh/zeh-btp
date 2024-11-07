package jp.co.futech.module.insight.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import jp.co.futech.framework.mybatis.core.log.StdOutImplSetting;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * mysql配置类
 */
@Configuration
@MapperScan(sqlSessionFactoryRef = "mysqlSqlSessionFactory", basePackages = {"jp.co.futech.module.insight.mapper.ms"})
public class DataSourceMysqlConfig {

    @Autowired
    private StdOutImplSetting stdOutImplSetting;

    @Primary
    @Bean(name = "mysqlMybatisPlusInterceptor")
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean(name = "mysqlDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "mysqlDataSource")
    public DataSource dataSource(@Qualifier("mysqlDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .type(DruidDataSource.class)
                .build();
    }

    @Bean("mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource,
                                                    @Qualifier("mysqlMybatisPlusInterceptor") MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setTypeAliasesPackage("jp.co.futech.module.insight.entity.ms.**");
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);

        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:ms/*.xml"));
        bean.setPlugins(mybatisPlusInterceptor);
        bean.setGlobalConfig(globalConfig);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);

        stdOutImplSetting.configLog(configuration);
        configuration.setCacheEnabled(false);
        bean.setConfiguration(configuration);

        SqlSessionFactory factory = bean.getObject();

        return factory;
    }

    @Bean
    @Primary
    public DataSourceTransactionManager msTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}



