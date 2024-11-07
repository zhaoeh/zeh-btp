package jp.co.futech.module.insight.controller.admin.event.vo;

import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventTaskMetaVo {
    /***
     * <pre>
     *  1 dim，2 event customer 3 all (dim event other customer)
     * </pre>
     */
    private String metaType;
    private Set<String> tables;
}
