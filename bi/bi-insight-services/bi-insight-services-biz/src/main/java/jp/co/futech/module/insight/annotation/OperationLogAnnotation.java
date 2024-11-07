package jp.co.futech.module.insight.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogAnnotation {
    String operModule() default "";//操作模块
    int operType() default 0;//操作类型
    String operDesc() default "";//操作说明
    //String objectId() default "";
}