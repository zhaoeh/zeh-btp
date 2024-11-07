package ft.btp.aop;

/**
 * 通用的切面逻辑接口
 *
 * @description: common aspect
 * @author: Erhu.Zhao
 * @create: 2023-10-20 16:11
 **/

public interface CommonAspectRunner {

    /**
     * 接收任意参数，返回一个输出
     *
     * @param beforeParams 参数
     * @return 执行结果
     * @throws Exception 异常
     */
    Object before(BeforeParams beforeParams) throws Exception;

    /**
     * 解释后置参数，返回一个输出，即后置拦截器，参数afterParams封装了后置拦截器的上有入参，和后置拦截器执行完毕的结果对象result
     *
     * @param afterParams
     * @return
     * @throws Exception
     */
    Object after(AfterParams afterParams) throws Exception;

}
