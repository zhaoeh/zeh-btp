package zeh.btp.enums;

/**
 * @description: 敏感字段枚举
 * @author: ErHu.Zhao
 * @create: 2024-04-26
 **/
public enum SensitiveFields {

    loginPassword("loginPassword"), password("password"), newPassword("newPassword"),
    oldPassword("oldPassword"), withdrawalPassword("withdrawalPassword"), mobileNo("mobileNo");
    private String name;

    SensitiveFields(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
