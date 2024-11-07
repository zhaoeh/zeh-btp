package ft.btp.scope.hook;

import ft.btp.scope.builder.IocBeanBuilder;
import org.springframework.lang.Nullable;

/**
 * @description: bean作用域管理器
 * @author: ErHu.Zhao
 * @create: 2024-08-23
 **/
public interface BeanScopeManager {

    /**
     * 从自定义作用域中获取目标ben
     *
     * @param name
     * @param iocBeanBuilder
     * @return
     */
    Object get(String name, IocBeanBuilder iocBeanBuilder);

    /**
     * 从自定义作用域中删除bean实例
     *
     * @param name
     * @return
     */
    @Nullable
    Object remove(String name);
}
