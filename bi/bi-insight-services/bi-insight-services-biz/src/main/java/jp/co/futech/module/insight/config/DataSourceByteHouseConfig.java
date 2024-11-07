package jp.co.futech.module.insight.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jp.co.futech.framework.mybatis.core.log.StdOutImplSetting;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;

/**
 * bytehouse配置类
 */
@Configuration
@MapperScan(sqlSessionFactoryRef = "bytehouseSqlSessionFactory", basePackages = {"jp.co.futech.module.insight.mapper.bh"})
public class DataSourceByteHouseConfig {

    @Autowired
    private StdOutImplSetting stdOutImplSetting;

    @Resource
    private JdbcParamsConfig jdbcParamsConfig;

    @Value("${spring.datasource.second.username}")
    private String userName;
    @Value("${spring.datasource.second.password}")
    private String password;
    @Value("${spring.datasource.second.url}")
    private String url;
    @Value("${spring.datasource.second.poolName}")
    private String poolName;
    @Value("${spring.datasource.second.driver-class-name}")
    private String driverClassName;

    @Bean("bytehouseMybatisPlusInterceptor")
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.CLICK_HOUSE));
        return mybatisPlusInterceptor;
    }

    @Bean("byteHouseDataSource")
    public HikariDataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setConnectionTimeout(jdbcParamsConfig.getConnectionTimeout());
        hikariConfig.setIdleTimeout(jdbcParamsConfig.getIdleTimeout());
        hikariConfig.setMaximumPoolSize(jdbcParamsConfig.getMaxPoolSize());
        hikariConfig.setMinimumIdle(jdbcParamsConfig.getMinIdle());
        hikariConfig.setMaxLifetime(jdbcParamsConfig.getMaxLifetime());
        hikariConfig.setPoolName(poolName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean("bytehouseSqlSessionFactory")
    public SqlSessionFactory clickhouseSqlSessionFactory(@Qualifier("byteHouseDataSource") HikariDataSource hikariDataSource, @Qualifier("bytehouseMybatisPlusInterceptor") MybatisPlusInterceptor bytehouseMybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(hikariDataSource);
        bean.setTypeAliasesPackage("jp.co.futech.module.insight.entity.bh.**");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:bh/*.xml"));
        bean.setPlugins(bytehouseMybatisPlusInterceptor);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        //打印sql语句
        stdOutImplSetting.configLog(configuration);
        configuration.setCacheEnabled(false);
        bean.setConfiguration(configuration);

        SqlSessionFactory factory = bean.getObject();

        return factory;
    }

}
