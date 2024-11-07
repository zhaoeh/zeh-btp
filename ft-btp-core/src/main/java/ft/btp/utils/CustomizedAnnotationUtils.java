package ft.btp.utils;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @description: 注解工具类
 * @author: ErHu.Zhao
 * @create: 2024-08-19
 **/
public class CustomizedAnnotationUtils {

    public static boolean isController(Class<?> targetClass) {
        Assert.notNull(targetClass, "targetClass cannot be null");
        return targetClass.isAnnotationPresent(Controller.class) || targetClass.isAnnotationPresent(RestController.class);
    }

    public static boolean isApplyOnMethod(Method method, Class<? extends Annotation> annotationClass) {
        Assert.notNull(method, "method cannot be null");
        Assert.notNull(annotationClass, "annotationClass cannot be null");
        return method.isAnnotationPresent(annotationClass);
    }

    public static void withOutOnController(Class<?> targetClass, Method method, Class<? extends Annotation> annotationClass) {
        if (isApplyOnMethod(method, annotationClass) && !isController(targetClass)) {
            throw new IllegalStateException(annotationClass.getName() + " can only annotated on method in controller");
        }
    }

}
