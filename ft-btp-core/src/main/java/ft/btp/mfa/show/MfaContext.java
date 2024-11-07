package ft.btp.mfa.show;

import ft.btp.mfa.MfaProcess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-05-16
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MfaContext {

    /**
     * mfa认证用户名称
     */
    private String authUser;

    /**
     * mfa认证标识
     */
    private String authFlag;

    /**
     * mfa处理器
     */
    private MfaProcess process;

    /**
     * 标注@MfaAuth注解的目标方法参数
     */
    private Object[] targetArgs;

    /**
     * 标注@MfaAuth注解的目标方法执行结果
     */
    private Object targetResult;


}
