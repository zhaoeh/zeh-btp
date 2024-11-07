package ft.btp.validation.validator.spel;

import ft.btp.validation.annotation.spel.SpELConditionExpression;
import ft.btp.validation.annotation.spel.SpELExpression;
import ft.btp.validation.annotation.spel.SpELValid;
import ft.btp.validation.po.MessagesPlace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * @description: SpEL校验器
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
public class SpELValidator implements ConstraintValidator<SpELValid, Object> {

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    private SpELValid spELValid;

    private ExpressionParser parser;

    @Override
    public void initialize(SpELValid spELValid) {
        this.spELValid = spELValid;
        this.parser = new SpelExpressionParser();
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
        if (Objects.isNull(target)) {
            return true;
        }
        Class<?> targetClass = target.getClass();
        boolean isAnnotatedForClass = isAnnotatedForClass(context, targetClass);
        String fieldName = Optional.ofNullable(targetClass).
                filter(aClass -> isAnnotatedForClass).
                map(Class::getDeclaredFields).map(Arrays::stream).
                map(Stream::findFirst).orElse(Optional.empty()).
                map(Field::getName).orElse(null);
        final List<MessagesPlace> result = new ArrayList<>();
        valid(target, result);
        return isValid(context, result, fieldName);
    }

    private MessagesPlace handleRangeMessage(SpELExpression spELValid) {
        String message = StringUtils.isNotBlank(spELValid.message()) ? spELValid.message() : "SpEL expression evaluation failed";
        return MessagesPlace.builder().message(message).build();
    }

    private boolean isValid(ConstraintValidatorContext context, final List<MessagesPlace> result, final String fieldName) {
        if (result.size() > 0) {
            MessagesPlace message = result.get(0);
            if (Objects.nonNull(message)) {
                // 禁用默认message
                context.disableDefaultConstraintViolation();
                ConstraintValidatorContext.ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(placeholderHelper.replacePlaceholders(message.getMessage(),
                        s -> {
                            Map<Object, Object> places = message.getPlaces();
                            if (CollectionUtils.isEmpty(places)) {
                                return s;
                            } else {
                                return Objects.toString(places.get(s));
                            }
                        }));
                if (StringUtils.isNotBlank(fieldName)) {
                    builder.addPropertyNode(fieldName).addConstraintViolation();
                } else {
                    builder.addConstraintViolation();
                }
            }
            return false;
        }
        return true;
    }

    private void valid(Object target, List<MessagesPlace> result) {
        isReject(target, result, spELValid);
    }

    public void isReject(Object target, List<MessagesPlace> result, SpELValid annotation) {
        EvaluationContext context = new StandardEvaluationContext(target);
        SpELExpression[] expressions = annotation.expressions();
        if (Objects.nonNull(expressions) && expressions.length > 0) {
            List<SpELExpression> expressionList = Arrays.asList(expressions);
            expressionList.stream().forEach(expression -> {
                SpELConditionExpression[] childrenExpressions = expression.children();
                if (Objects.nonNull(childrenExpressions) && childrenExpressions.length > 0) {
                    // 子条件存在任意一条不符合，则直接跳过当前校验，认为当前值合法
                    if (Arrays.stream(childrenExpressions).
                            anyMatch(childrenExpression -> !parser.parseExpression(childrenExpression.expression()).getValue(context, Boolean.class))) {
                        return;
                    }
                }
                Boolean validate = parser.parseExpression(expression.expression()).getValue(context, Boolean.class);
                if (BooleanUtils.isFalse(validate)) {
                    result.add(handleRangeMessage(expression));
                }
            });
        }
    }

    /**
     * 注解是否标注在class上*
     *
     * @param context
     * @param targetClass
     * @return
     */
    private boolean isAnnotatedForClass(ConstraintValidatorContext context, Class<?> targetClass) {
        List<ConstraintViolationCreationContext> creationContexts = ((ConstraintValidatorContextImpl) context).getConstraintViolationCreationContexts();
        return creationContexts.stream().map(con -> con.getPath()).map(PathImpl::getLeafNode).map(NodeImpl::getName).allMatch(StringUtils::isBlank) &&
                targetClass.isAnnotationPresent(SpELValid.class);
    }
}
