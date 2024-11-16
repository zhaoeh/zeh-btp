package zeh.btp.validation.holder;

import zeh.btp.enums.CustomizedValidationContents;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @description: 校验器message处理
 * @author: ErHu.Zhao
 * @create: 2023-12-30
 **/
public class ConstraintValidatorContextHandler {

    /**
     * 自定义校验message
     *
     * @param context  上下文
     * @param contents 校验器内容容器
     */
    public static void handleConstraintValidatorContext(ConstraintValidatorContext context, CustomizedValidationContents contents) {
        String message = contents.getMessage();
        if (StringUtils.isNotBlank(message)) {
            // 禁用默认message
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
    }
}
