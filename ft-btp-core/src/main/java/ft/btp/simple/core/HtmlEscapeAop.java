package ft.btp.simple.core;

import ft.btp.simple.escape.EnableHtmlEscape;
import ft.btp.simple.escape.HtmlEscape;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.util.HtmlUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @description: 对入参对象中标注HtmlEscape的字段且为String类型的，进行html转义，防止xss攻击
 * @author: ErHu.Zhao
 * @create: 2024-08-19
 **/
@Aspect
@Slf4j
public class HtmlEscapeAop {

    @Pointcut("@annotation(enableHtmlEscape)")
    public void escape(EnableHtmlEscape enableHtmlEscape) {
    }

    @SneakyThrows
    @Before("escape(enableHtmlEscape)")
    public void htmlEscape(JoinPoint joinPoint, EnableHtmlEscape enableHtmlEscape) {
        Object[] args = joinPoint.getArgs();
        if (Objects.isNull(args)) {
            return;
        }
        for (Object arg : args) {
            checkFields(arg);
        }

    }

    private void checkFields(Object arg) throws IllegalAccessException {
        if (Objects.isNull(arg)) {
            return;
        }
        Field[] fields = arg.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (String.class.equals(field.getType()) && field.isAnnotationPresent(HtmlEscape.class)) {
                field.setAccessible(true);
                Object value = field.get(arg);
                if (Objects.isNull(value)) {
                    return;
                }
                String valueInUse = Objects.toString(value);
                if (StringUtils.isBlank(valueInUse)) {
                    return;
                }
                field.set(arg, HtmlUtils.htmlEscape(valueInUse));
            }
        }
    }
}
