package ft.btp.validation.annotation.validate;

/**
 * @description: 枚举值校验注解
 * @author: ErHu.Zhao
 * @create: 2024-07-02
 **/
public @interface EnumValid {

    Class<? extends Enum> enumClass();

    String enumField();

    String message() default "{000000011}";
}
