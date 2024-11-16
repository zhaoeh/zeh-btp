package zeh.btp.simple.core;

import zeh.btp.simple.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: 分布式锁aop
 * @author: ErHu.Zhao
 * @create: 2024-08-19
 **/
@Aspect
@Slf4j
public class DistributedLockAop {

    private static final String LOCK_PREFIX = "DISTRIBUTED_LOCK:";

    private final RedisTemplate redisTemplate;

    public DistributedLockAop(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(distributedLock)")
    public void disLock(DistributedLock distributedLock) {
    }

    @Around("disLock(distributedLock)")
    public Object doLock(ProceedingJoinPoint proceedingJoinPoint, DistributedLock distributedLock) throws Throwable {
        String lockKey = distributedLock.value();
        if (StringUtils.isBlank(lockKey)) {
            return proceedingJoinPoint.proceed();
        }
        long expire = distributedLock.expire();
        // value值
        String lockValue = UUID.randomUUID().toString();
        // 尝试获取锁
        boolean lockAcquired = acquireLock(lockKey, lockValue, expire);
        if (!lockAcquired) {
            log.info("获取分布式锁失败，终止本次执行，lockKey is {}", lockKey);
            return null;
        }
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            releaseLock(lockKey, lockValue);
        }
    }

    /**
     * 获取锁
     *
     * @param lockKey
     * @param lockValue
     * @param expire
     * @return
     */
    private boolean acquireLock(String lockKey, String lockValue, long expire) {
        String key = LOCK_PREFIX + lockKey;
        return redisTemplate.opsForValue().setIfAbsent(key, lockValue, expire, TimeUnit.MINUTES);
    }

    /**
     * 释放锁
     *
     * @param lockKey
     * @param lockValue
     */
    public void releaseLock(String lockKey, String lockValue) {
        String key = LOCK_PREFIX + lockKey;
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(currentValue) && currentValue.equals(lockValue)) {
            Boolean result = redisTemplate.delete(key);
            if (result) {
                log.info("删除分布式锁成功,key is {},value is {}", key, lockValue);
                return;
            }
        }
        log.error("删除分布式锁失败,key is {},value is {}", key, lockValue);

    }
}
