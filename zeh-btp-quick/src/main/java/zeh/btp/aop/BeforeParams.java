package zeh.btp.aop;

import lombok.Builder;
import lombok.Data;

/**
 * @description: 前置操作入参对象
 * @author: Erhu.Zhao
 * @create: 2023-10-23 13:38
 **/
@Data
@Builder
public class BeforeParams {

    private Object[] params;

}
