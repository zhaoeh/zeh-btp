package ft.btp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: CustomerFlagEnum
 * @description: TODO
 * @author: Jojo
 * @create: 2023/10/30-17:13
 **/

@AllArgsConstructor
@Getter
public enum CustomerFlagEnum {

    one(1,"loginName"),
    two(2,"phone"),
    three(3,"realName"),
    four(4,"loginName"),
    five(5,"facebookId"),
    six(6,"email"),
    seven(7,"loginName"),
    eleven(11,"phone"),
    ninetyNine(99,"phone"),
    ;

    private int flag;
    private String field;

    public static CustomerFlagEnum findEnumByFlag(Integer flag) {
        for (CustomerFlagEnum typeEnum : CustomerFlagEnum.values()) {
            if (typeEnum.getFlag()==flag) {
                return typeEnum;
            }
        }
        return null;
    }
}
