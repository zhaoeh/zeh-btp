package jp.co.futech.module.insight.entity.ms;

import com.baomidou.mybatisplus.annotation.TableName;
import ft.btp.i18n.annotation.EnableI18nField;
import ft.btp.i18n.annotation.I18nField;
import lombok.Data;

@Data
@TableName("task_sql_template_info")
@EnableI18nField
public class EventTaskSqlTemplateEntity {
    private int aggType;
    private String aggTypeName;
    private int templateType;
    @I18nField
    private String templateTypeName;
    private String templateTypeValue;
    private String aliasName;
    private int sqlStatus;
    private String guid;
}
