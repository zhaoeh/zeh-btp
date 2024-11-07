package ft.btp.aop;

import lombok.Builder;
import lombok.Data;

/**
 * @description: 后置操作参数对象
 * @author: Erhu.Zhao
 * @create: 2023-10-23 13:39
 **/
@Data
@Builder
public class AfterParams {

    private Object[] params;

    private Object returnValue;
}
