package jp.co.futech.module.insight.entity.ms;

import com.baomidou.mybatisplus.annotation.TableName;
import ft.btp.i18n.annotation.EnableI18nField;
import ft.btp.i18n.annotation.I18nField;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("task_event_meta_info")
@EnableI18nField
public class EventTaskEventMetaEntity extends BaseMetaEntity{
    private String colName;
    private String colRename;
    @I18nField
    private String colAliasName;
    private int colType;
    private String colTableName;
    @I18nField
    private String colGroup;
    private String colComment;
    private Timestamp updateTime;
    private String guid;
}
