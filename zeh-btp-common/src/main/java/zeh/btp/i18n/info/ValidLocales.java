package zeh.btp.i18n.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

/**
 * @description: 有效locale实体
 * @author: ErHu.Zhao
 * @create: 2024-07-09
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidLocales {

    private Exception exceptionOfCheckFailed;

    private List<Locale> validLocales;

    private Locale defaultLocale;

}
