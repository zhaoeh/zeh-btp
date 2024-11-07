package ft.btp.validation.validator;

import ft.btp.validation.annotation.MultipartFileValidate;
import ft.btp.validation.po.MessagesPlace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 关联校验指定枚举
 * @author: ErHu.Zhao
 * @create: 2024-09-24
 **/
public class MultipartFileValidator implements ConstraintValidator<MultipartFileValidate, Object> {

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    private MultipartFileValidate multipartFileValidate;

    @Override
    public void initialize(MultipartFileValidate constraintAnnotation) {
        multipartFileValidate = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
        if (Objects.isNull(target) || !(target instanceof MultipartFile)) {
            return true;
        }
        final List<MessagesPlace> result = new ArrayList<>();
        valid(target, result);
        return isValid(context, result);
    }

    private MessagesPlace handleRangeMessage(MultipartFileValidate multipartFileValidate, List<String> expects) {
        String append = multipartFileValidate.ignoreExtensionsCase() ? ",and extension can ignore case" : "";
        String message = StringUtils.isNotBlank(multipartFileValidate.message()) ? multipartFileValidate.message() : "current extension must be {target}";
        return MessagesPlace.builder().message(message + append).places(Map.of("target", expects)).build();
    }

    private boolean isValid(ConstraintValidatorContext context, final List<MessagesPlace> result) {
        if (CollectionUtils.isEmpty(result)) {
            return true;
        }
        MessagesPlace message = result.get(0);
        if (Objects.nonNull(message)) {
            // 禁用默认message
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(placeholderHelper.replacePlaceholders(message.getMessage(),
                    s -> {
                        Map<Object, Object> places = message.getPlaces();
                        if (CollectionUtils.isEmpty(places)) {
                            return s;
                        } else {
                            return Objects.toString(places.get(s));
                        }
                    })).addConstraintViolation();
        }
        return false;
    }

    private void valid(Object target, List<MessagesPlace> result) {
        isReject(target, result, multipartFileValidate);
    }

    public void isReject(Object target, List<MessagesPlace> result, MultipartFileValidate multipartFileValidate) {
        Optional.ofNullable(multipartFileValidate).ifPresent(ens -> {
            MultipartFile file = (MultipartFile) target;
            if (file.isEmpty()) {
                result.add(MessagesPlace.builder().message("current MultipartFile is empty").build());
            }
            long maxSize = ens.maxSize();
            if (maxSize > 0 && file.getSize() > maxSize) {
                result.add(MessagesPlace.builder().message("Current MultipartFile size exceeds the limit，limit size is：" + maxSize + " bytes,actual size is：" + file.getSize() + " bytes").build());
            }
            String extension = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename());
            List<String> extensions = Arrays.stream(ens.extensions()).toList();
            if(CollectionUtils.isEmpty(extensions)){
                return;
            }
            List<String> extensionsIgnoreCase = extensions;
            if(multipartFileValidate.ignoreExtensionsCase()){
                extensionsIgnoreCase = extensions.stream().map(String::toLowerCase).collect(Collectors.toList());
                extension = extension.toLowerCase();
            }
            if (!extensionsIgnoreCase.contains(extension)) {
                result.add(handleRangeMessage(multipartFileValidate, extensions));
            }
        });
    }
}
