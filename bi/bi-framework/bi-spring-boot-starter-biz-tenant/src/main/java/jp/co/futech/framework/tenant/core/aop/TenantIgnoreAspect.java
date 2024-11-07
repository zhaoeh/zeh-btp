package jp.co.futech.framework.tenant.core.aop;

import jp.co.futech.framework.tenant.core.context.TenantContextHolder;
import jp.co.futech.framework.tenant.core.util.TenantUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 忽略多租户的 Aspect，基于 {@link IgnoreTenantTable} 注解实现，用于一些全局的逻辑。
 * 例如说，一个定时任务，读取所有数据，进行处理。
 * 又例如说，读取所有数据，进行缓存。
 *
 * 整体逻辑的实现，和 {@link TenantUtils#executeIgnore(Runnable)} 需要保持一致
 *
 * @author futech.co.jp
 */
@Aspect
@Slf4j
public class TenantIgnoreAspect {

    @Around("@annotation(ignoreTenantTable)")
    public Object around(ProceedingJoinPoint joinPoint, IgnoreTenantTable ignoreTenantTable) throws Throwable {
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setIgnore(true);
            // 执行逻辑
            return joinPoint.proceed();
        } finally {
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

}
