package jp.co.futech.module.insight.mapper.pg;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 测试pg
 * @author: ErHu.Zhao
 * @create: 2024-07-24
 **/
@Repository
public interface PgEventTaskMapper extends BaseMapper<Object> {

    @Update("${querySql}")
    void executeDDL(String querySql);


    @Select("${querySql}")
    List<JSONObject> executeSql(String querySql);
}
