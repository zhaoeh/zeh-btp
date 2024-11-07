package ft.btp.enums;

/**
 * @description: 搜索类型枚举
 * @author: ErHu.Zhao
 * @create: 2024-01-05
 **/
public enum SearchTypeEnum {
    LOGIN_NAME("1"), CUSTOMER_ID("2"), PHONE("3"), EMAIL("4"), NICK_NAME("5");

    private String searchType;

    SearchTypeEnum(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }
}
