package jp.co.futech.module.insight.controller.admin.event.vo;

import lombok.*;

import java.util.List;

/**
 * @author Heng.zhang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventTaskPreviewVo {
    private List<String> fields;
    private String taskId;
}