package jp.co.futech.module.insight.mapper.bh;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * @author Heng.zhang
 */
@Repository
public interface EventTaskMapper extends BaseMapper<Object> {
    /**
     * Explain Syntax By Query Sql
     *
     * @param querySql Query Sql
     */
    @Update("${querySql}")
    void explainSyntax(String querySql);


}
