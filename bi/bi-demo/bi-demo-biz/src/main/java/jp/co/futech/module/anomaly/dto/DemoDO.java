package jp.co.futech.module.anomaly.dto;


import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.module.system.enums.notice.NoticeTypeEnum;
import lombok.Data;

/**
 * 帮助文档表
 *
 * @author ruoyi
 */
@Data
public class DemoDO {

    /**
     * 帮助文档ID
     */
    private Long id;
    /**
     * 关联菜单id
     */
    private Long menuId;
    /**
     * 帮助文档标题
     */
    private String title;
    /**
     * 帮助文档类型
     * <p>
     * 枚举 {@link NoticeTypeEnum}
     */
    private Integer type;
    /**
     * 帮助文档内容
     */
    private String content;
    /**
     * 帮助文档状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
