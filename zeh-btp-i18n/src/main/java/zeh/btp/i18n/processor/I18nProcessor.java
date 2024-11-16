package zeh.btp.i18n.processor;

import zeh.btp.i18n.annotation.EnableI18nField;
import zeh.btp.i18n.annotation.EnableI18nWeavingForResponse;
import zeh.btp.i18n.annotation.I18nField;
import zeh.btp.i18n.api.I18nCodeMapper;
import zeh.btp.i18n.api.I18nHandler;
import ft.btp.i18n.core.*;
import zeh.btp.i18n.core.*;
import zeh.btp.i18n.info.DynamicParams;
import zeh.btp.i18n.interpolator.CustomizeMessageInterpolator;
import zeh.btp.i18n.tools.TypeUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @description: 国际化插值处理器
 * @author: ErHu.Zhao
 * @create: 2024-07-10
 **/
public class I18nProcessor {

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    private CustomizeMessageInterpolator customizeMessageInterpolator;

    private static CustomizeMessageInterpolator staticCustomizeMessageInterpolator;

    private final I18nMessageWrapper i18nMessageWrapper;

    private final I18nConfigFinder configFinder;

    private final List<I18nMessagePostProcessor> i18nMessagePostProcessor;

    public I18nProcessor(ObjectProvider<CustomizeMessageInterpolator> customizeMessageInterpolator,
                         ObjectProvider<I18nMessageWrapper> i18nMessageWrapper,
                         ObjectProvider<I18nConfigFinder> codeFinder,
                         List<I18nMessagePostProcessor> i18nMessagePostProcessor) {
        this.customizeMessageInterpolator = customizeMessageInterpolator.getIfAvailable();
        this.i18nMessagePostProcessor = i18nMessagePostProcessor;
        staticCustomizeMessageInterpolator = this.customizeMessageInterpolator;
        this.i18nMessageWrapper = i18nMessageWrapper.getIfAvailable();
        this.configFinder = codeFinder.getIfAvailable();
    }

    /**
     * 工具方法，手动处理i18n国际化，使用当前环境的Locale
     *
     * @param original
     * @param places
     * @return
     */
    public static String i18nProcess(String original, @Nullable Map<String, Object> places) {
        if (!StringUtils.hasText(original)) {
            return original;
        }
        return Optional.ofNullable(staticCustomizeMessageInterpolator).map(e -> e.interpolateOfCurrentLocal(original, places)).orElse(original);
    }

    /**
     * 工具方法，手动处理i18n国际化，使用指定Locale
     *
     * @param original
     * @param places
     * @param locale
     * @return
     */
    public static String i18nProcess(String original, @Nullable Map<String, Object> places, Locale locale) {
        if (!StringUtils.hasText(original)) {
            return original;
        }
        return Optional.ofNullable(staticCustomizeMessageInterpolator).map(e -> e.interpolateOfLocal(original, places, locale)).orElse(original);
    }

    /**
     * 对响应实体进行国际化处理
     *
     * @param result  response body
     * @param weaving EnableI18nWeavingForResponse
     */
    public void processI18nResult(Object result, EnableI18nWeavingForResponse weaving) {
        if (Objects.isNull(result)) {
            return;
        }
        if (Objects.isNull(customizeMessageInterpolator) ||
                Objects.isNull(configFinder) ||
                Objects.isNull(i18nMessageWrapper)) {
            return;
        }
        if (Objects.nonNull(weaving)) {
            if (I18nHandler.class == weaving.i18nHandler()) {
                return;
            }
            I18nHandler i18nHandler = configFinder.findHandler(weaving.i18nHandler());
            Assert.notNull(i18nHandler, "cannot find I18nHandler instance in spring container");
            i18nHandler.handleResult(result, new I18nMessageConverter(configFinder, i18nMessageWrapper, weaving.mapperConfigure()));
        }
    }


    /**
     * 处理响应体body，字段国际化
     *
     * @param body body
     */
    public void processI18nField(Object body, Class<? extends I18nCodeMapper> mapper) {
        if (Objects.isNull(body)) {
            return;
        }
        if (Objects.isNull(customizeMessageInterpolator) ||
                Objects.isNull(configFinder) ||
                Objects.isNull(i18nMessageWrapper)) {
            return;
        }
        Class<?> instanceClass = body.getClass();
        // 请注意，对于基本类型和对应的包装类型，不做任何处理
        if (TypeUtils.isArray(instanceClass)) {
            // 处理数组类型
            processArray(body, mapper);
        } else if (TypeUtils.isList(instanceClass)) {
            // 处理list类型
            processList((List<?>) body, mapper);
        } else if (!TypeUtils.isSimple(instanceClass)) {
            // 处理普通对象类型
            processInstance(body, mapper);
        }
    }

