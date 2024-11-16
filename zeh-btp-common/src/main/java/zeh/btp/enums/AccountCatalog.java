package zeh.btp.enums;

/**
 * @author Herman.T
 */

public enum AccountCatalog {

    BANK_CARD("BANK_CARD", "0"),
    BTC("BTC", "1"),
    AMTC("AMTC", "2"),
    USDT("USDT", "3"),
    MBTC("MBTC", "4"),
    ETH("ETH", "5"),
    ALIPAY_A("ALIPAY_A", "6"),
    ALIPAY_Q("ALIPAY_Q", "7"),
    BITOLL("BITOLL", "8"),
    DCBOX("DCBOX", "9"),
    GCASH_A("GCASH_A", "11"),
    PAYMAYA_A("PAYMAYA_A", "12"),
    GLIFE_A("GLIFE_A", "13"),
    LAZADA_A("LAZADA_A", "14"),

    ZAKZAK("ZAKZAK", "10");
    private String accountType;

    private String catalog;

    public String getAccountType() {
        return accountType;
    }

    public String getCatalog() {
        return catalog;
    }

    AccountCatalog(String accountType, String catalog) {
        this.accountType = accountType;
        this.catalog = catalog;
    }

    /**
     * 默认返回银行卡
     *
     * @param accountType
     * @return
     */
    public static AccountCatalog getByAccountType(String accountType) {
        for (AccountCatalog catalog : AccountCatalog.values()) {
            if (catalog.getAccountType().equalsIgnoreCase(accountType)) {
                return catalog;
            }
        }
        return BANK_CARD;
    }
}
