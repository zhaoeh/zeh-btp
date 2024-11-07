package jp.co.futech.module.insight.entity.bh.anomaly;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: t_bi_daily_get_tree_data 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_bi_daily_get_tree_data")
public class BiDailyGetTreeDataEntity {

    private String metric;

    private String site;

    private String dt;

    private String treeData;

    private String updateDt;

}
