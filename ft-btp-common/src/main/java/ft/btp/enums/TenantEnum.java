package ft.btp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author: yueds
 * @desc: 血缘标记（BP、AP、 GP、PG、SP）
 * @date: 2024/10/8 14:44
 */
@AllArgsConstructor
@Getter
public enum TenantEnum {
    //BP、AP、 GP、PG、SP
    BP("BP"),
    AP("AP"),
    GP("GP"),
    PG("PG"),
    SP("SP");

    private final String name;
}
