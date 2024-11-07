package jp.co.futech.framework.mybatis.core.log;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Value;

/**
 * @description: log设置
 * @author: ErHu.Zhao
 * @create: 2024-08-01
 **/
public class StdOutImplSetting {

    private final Integer originalLevel = 2;
    private final Integer ignoreAllSqlLevel = 3;

    @Value("${LOCAL_SQL_LOG:}")
    private Integer sqlLogLevel;

    public void configLog(MybatisConfiguration configuration) {
        if (originalLevel.equals(sqlLogLevel)) {
            configuration.setLogImpl(StdOutImpl.class);
        } else if (ignoreAllSqlLevel.equals(sqlLogLevel)) {
            configuration.setLogImpl(IgnoreAllSqlStdOutImpl.class);
        } else {
            configuration.setLogImpl(IgnoreSqlResultStdOutImpl.class);
        }

    }
}
