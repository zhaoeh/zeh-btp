package ft.btp.i18n.interpolator;

import jakarta.validation.MessageInterpolator;
import ft.btp.i18n.core.I18nLocaleWrapper;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 自定义消息插值器，用于对原始消息进行插值替换，替换值采用messageSource从国际化资源中获取
 * @author: ErHu.Zhao
 * @create: 2024-07-10
 **/
public class CustomizeMessageInterpolator {

    private MessageInterpolator messageInterpolator;

    private MessageInterpolator.Context context;

    public CustomizeMessageInterpolator(MessageSource messageSource) {
        /**
         * 采用MessageInterpolatorFactory工厂对象，返回MessageSourceMessageInterpolator插值器，自带国际化处理
         */
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory(messageSource);
        this.messageInterpolator = interpolatorFactory.getObject();
        context = CustomizedDefaultContext.of();
    }

    /**
     * 进行消息插值替换，使用当前请求中的Locale（替换插值表达式，即{}包裹的内容）
     *
     * @param term 原始消息
     * @return 插值替换后的消息
     */
    public String interpolate(String term, Map<String, Object> params) {
        MessageInterpolator.Context contextToUse = Objects.isNull(params) ? context : CustomizedDefaultContext.of(params);
        return messageInterpolator.interpolate(term, contextToUse);
    }

    /**
     * 进行消息插值替换，使用当前环境的Locale
     *
     * @param term 原始消息
     * @return 插值替换后的消息
     */
    public String interpolateOfCurrentLocal(String term, Map<String, Object> params) {
        MessageInterpolator.Context contextToUse = Objects.isNull(params) ? context : CustomizedDefaultContext.of(params);
        return messageInterpolator.interpolate(term, contextToUse, I18nLocaleWrapper.obtainLocale());
    }

    /**
     * 进行消息插值替换，使用指定的Locale
     *
     * @param term
     * @param params
     * @param locale
     * @return
     */
    public String interpolateOfLocal(String term, Map<String, Object> params, Locale locale) {
        MessageInterpolator.Context contextToUse = Objects.isNull(params) ? context : CustomizedDefaultContext.of(params);
        return messageInterpolator.interpolate(term, contextToUse, locale);
    }
}
