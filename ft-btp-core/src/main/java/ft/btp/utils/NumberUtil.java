package ft.btp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class NumberUtil {

    public static final BigDecimal HUNDRED = BigDecimal.TEN.multiply(BigDecimal.TEN);

    public static final BigDecimal fromString(String str, BigDecimal defaultIfNull) {
        if (null == str || "".equals(str)) {
            return defaultIfNull;
        }
        try {
            return new BigDecimal(str.trim());
        } catch (Exception e) {
            return defaultIfNull;
        }
    }

    public static final boolean isZeroOrNull(BigDecimal number) {
        return number == null || BigDecimal.ZERO.equals(number);
    }

    public static boolean lessThanEqual(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }

    public static boolean greaterThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }

    public static BigDecimal asPercentage(BigDecimal a) {
        return a.divide(HUNDRED);
    }

    public static BigDecimal roundDown(BigDecimal bd) {
        return bd.setScale(2, RoundingMode.DOWN);
    }

    public static String asString(BigDecimal bd) {
        if (null == bd) {
            return "0.00";
        } else {
            return roundDown(bd).toPlainString();
        }
    }

    public static BigDecimal parseString(String str) {
        return roundDown(new BigDecimal(str));
    }

}