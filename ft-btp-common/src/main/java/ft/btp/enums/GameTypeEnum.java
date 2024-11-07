package ft.btp.enums;


public enum GameTypeEnum {

    Sport("Sports", "sportsPercentage", 1),
    Sport_22("Sports", "sportsPercentage", 22),
    Poker("Poker", "pokerPercentage", 3),
    Slot("Slot", "slotPercentage", 5),
    Bingo("Bingo", "bingoPercentage", 27),
    Ebingo("EBingo", "ebingoPercentage", 21),
    Casino("Casino", "casinoPercentage", 17),
    Fishing("Fishing", "fishingPercentage", 8),
    Specialty("Specialty Game", "specialty game", 30),
    All("All", "allGamesPercentage", 0),
    SUMMARY("Summary", "summary", -1),

    //不属于游戏类型，用于计算用户订单数据时的特殊类型   99999 存款，99998取款，99997首存
    DEPOSIT("Deposit", "存款", 9999),
    WITHDRAW("Withdraw", "取款", 9998);
//    FIRST_DEPOSIT("First_deposit","首存金额",99997);

    private String name;

    private String des;

    private Integer value;

    GameTypeEnum(String name, String des, Integer value) {
        this.name = name;
        this.des = des;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public Integer getValue() {
        return value;
    }

    public static String getNameByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (GameTypeEnum typeEnum : values()) {
            if (typeEnum.getValue().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