    private void processInstance(Object instance, Class<? extends I18nCodeMapper> mapper) {
        Class<?> instanceClass = instance.getClass();
        if (instanceClass.isAnnotationPresent(EnableI18nField.class)) {
            Map<String, Object> targetFieldParams = null;
            Field[] fields = instanceClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(instance);
                    Object replaceValue = null;
                    if (Objects.nonNull(value)) {
                        Class<?> fieldType = value.getClass();
                        // 如果成员是简单类型并且存在I18nFiled注解，则直接进行国际化处理
                        if (TypeUtils.isSimple(fieldType) && field.isAnnotationPresent(I18nField.class)) {
                            I18nField i18NField = field.getAnnotation(I18nField.class);
                            String targetI18nFieldName = i18NField.i18nField();
                            Field targetField = null;
                            if (StringUtils.hasText(targetI18nFieldName)) {
                                targetField = instanceClass.getDeclaredField(targetI18nFieldName);
                                targetField.setAccessible(true);
                            }
                            String targetCode = findTargetCode(mapper, Objects.toString(value), targetField, instance);
                            if (Objects.nonNull(targetCode)) {
                                if (Objects.isNull(targetFieldParams) && instance instanceof DynamicParams) {
                                    targetFieldParams = ((DynamicParams) instance).provideParams();
                                }
                                String origin = targetCode;
                                String placed = customizeMessageInterpolator.interpolateOfCurrentLocal(origin, targetFieldParams);
                                if (origin.equals(placed)) {
                                    continue;
                                }
                                replaceValue = placed;
                            }

                            if (Objects.nonNull(replaceValue)) {
                                replaceValue = applyPostProcessAfterI18nConvert(targetCode, Objects.toString(replaceValue));
                                field.set(instance, replaceValue);
                            }
                        } else {
                            // 否则，认为成员是对象类型，嵌套进行处理
                            processI18nField(value, mapper);
                        }
                    }

                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理数组
     *
     * @param array 数组对象
     */
    private void processArray(Object array, Class<? extends I18nCodeMapper> mapper) {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            if (Objects.nonNull(element)) {
                processI18nField(element, mapper);
            }
        }
    }

    /**
     * 处理列表
     *
     * @param list
     */
    private void processList(List<?> list, Class<? extends I18nCodeMapper> mapper) {
        for (Object element : list) {
            processI18nField(element, mapper);
        }
    }

    /**
     * 查找目标code（带有插值表达式的code）
     *
     * @param mapper
     * @param message
     * @param targetField
     * @param instance
     * @return
     * @throws IllegalAccessException
     */
    private String findTargetCode(Class<? extends I18nCodeMapper> mapper, String message, Field targetField, Object instance) throws IllegalAccessException {
        String targetCode;
        if (Objects.nonNull(targetField)) {
            Object targetFieldValue = targetField.get(instance);
            targetCode = Optional.ofNullable(targetFieldValue).map(Objects::toString).orElse(null);
        } else {
            String i18nCode = configFinder.findCode(mapper, message);
            targetCode = Optional.ofNullable(i18nCode).map(code -> "{" + code + "}").orElse(null);
        }
        return targetCode;
    }

    /**
     * 执行国际化后置处理
     *
     * @param code
     * @param valueAfterI18n
     * @return
     */
    private String applyPostProcessAfterI18nConvert(String code, String valueAfterI18n) {
        if (CollectionUtils.isEmpty(i18nMessagePostProcessor)) {
            return valueAfterI18n;
        }
        String result = valueAfterI18n;
        String current;
        for (Iterator<I18nMessagePostProcessor> post = this.i18nMessagePostProcessor.iterator(); post.hasNext(); result = current) {
            I18nMessagePostProcessor processor = post.next();
            Locale locale = I18nLocaleWrapper.obtainLocale();
            if (processor.supports(locale)) {
                current = processor.postProcessAfterI18nConvert(locale, placeholderHelper.replacePlaceholders(code, s -> s), valueAfterI18n);
                if (current == null) {
                    return result;
                }
            } else {
                current = result;
            }
        }
        return result;
    }

}
