package jp.co.futech.module.infra.dal.mysql.db;

import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author futech.co.jp
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
