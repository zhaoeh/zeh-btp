package jp.co.futech.module.system.context;

import jp.co.futech.module.system.dal.dataobject.user.AdminUserDO;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

/**
 * @description: admin user线程持有器
 * @author: ErHu.Zhao
 * @create: 2024-05-30
 **/
public class AdminUserHolder {

    private static final ThreadLocal<AdminUserDO> adminUserDoHolder = new NamedThreadLocal("Admin user do");
    private static final ThreadLocal<AdminUserDO> inheritableadminUserDoHolder = new NamedInheritableThreadLocal("Admin user");

    public AdminUserHolder() {
    }

    public static void resetAdminUser() {
        adminUserDoHolder.remove();
        inheritableadminUserDoHolder.remove();
    }

    public static void setAdminUser(@Nullable AdminUserDO adminUser) {
        setAdminUser(adminUser, false);
    }

    public static void setAdminUser(@Nullable AdminUserDO adminUser, boolean inheritable) {
        if (adminUser == null) {
            resetAdminUser();
        } else if (inheritable) {
            inheritableadminUserDoHolder.set(adminUser);
            adminUserDoHolder.remove();
        } else {
            adminUserDoHolder.set(adminUser);
            inheritableadminUserDoHolder.remove();
        }

    }

    @Nullable
    public static AdminUserDO getAdminUser() {
        AdminUserDO adminUser = (AdminUserDO) adminUserDoHolder.get();
        if (adminUser == null) {
            adminUser = (AdminUserDO) inheritableadminUserDoHolder.get();
        }

        return adminUser;
    }

}
