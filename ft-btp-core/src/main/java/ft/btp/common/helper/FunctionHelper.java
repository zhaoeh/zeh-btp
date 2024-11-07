package ft.btp.common.helper;

import org.apache.commons.lang3.BooleanUtils;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Function Helper
 *
 * @program: riskcontrol-api
 * @description:包装Function函数式接口，提供单值和复值的包装方法
 * @author: Erhu.Zhao
 * @create: 2023-10-06 11:04
 **/

public class FunctionHelper {

    /**
     * 调度执行单值Function
     *
     * @param function 函数式对象
     * @param param    实际参数
     * @param <P>      参数类型
     * @param <R>      返回值类型
     * @return 函数执行结果
     */
    public static <P, R> R doIt(Function<P, R> function, P param) {
        return function.apply(param);
    }

    /**
     * 调度执行复值Function
     *
     * @param function 函数式对象
     * @param param1   实际参数1
     * @param param2   实际参数2
     * @param <P1>     参数1类型
     * @param <P2>     参数2类型
     * @param <R>      返回值类型
     * @return 函数执行结果
     */
    public static <P1, P2, R> R doIt(BiFunction<P1, P2, R> function, P1 param1, P2 param2) {
        return function.apply(param1, param2);
    }


    /**
     * 根据条件调度执行单值Function
     *
     * @param condition boolean条件
     * @param one       函数式对象1
     * @param two       函数式对象2
     * @param param     实际参数
     * @param <P>       参数类型
     * @param <R>       返回值类型
     * @return 函数执行结果
     */
    public static <P, R> R doIt(Boolean condition, Function<P, R> one, Function<P, R> two, P param) {
        return conditionIsSuccess(condition) ? doIt(one, param) : doIt(two, param);
    }

    /**
     * 根据条件调度执行复值BiFunction
     *
     * @param condition boolean条件
     * @param one       函数式对象1
     * @param two       函数式对象2
     * @param param1    实际参数1
     * @param param2    实际参数2
     * @param <P1>      参数1类型
     * @param <P2>      参数2类型
     * @param <R>       返回值类型
     * @return 函数执行结果
     */
    public static <P1, P2, R> R doIt(Boolean condition, BiFunction<P1, P2, R> one, BiFunction<P1, P2, R> two, P1 param1, P2 param2) {
        return conditionIsSuccess(condition) ? doIt(one, param1, param2) : doIt(two, param1, param2);
    }

    /**
     * 判断条件是否成立
     *
     * @param condition 条件表达式
     * @return 条件是否成立 true：成立  false：不成立
     */
    private static boolean conditionIsSuccess(Boolean condition) {
        return BooleanUtils.isTrue(condition);
    }
}
