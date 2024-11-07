package jp.co.futech.module.insight.mapper.pg;

import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 测试pg
 * @author: ErHu.Zhao
 * @create: 2024-07-24
 **/
@Mapper
public interface TestPgMapper {

    int selectCount();
}
