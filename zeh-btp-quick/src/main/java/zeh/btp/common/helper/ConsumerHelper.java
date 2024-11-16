package zeh.btp.common.helper;

import java.util.function.Consumer;

/**
 * @program: riskcontrol-common
 * @description: 包装Consumer函数式接口
 * @author: Erhu.Zhao
 * @create: 2023-10-30 14:38
 */
public class ConsumerHelper {

    /**
     * 根据条件判断是否执行Consumer
     *
     * @param isDoIt 是否执行consumer
     * @param action consumer instance
     * @param param  实际参数
     * @param <T>    泛型
     */
    public static <T> void doIt(boolean isDoIt, Consumer<T> action, T param) {
        if (isDoIt) {
            action.accept(param);
        }
    }
}