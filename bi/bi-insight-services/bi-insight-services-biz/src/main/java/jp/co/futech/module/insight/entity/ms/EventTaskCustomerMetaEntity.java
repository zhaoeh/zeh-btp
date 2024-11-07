package jp.co.futech.module.insight.entity.ms;


import com.baomidou.mybatisplus.annotation.TableName;
import ft.btp.i18n.annotation.EnableI18nField;
import ft.btp.i18n.annotation.I18nField;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("task_customer_meta_info")
@EnableI18nField
public class EventTaskCustomerMetaEntity extends BaseMetaEntity{
    private String colName;
    private String colRename;
    @I18nField
    private String colAliasName;
    private int colType;
    private String colTableName;
    @I18nField
    private String colGroup;
    private String colCalculation;
    private String colComment;
    private String createUserName;
    private Timestamp createTime;
    private String updateUserName;
    private Timestamp updateTime;
    private String guid;
}
