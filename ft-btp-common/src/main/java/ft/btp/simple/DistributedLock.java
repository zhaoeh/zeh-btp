package ft.btp.simple;

import java.lang.annotation.*;

/**
 * @description: 分布式锁注解
 * @author: ErHu.Zhao
 * @create: 2024-08-19
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    // 锁的键名
    String value();

    // 锁的过期时间，默认10分钟
    long expire() default 10;
}
