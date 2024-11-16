package zeh.btp.enums;

/**
 * @description: 综合门店枚举
 * @author: ErHu.Zhao
 * @create: 2024-04-16
 **/
public enum ComprehensiveBranchEnum {

    NewFarmersPlaza("000090", "New Farmers Plaza"),
    ExtremeTimog("000047", "Extreme Timog"),
    GTCGreenhils("000048", "GTC Greenhils"),
    StaLuciaEastMall("000075", "Sta. Lucia East Mall"),
    SMBacolod("000054", "SM Bacolod");

    private String branchCode;

    private String branchName;

    ComprehensiveBranchEnum(String branchCode, String branchName) {
        this.branchCode = branchCode;
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getBranchName() {
        return branchName;
    }
}
