package zeh.btp.scope.holder;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @description: refresh scope holder
 * @author: ErHu.Zhao
 * @create: 2024-08-29
 **/
public class RefreshScopeHolder {

    private static final ThreadLocal<String> refreshScopeHolder = new NamedThreadLocal("refresh scope");

    private static final ThreadLocal<String> inheritableRefreshScopeHolder = new NamedInheritableThreadLocal("inheritable refresh scope");

    public static void resetScope() {
        refreshScopeHolder.remove();
        inheritableRefreshScopeHolder.remove();
    }

    public static boolean isReset() {
        return Objects.isNull(getScope());
    }

    public static void setScope(@Nullable String flag) {
        setScope(flag, false);
    }

    public static void setScope(@Nullable String flag, boolean inheritable) {
        if (!StringUtils.hasText(flag)) {
            resetScope();
        } else if (inheritable) {
            inheritableRefreshScopeHolder.set(flag);
            refreshScopeHolder.remove();
        } else {
            refreshScopeHolder.set(flag);
            inheritableRefreshScopeHolder.remove();
        }

    }

    @Nullable
    public static String getScope() {
        String scope = refreshScopeHolder.get();
        if (!StringUtils.hasText(scope)) {
            scope = inheritableRefreshScopeHolder.get();
        }
        return scope;
    }
}
