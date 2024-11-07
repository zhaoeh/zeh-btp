package jp.co.futech.module.insight.po.req.task;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskSqlTemplateDataReq {

    /**
     * <pre>
     * 模型类型
     * </pre>
     */
    private Integer	templateType;

    /**
     * <pre>
     * 模型类型名称
     * </pre>
     */
    private String	templateTypeName;

    /**
     * <pre>
     * 模型类型值
     * </pre>
     */
    private String	templateTypeValue;


}
