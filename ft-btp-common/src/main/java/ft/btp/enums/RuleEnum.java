package ft.btp.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: sanji
 * @desc: 规则枚举
 * @date: 2024/4/15 14:32
 */
@Getter
public enum RuleEnum {
    RESTRICT_WITHDRAW("RESTRICT_WITHDRAW", "restrict withdraw/限制取款"),
    RESTRICT_BETTING("RESTRICT_BETTING", "restrict betting/限制下注"),
    AUDIT_FREE_WITHDRAW("AUDIT_FREE_WITHDRAW", "audit free withdraw/免审计取款"),
    TRANSFERRED_TO_MANUAL_REVIEW("TRANSFERRED_TO_MANUAL_REVIEW", "transferred to manual review/转人工审核"),
    ;
    private final String ruleAction;

    private final String ruleDesc;

    RuleEnum(String ruleAction, String ruleDesc) {
        this.ruleAction = ruleAction;
        this.ruleDesc = ruleDesc;
    }

    public static Map<String, String> getAllRuleMap() {
        Map<String, String> map = new HashMap<>();
        for (RuleEnum rule : values()) {
            map.put(rule.ruleAction, rule.getRuleDesc());
        }
        return map;
    }
}
