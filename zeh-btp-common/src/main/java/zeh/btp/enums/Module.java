package zeh.btp.enums;

import java.util.Arrays;

public enum Module {
    NEPTUNE_WEB("neptune_web", "neptune_web:", "com.jazz.neptune.Application", "com.jazz.neptune.biz.util.HttpClientUtil"),
    NEPTUNE_GROUP("neptune_group", "neptune_group:", "com.jazz.saturn.Application", "com.jazz.saturn.util.HttpClientUtil"),
    NEPTUNE_OFFICE("neptune_office", "neptune_web:", "com.jazz.vesta.Application", ""),
    NEPTUNE_GROUP_OFFICE("neptune_group_office", "neptune_group:", "com.jazz.ceres.Application", "");

    private final String name;
    private final String cacheKey;
    private final String mainClass;
    private final String httpClientUtilPath;

    Module(String name, String cacheKey, String mainClass, String httpClientUtilPath) {
        this.name = name;
        this.cacheKey = cacheKey;
        this.mainClass = mainClass;
        this.httpClientUtilPath = httpClientUtilPath;
    }

    public String getHttpClientUtilPath() {
        return httpClientUtilPath;
    }

    public String getName() {
        return this.name;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public String getMainClass() {
        return mainClass;
    }

    public static Module getModuleByName(String name) {
        return Arrays.stream(Module.values()).filter(m -> m.getName().equals(name)).findFirst().get();
    }
}