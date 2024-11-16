package zeh.btp.enums;

import lombok.Getter;

@Getter
public enum WithdrawalRiskPromptEnum {

    PASS(0, ""),

    RECEIVE_TOO_LARGE(1, "receive too large"),

    WITHDRAWAL_TOO_LARGE(2, "withdrawal too large");

    private final int type;
    private final String prompt;

    WithdrawalRiskPromptEnum(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }
}
