package ft.btp.i18n.endpoint;

import ft.btp.i18n.core.LocaleChecker;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/locale/check")
@Validated
@Slf4j
public class LocaleCheckerEndpoint {

    public LocaleCheckerEndpoint() {
        log.info("LocaleCheckerEndpoint instant");
    }

    /**
     * 校验locale
     *
     * @return
     */
    @RequestMapping("/check-locale")
    @PermitAll
    public Object checkLocale(HttpServletRequest request) throws Exception {
        Exception e = (Exception) request.getAttribute(LocaleChecker.EXCEPTION);
        if (Objects.nonNull(e)) {
            throw e;
        }
        throw new IllegalArgumentException("当前locale非法，请检查");
    }

}
