package ft.btp.validation.validator;


import ft.btp.enums.CustomizedValidationContents;
import ft.btp.validation.annotation.EnumContentsValid;
import ft.btp.validation.holder.ConstraintValidatorContextHandler;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @ClassName EnumValidtor
 * @Description 校验入参是否为指定enum的值的实现方法
 * @Author TJSAustin
 * @Date 2023/5/20 10:00
 * @Version 1.0
 **/
public class EnumContentsValidtor implements ConstraintValidator<EnumContentsValid, Object> {
    private Class<?> cls;
    private Object[] values;
    private CustomizedValidationContents contents;

    @Override
    public void initialize(EnumContentsValid constraintAnnotation) {
        cls = constraintAnnotation.enumValue();
        contents = constraintAnnotation.contents();
        values = contents.getAcceptValues();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        if (cls.isEnum()) {
            Object[] objects = cls.getEnumConstants();
            for (Object obj : objects) {
                //此处方法getCode需要根据自己项目枚举的命名而变化
                Method method = cls.getDeclaredMethod("getCode");
                String expectValue = String.valueOf(method.invoke(obj));
                if (expectValue.equals(String.valueOf(value))) {
                    return true;
                }
            }
        } else {
            if (matches(values, value)) {
                return true;
            }
        }
        ConstraintValidatorContextHandler.handleConstraintValidatorContext(context, contents);
        return false;
    }

    /**
     * 判断当前参数是否符合校验
     *
     * @param values 预期取值范围
     * @param value  当前参数
     * @return 是否符合校验 true：符合校验 false：不符合校验
     */
    private boolean matches(Object[] values, Object value) {
        return Arrays.asList(values).stream().anyMatch(v -> String.valueOf(v).equals(String.valueOf(value)));
    }

}
