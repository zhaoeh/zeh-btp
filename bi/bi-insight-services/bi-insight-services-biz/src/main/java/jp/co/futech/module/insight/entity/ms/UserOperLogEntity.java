package jp.co.futech.module.insight.entity.ms;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("data_mage_user_oper_log")
public class UserOperLogEntity {
    private long id;
    private String ModuleCode;
    private String ObjectId;
    private int operType;
    private String operDesc;
    private String uri;
    private String remoteAddr;
    private String params;
    private String responseCode;
    private String responseMessage;
    private long creatorUserid;
    private String creatorUsername;
    private LocalDateTime createTime;
}
