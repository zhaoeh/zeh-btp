package zeh.btp.enums;

import java.util.Objects;

public enum SiteEnum {

    AP(1, "Bingoplus","siteB"),
    BP(2, "Arenaplus","siteA"),
    GP(3, "Gameplus","siteG");

    private Integer siteId;

    private String siteName;

    private String configParamName;

    SiteEnum(Integer siteId, String siteName, String configParamName) {
        this.siteId = siteId;
        this.siteName = siteName;
        this.configParamName = configParamName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getConfigParamName() {
        return configParamName;
    }


    public static SiteEnum getById(Integer siteId) {

        if (Objects.isNull(siteId)) {
            throw new IllegalArgumentException("Invalid value for SiteEnum: siteId can't be blank!");
        }

        for (SiteEnum siteEnum : SiteEnum.values()) {
            if (siteEnum.siteId.equals(siteId)) {
                return siteEnum;
            }
        }
        return null;
    }

    public static String getConfigParamNameById(Integer siteId) {
        SiteEnum siteEnum = getById(siteId);
        return siteEnum==null ? null : siteEnum.getConfigParamName();
    }

}
