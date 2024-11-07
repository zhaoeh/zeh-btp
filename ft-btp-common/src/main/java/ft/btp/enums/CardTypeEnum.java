package ft.btp.enums;

import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Auther: erhu
 * @Date: 2023/3/31 17:14
 * @Description: 证件类型
 */
@Getter
public enum CardTypeEnum {
    //Driver’s license 驾照
    DRIVER_LICENCE("4", "driverLicenseService", "DRIVER'S LICENSE","00630000004"),
    //保险卡 Unified Multi-Purpose ID (UMID)
    UMID("1", "UMIDService", "Unified Multi-Purpose ID","00630000001"),
    //健康卡(菲健保公司身体证) PhilHealth ID
    PHILHEALTH_ID("17", "philHealthIdService", "PhilHealth","00630000024"),
    //纳税人识别号 Tax Identification (TIN)
    TIN("2", "TINService", "BUREAU OF INTERNAL REVENUE","00630000002;00630000036"),//00630000002旧版，00630000036新版
    //身份证 Philippine National ID
    PHILIPPINE_NATIONAL_ID("21", "philippineNationalService", "PAMBANSANG PAGKAKAKILANLAN","00630000033"),
    //选举人身份证 Voter’s ID
    VOTER_ID("6", "voterIdService", "COMMISSION ON ELECTIONS","00630000022"),
    //护照 Passport ----
    PASSPORT("5", "passportService", "PASSPORT","00630000031;00630000032"),//00630000031旧版，00630000032新版
    //社保身份证 Social Security System (SSS) Card,
    SSS_CARD("3", "sssCordService", "Social Security System","00630000020"),
    //邮政身份证 Postal ID
    POSTAL_ID("7", "postalIdService", "POSTAL IDENTITY CARD","00630000016"),
    //ACR I-Card 外国人身份登记卡 Alien Certificate of Registration / Immigrant Certificate of Registration
    ACR_CARD("22", "acrCardService", "ALIEN CERTIFICATE OF REGISTRATION","00630000038"),
    //Other Valid IDs
//    OTHER_VALID_IDS("23", "otherValidIdsService", ""),
    //Other IDs
    //国家调查局无犯罪记录证明 NBI Clearance
    NBI_CLEARANCE("15", "nbiClearanceService", "National Bureau of Investigation",""),
    //海外工作者福利署 OWWA ID
    OWWA_ID("13", "owwaIdService", "Overseas Workers Welfare Administration",""),
    //海员证 Seaman’s Book
    SEAMAN_BOOK("18", "seamanBookService", "",""),

    //武器执照 Firearms License
    FIREARMS_LICENSE("19", "firearmsLicenseService", "FIREARMS LICENCE",""),
    ;

    private String code;
    //服务名字
    private String serviceName;
    //证件名称
    private String cardName;
    //证件名称
    private String zolozType;

    CardTypeEnum(String code, String serviceName, String cardName, String zolozType) {
        this.code = code;
        this.serviceName = serviceName;
        this.cardName = cardName;
        this.zolozType = zolozType;
    }

    public static CardTypeEnum findEnumByCode(String code) {

        for (CardTypeEnum typeEnum : CardTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }

        }
        return null;
    }

    public static CardTypeEnum findEnumByServiceName(String serviceName) {
        for (CardTypeEnum typeEnum : CardTypeEnum.values()) {
            if (typeEnum.getServiceName().equals(serviceName)) {
                return typeEnum;
            }

        }
        return null;
    }

    public static CardTypeEnum findEnumByZolozType(String zolozType) {
        for (CardTypeEnum typeEnum : CardTypeEnum.values()) {
            if (StringUtils.isNotBlank(typeEnum.getZolozType()) &&
                    Arrays.asList(typeEnum.getZolozType().split(";")).contains(zolozType)) {
                return typeEnum;
            }
        }
        return null;
    }
}
