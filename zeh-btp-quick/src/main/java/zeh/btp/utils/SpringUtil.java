package zeh.btp.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        SpringUtil.applicationContext = arg0;
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = applicationContext.getBean(clz);
        return result;
    }
}