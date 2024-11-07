package jp.co.futech.framework.mybatis.core.log;

import org.apache.ibatis.logging.stdout.StdOutImpl;

/**
 * @description: 自定义sql日志记录器
 * @author: ErHu.Zhao
 * @create: 2024-08-01
 **/
public class IgnoreAllSqlStdOutImpl extends StdOutImpl {
    public IgnoreAllSqlStdOutImpl(String clazz) {
        super(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }
}
