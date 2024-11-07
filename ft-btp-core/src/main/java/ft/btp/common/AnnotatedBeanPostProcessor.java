package ft.btp.common;

import ft.btp.utils.CustomizedAnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @description: 注解标注位置校验器
 * @author: ErHu.Zhao
 * @create: 2024-08-19
 **/
public class AnnotatedBeanPostProcessor implements BeanPostProcessor {

    private final Class<? extends Annotation> annotationClass;

    public AnnotatedBeanPostProcessor(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        boolean isController = CustomizedAnnotationUtils.isController(beanClass);
        Arrays.stream(beanClass.getDeclaredMethods()).forEach(method -> {
            if (CustomizedAnnotationUtils.isApplyOnMethod(method, annotationClass) && !isController) {
                throw new IllegalStateException(annotationClass.getName() + " can only annotated on method in controller");
            }
        });
        return bean;
    }

}
