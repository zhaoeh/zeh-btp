package jp.co.futech.module.insight.entity.ms;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Heng.zhang
 */
@Data
@TableName("task_dim_meta_info")
public class EventTaskDimMetaInfoEntity {
    private String colName;
    private String colRename;
    private String colAliasName;
    private String colType;
    private String colTableName;
    private String colComment;
    private String guid;
}
