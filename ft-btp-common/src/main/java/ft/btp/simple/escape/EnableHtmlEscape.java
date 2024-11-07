package ft.btp.simple.escape;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableHtmlEscape {
}
