package com.dynamic.model.core;

import com.dynamic.model.annotation.MayBeBlendModel;
import com.dynamic.model.context.BlendModelContextHolder;
import com.dynamic.model.context.BlendModelRoutedContextHolder;
import com.dynamic.model.context.ModelContextHolder;
import com.dynamic.model.enums.BizModel;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Aspect
public class MayBeBlendModelAround {

    private final List<BizModel> validModels = BizModel.getValidModels();

    /**
     * 定义切点，拦截标注了DSWeaver注解的方法*
     *
     * @param mbbm 注解对象
     */
    @Pointcut("@annotation(mbbm)")
    public void mbbmPoint(MayBeBlendModel mbbm) {
    }

    @Around("mbbmPoint(mbbm)")
    public Object around(ProceedingJoinPoint joinPoint, MayBeBlendModel mbbm) throws Throwable {
        if (ModelContextHolder.isIgnore()) {
            // 对于全局忽略动态模式切换的请求，直接放行走原始逻辑即可，不做代理逻辑织入
            return joinPoint.proceed();
        }

        if (BooleanUtils.isTrue(BlendModelRoutedContextHolder.isRouted())) {
            // 已经被外层代理过，则内层 MayBeBlendModel 标注的方法不再被代理，直接走原生方法
            return joinPoint.proceed();
        }
        Object result = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 目标方法的唯一标识
        String currentMethodFlag = method.toGenericString();
        String blend = BlendModelContextHolder.getBlend();
        if (Objects.nonNull(blend) && !StringUtils.equals(blend, currentMethodFlag)) {
            // 处理嵌套织入逻辑：确保同一个线程的调度路径中，只有栈底首次命中的方法会成功织入，其他下游路径中的方法将不再被代理
            // 上下文存在 blend，说明当前线程之前已经有方法被织入代理逻辑了
            // 同时判断之前织入的方法，是否和当前方法唯一限定不同
            // 同时满足上述两个条件，则终止再次代理。简单理解：当前的方法被代理时，发现之前调用路径中的方法已经代理过了，则栈顶的方法不再进行混合代理
            return joinPoint.proceed();
        }

        BizModel model = ModelContextHolder.get();
        // 上下文中是混合模式
        if (Objects.equals(model, BizModel.BLEND)) {
            BlendModelContextHolder.setBlend(currentMethodFlag, true);
            try {
                for (BizModel m : validModels) {
                    // 多模式分别设置上下文标记循环调度，返回最后一次调度的结果即可
                    ModelContextHolder.set(m);
                    result = joinPoint.proceed();
                }

                // 还原上下文模式
                ModelContextHolder.set(BizModel.BLEND);
            } finally {
                // 清理
                BlendModelContextHolder.resetBlend();
            }

        } else {
            result = joinPoint.proceed();
        }

        return result;
    }

}
