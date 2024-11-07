package jp.co.futech.module.insight.po.req.task;

import lombok.Data;


@Data
public class TaskRerunReq {
    private String taskId;
    private String startDate;
    private String endDate;

}
