package jp.co.futech.framework.web.core.rpc;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jp.co.futech.framework.web.core.holder.TransmittableLocaleHolder;
import lombok.extern.slf4j.Slf4j;

import static jp.co.futech.framework.web.core.util.WebFrameworkUtils.ACCEPT_LANGUAGE;

/**
 * Accept-Language在feign之间进行透传
 */
@Slf4j
public class LocaleInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String locale = TransmittableLocaleHolder.getLocale();
        if (locale != null) {
            requestTemplate.header(ACCEPT_LANGUAGE, locale);
        }
    }

}
