package jp.co.futech.module.insight.controller.admin.event.vo;


import com.alibaba.fastjson2.JSONObject;
import lombok.*;

import java.sql.Timestamp;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskScheduleResultVo {
    private String task_id;
    private String task_cate;
    private Integer schedule_result; //1：成功 2：失败
//    private Timestamp start_time;  //任务开始时间
//    private Timestamp end_time; //任务结束时间
//    private String log_info; //任务运行日志

}
