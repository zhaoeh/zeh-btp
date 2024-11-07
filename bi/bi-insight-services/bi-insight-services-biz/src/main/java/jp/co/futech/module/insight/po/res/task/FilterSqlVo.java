package jp.co.futech.module.insight.po.res.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FilterSqlVo {
    private int isfirstFilter=-1; //0 是，1 否 -1 默认值
    private int isCustomersAll; //0是，1否
    private String beforeRelation;
    private String joinRelationOp;
    private String joinOrUnion;
    private String origineTableName;
    private String aliasTableName;
    private String sqlStr;
    private String filterColTableName;
}
