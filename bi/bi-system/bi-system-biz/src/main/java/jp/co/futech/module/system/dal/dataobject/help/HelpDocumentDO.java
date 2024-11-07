package jp.co.futech.module.system.dal.dataobject.help;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.tenant.core.db.TenantBaseDO;
import jp.co.futech.module.system.enums.notice.NoticeTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帮助文档表
 *
 * @author ruoyi
 */
@TableName("system_help_document")
@KeySequence("system_help_document_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class HelpDocumentDO extends TenantBaseDO {

    /**
     * 帮助文档ID
     */
    @TableId
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
     * 帮助文档状态 0：正常 1：关闭
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * 多语言标识
     */
    private String locale;

}
