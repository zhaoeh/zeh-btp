package zeh.btp.feign.container;

import org.springframework.core.NamedThreadLocal;

import java.util.Objects;

/**
 * @description: header上下文持有者，将header上下文转存于当前线程中
 * @author: Erhu.Zhao
 * @create: 2023-10-27 18:52
 */
public class HeaderContextHolder {

    private static final ThreadLocal<HeaderContext> headerContextHolder = new NamedThreadLocal("HeaderContext");

    public static void resetHeaderContext() {
        headerContextHolder.remove();
    }

    public static void setHeaderContext(HeaderContext headerContext) {
        if (Objects.isNull(headerContext)) {
            resetHeaderContext();
        } else {
            headerContextHolder.set(headerContext);
        }
    }

    public static HeaderContext getHeaderContext() {
        HeaderContext headerContext = headerContextHolder.get();
        return headerContext;
    }

    public static void clearHeaderContext() {
        headerContextHolder.remove();
    }

}