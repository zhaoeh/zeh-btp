package zeh.btp.i18n.processor;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.Objects;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-08-08
 **/
public class CheckResponseBodyAdviceProcessor implements SmartInitializingSingleton {

    private final DefaultResponseAdvice responseAdvice;

    private static boolean DEFAULT_SUPPORTS = true;

    public CheckResponseBodyAdviceProcessor(ObjectProvider<DefaultResponseAdvice> defaultResponseAdvice) {
        this.responseAdvice = defaultResponseAdvice.getIfAvailable();
    }

    @Override
    public void afterSingletonsInstantiated() {
        if (Objects.nonNull(responseAdvice)) {
            responseAdvice.setSupports(DEFAULT_SUPPORTS);
        }
    }

    /**
     * 是否支持默认advice
     *
     * @param defaultSupports
     */
    public static void supportsDefaultAdvice(boolean defaultSupports) {
        DEFAULT_SUPPORTS = defaultSupports;
    }
}
