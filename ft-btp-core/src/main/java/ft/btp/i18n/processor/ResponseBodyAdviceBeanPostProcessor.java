package ft.btp.i18n.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @description: 针对ResponseBodyAdvice的BeanPostProcessor，由于某些业务侧通过@Bean的方式注册ResponseBodyAdvice，导致条件注解失效（@Bean返回值类型不是父类型）
 * @author: ErHu.Zhao
 * @create: 2024-08-08
 **/
public class ResponseBodyAdviceBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (Objects.nonNull(bean) && ResponseBodyAdvice.class.isAssignableFrom(bean.getClass())) {
            if (bean.getClass() != DefaultResponseAdvice.class) {
                CheckResponseBodyAdviceProcessor.supportsDefaultAdvice(false);
            }
        }
        return bean;
    }

}
