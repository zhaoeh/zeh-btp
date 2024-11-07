package ft.btp.utils;

import com.riskcontrol.common.constants.Constant;
import com.riskcontrol.common.exception.BusinessException;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.riskcontrol.common.enums.ResultEnum.ENCRYPTION_FAIL;

/**
 * Created by WADe on 9/14/2017.
 */
public class SensitiveUtil {


    /**
     * 隐藏敏感信息，遮蔽的字符串长度比目标长度大，返回全遮蔽
     * @param hideway 遮蔽方式：前二后一AA*A
     * @param value 需要遮蔽的字符串
     * @return
     */
    public static String halfHideSensitive(String hideway, String value){
        if (StringUtils.isEmpty(hideway) || StringUtils.isEmpty(value)) {
            return "";
        }
        if (hideway.length() > value.length()) {
            return "******";
        }
        // 如果是邮箱
        String suffix = "";
        Matcher m = Pattern.compile(Constant.REGEX_EMAIL).matcher(value);
        if (m.find()) {
            value = m.group(1);
            suffix = m.group(3);
        }
        String res = "";
        Pattern p1 = Pattern.compile("^("+Constant.HIDE_PLACEHOLDER+"{1,})\\*");
        Pattern p2 = Pattern.compile("\\*("+Constant.HIDE_PLACEHOLDER+"{1,})\\*");
        Pattern p3 = Pattern.compile("\\*("+Constant.HIDE_PLACEHOLDER+"{1,})$");
        Matcher m1 = p1.matcher(hideway);
        Matcher m2 = p2.matcher(hideway);
        Matcher m3 = p3.matcher(hideway);

        int start = m1.find() ? m1.group(1).length() : 0;
        int mid = m2.find() ? m2.group(1).length() : 0;
        int end = m3.find() ? m3.group(1).length() : 0;

        char[] textarr = value.toCharArray();
        for (int i = 0; i < textarr.length; i++) {
            if (start > 0 && i < start) {
                res += textarr[i];
            } else if (mid > 0 && i > textarr.length / 2 - mid / 2 && i <= textarr.length / 2 + (mid % 2 == 0 ? mid / 2 : mid / 2 + 1)) {
                res += textarr[i];
            } else if (end > 0 && i >= textarr.length - end) {
                res += textarr[i];
            } else {
                res += Constant.HIDE_CHAR;
            }
        }

        return res + suffix;
    }

    /**
     * 解密敏感信息
     * @param productId
     * @param type
     * @param content
     * @return
     * @throws BusinessException
     */
    public static String decryptContent(String productId, String type, String content){
        PHPDESEncrypt d3 = PHPDESEncrypt.getNewInstance(productId, type);
        try {
            return d3.decrypt(content);
        } catch (Exception e) {
            throw new BusinessException(ENCRYPTION_FAIL);
        }
    }


    /**
     * 加密敏感信息
     * @param productId
     * @param type
     * @param content
     * @return
     * @throws BusinessException
     */
    public static String encryptContent(String productId, String type, String content) {
        PHPDESEncrypt d3 = PHPDESEncrypt.getNewInstance(productId, type);
        try {
            return d3.encrypt(content);
        }catch (Exception e){
            throw new BusinessException(ENCRYPTION_FAIL);
        }
    }
}
