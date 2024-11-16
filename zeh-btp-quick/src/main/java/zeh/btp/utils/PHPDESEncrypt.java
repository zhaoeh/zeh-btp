package zeh.btp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Component
@Scope("prototype")
public class PHPDESEncrypt {

    private final static Logger logger = LoggerFactory.getLogger(PHPDESEncrypt.class);

    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    Random random = new Random();
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PHPDESEncrypt() {

    }

    @Autowired
    private Environment env;

    /**
     * 获取并根据类型实例化加密类
     *
     * @param productId
     * @param type      01:真实姓名 02:地址 03:电话 04:邮件 05:网络联系方式 06:帐户名 07:帐号 08:存款人
     * @return
     */
    public static PHPDESEncrypt getNewInstance(String productId, String type) {
        PHPDESEncrypt bean = SpringUtil.getBean(PHPDESEncrypt.class);
        bean.formatKey(productId, type);
        return bean;
    }

    /**
     * 根据类型实例化加密类
     *
     * @param productId
     * @param type      01:真实姓名 02:地址 03:电话 04:邮件 05:网络联系方式 06:帐户名 07:帐号 08:存款人
     *                  (预留校验码以真实姓名加密,身份证以电话加密,问题答案以地址加密)
     * @author sky.x
     */
    private void formatKey(String productId, String type) {
        StringBuffer tempKey = new StringBuffer();
        if (null == type || "".equals(type)) {
            tempKey.append(productId).append(productId).append(productId);
        }
        String productKey = env.getProperty(type + "." + productId + ".KEY");
        // 01:真实姓名
        if ("01".equals(type)) {
            tempKey.append(productId).append("R_01").append(productKey, 1, productKey.length() - 1);
        }
        // 02:地址
        if ("02".equals(type)) {
            tempKey.append(productId).append("A_02").append(productKey, 2, productKey.length() - 2);
        }
        // 03:电话
        if ("03".equals(type)) {
            tempKey.append(productId).append("P_03").append(productKey, 1, productKey.length() - 1);
        }
        // 04:邮件
        if ("04".equals(type)) {
            tempKey.append(productId).append("E_04").append(productKey, 1, productKey.length() - 2);
        }
        // 05:网络联系方式
        if ("05".equals(type)) {
            tempKey.append(productId).append("O_05").append(productKey, 3, productKey.length() - 3);
        }
        // 06:帐户名
        if ("06".equals(type)) {
            tempKey.append(productId).append("AN_06").append(productKey, 1, productKey.length() - 2);
        }
        // 07:帐号
        if ("07".equals(type)) {
            tempKey.append(productId).append("AN_07").append(productKey, 2, productKey.length() - 1);
        }
        // 08:存款人
        if ("08".equals(type)) {
            tempKey.append(productId).append("D_08").append(productKey, 2, productKey.length() - 2);
        }
        // 09:身份证
        if ("09".equals(type)) {
            tempKey.append(productId).append("C_09").append(productKey, 1, productKey.length() - 1);
        }

        this.key = tempKey.toString();
    }


    public byte[] desEncrypt(byte[] plainText) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte[] data = plainText;
        byte[] encryptedData = cipher.doFinal(data);
        return encryptedData;
    }

    public static byte[] desEncrypt(byte[] plainText, String key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
        byte[] data = plainText;
        byte[] encryptedData = cipher.doFinal(data);
        return encryptedData;
    }

    public byte[] desDecrypt(byte[] encryptText) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        byte[] encryptedData = encryptText;
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return decryptedData;
    }

    /**
     * 加密
     *
     * @param input
     * @return String
     * @author sky.x
     */
    public String encrypt(String input) throws Exception {
        if (null == input || "".equals(input.trim())) {
            return input;
        }
        String str1 = getRandomNum(1);
        String str2 = getRandomStr(str1, 2);
        String str3 = getRandomStr(str1, 3);
        StringBuffer sb = new StringBuffer(str2);
        sb.append(base64Encode(desEncrypt(input.getBytes())));
        sb.append(str3);
        return sb.toString();
    }

    /**
     * 解密
     *
     * @param input
     * @return String
     * @author sky.x
     */
    public String decrypt(String input) throws Exception {
        if (null == input || "".equals(input.trim())) {
            return input;
        }
        input = input.substring(3, input.length() - 4);
        byte[] result = base64Decode(input);
        return new String(desDecrypt(result));
    }

    public static String base64Encode(byte[] s) {
        if (s == null) {
            return null;
        }
        Base64.Encoder b = Base64.getEncoder();
        return b.encodeToString(s);
    }

    public byte[] base64Decode(String s) throws IOException {
        if (s == null) {
            return null;
        }
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] b = decoder.decode(s);
        return b;
    }

    /**
     * 加密电话
     *
     * @param phone
     * @return
     */
    public static String callEncode(String phone) {
        return PHPDESEncrypt.base64Encode(phone.getBytes());
    }


    /**
     * 生成随机字符串
     *
     * @param prefix     前缀
     * @param bodyLength 前缀后 字符串长度
     * @return 帐号
     */
    public String getRandomStr(String prefix, int bodyLength) {
        char[] c = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o',
                'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'b', 'n', 'm', 'Q', 'W', 'E', 'R',
                'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B',
                'N', 'M', '='};
        StringBuffer sb = new StringBuffer();
        // 初始化随机数产生器

        if (random != null) {
            for (int i = 0; i < bodyLength; i++) {
                sb.append(c[Math.abs(random.nextInt() % c.length)]);
            }
        }
        return prefix + sb.toString();
    }

    /**
     * 生成随机由数字组成的字符串
     *
     * @param bodyLength 字符串长度
     * @return
     */
    public String getRandomNum(int bodyLength) {
        String sRand = "";
        if (random != null) {
            for (int i = 0; i < bodyLength; i++) {
                String rand = String.valueOf(random.nextInt(10));
                sRand += rand;
            }
        }
        return sRand;
    }

    /**
     * @param input
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptForGi(String input, String key) throws Exception {
        if (null == input || "".equals(input.trim())) {
            return input;
        }
        String base64Code = base64Encode(desEncrypt(input.getBytes(), key));
        return base64Code == null ? null : base64Code.replaceAll("\\s*", "");
    }

    /**
     * MD5加密
     */
    public static String md5Encode(String origin) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(origin.getBytes()));
        } catch (Exception ex) {
            logger.error("md5加密失败!", ex);
        }
        return resultString;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }


}
