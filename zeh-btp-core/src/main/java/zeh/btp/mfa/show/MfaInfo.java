package zeh.btp.mfa.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: mfa实体类
 * @author: ErHu.Zhao
 * @create: 2024-06-04
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MfaInfo<T> {

    private Integer mfaStatus;

    private T mfaInfo;

}
