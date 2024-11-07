package jp.co.futech.module.insight.entity.ms;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
@Data
public class BaseMetaEntity {
    private String colName;
    private String colRename;
    private String colAliasName;
    private int colType;
    private String colTableName;
    private String colGroup;
    private String colComment;
    private Timestamp updateTime;
    private String guid;
}
