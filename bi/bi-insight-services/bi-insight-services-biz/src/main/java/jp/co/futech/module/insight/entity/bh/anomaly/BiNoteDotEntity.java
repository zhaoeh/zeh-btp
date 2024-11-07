package jp.co.futech.module.insight.entity.bh.anomaly;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: t_bi_note_dot 实体
 * @author: ErHu.Zhao
 * @create: 2024-06-14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_bi_note_dot")
public class BiNoteDotEntity {

    private String metric;

    private String site;

    private String user_name;

    private String dt;

    private String note;

    private String remark;

    private String create_time;

}
