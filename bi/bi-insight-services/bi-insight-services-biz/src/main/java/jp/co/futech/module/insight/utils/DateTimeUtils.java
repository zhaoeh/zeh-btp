package jp.co.futech.module.insight.utils;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author tao.ma@futech.co.jp
 * @date 2024/4/10
 */
public class DateTimeUtils {

    /**
     * Get Datetime String By Pattern
     *
     * @param inputTs    Input Timestamp
     * @param inPattern  e.g. "yyyy-MM-dd HH:mm:ss"
     * @param outPattern e.g. "yyyy-MM-dd"
     * @return Datetime String
     */
    public static String getDatetimeStringByPattern(String inputTs, String inPattern, String outPattern) {
        return LocalDateTime.parse(inputTs, DateTimeFormatter.ofPattern(inPattern)).format(DateTimeFormatter.ofPattern(outPattern));
    }

    /**
     * Get Timestamp By Datetime String Midnight
     *
     * @param datetime Datetime String
     * @param pattern  e.g. "yyyy-MM-dd HH:mm:ss"
     * @return Timestamp
     */
    public static Timestamp getTimestampByDatetimeStringMidnight(String datetime, String pattern) {
        return Timestamp.valueOf(LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(pattern)).toLocalDate().atStartOfDay());
    }

    /**
     * Get Timestamp By Datetime String
     *
     * @param datetime Datetime String
     * @param pattern  e.g. "yyyy-MM-dd HH:mm:ss"
     * @return Timestamp
     */
    public static Timestamp getTimestampByDatetimeString(String datetime, String pattern) {
        return Timestamp.valueOf(LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(pattern)));
    }

    /**
     * Get Datetime String By Timestamp
     *
     * @param ts      Timestamp
     * @param pattern e.g. "yyyy-MM-dd HH:mm:ss"
     * @return Datetime String
     */
    public static String getDatetimeStringByTimestamp(Timestamp ts, String pattern) {
        return ts.toLocalDateTime().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get Midnight Datetime String
     *
     * @param inputTs Input Datetime String
     * @param pattern e.g. "yyyy-MM-dd HH:mm:ss"
     * @return Midnight Datetime String
     */
    public static String getMidnightDatetimeString(String inputTs, String pattern) {
        return LocalDateTime.parse(inputTs, DateTimeFormatter.ofPattern(pattern)).withHour(0).withMinute(0).withSecond(0)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get Timestamp By Plus Hours
     *
     * @param inputTs      Input Timestamp
     * @param inputPattern Input Pattern e.g. "yyyy-MM-dd HH:mm:ss"
     * @param hourOffset   Plus Hour Offset
     * @return Timestamp
     */
    public static Timestamp getTimestampByPlussingHours(String inputTs,
                                                        String inputPattern,
                                                        int hourOffset) {
        return Timestamp.valueOf(LocalDateTime.parse(inputTs, DateTimeFormatter.ofPattern(inputPattern)).plusHours(hourOffset));
    }

    /**
     * Get Now Datetime String
     *
     * @param pattern Datetime Pattern
     * @return Datetime String
     */
    public static String getNowDateTimeString(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get Datetime String By Minus Days
     *
     * @param pattern e.g. "yyyy-MM-dd"
     * @param days    Days Offset
     * @return Datetime String
     */
    public static String getDatetimeStringByMinusDays(String pattern, int days) {
        return LocalDateTime.now().minusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get Datetime String By Minus Days V2
     *
     * @param datetime Datetime String
     * @param pattern  e.g. "yyyy-MM-dd"
     * @param days     Days Offset
     * @return Datetime String
     */
    public static String getDatetimeStringByMinusDays2(String datetime, String pattern, int days) {
        return LocalDate.parse(datetime, DateTimeFormatter.ofPattern(pattern)).minusDays(days)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get Now Timestamp
     *
     * @return Now Timestamp
     */
    public static Timestamp getNowTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * Get Current Midnight Timestamp
     *
     * @return Current Midnight Timestamp
     */
    public static Timestamp getCurrentMidnightTimestamp() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
    }

    /**
     * Get Datetime String By Pattern
     *
     * @param pattern e.g. "yyyy-MM-dd HH:mm:ss"
     * @return Datetime String
     */
    public static String getCurrentMidnightStringByPattern(String pattern) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get Previous Midnight String
     *
     * @param pattern e.g. "yyyy-MM-dd HH:mm:ss"
     * @param offset  Day offset
     * @return Datetime String
     */
    public static String getPreviousMidnightString(String pattern, Integer offset) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).minusDays(offset).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get Days Interval
     *
     * @param startTime Start Time String
     * @param endTime   End Time String
     * @param pattern   e.g. "yyyy-MM-dd HH:mm:ss"
     * @return Days Interval Long
     */
    public static long getDaysInterval(String startTime, String endTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return Math.abs(Duration.between(LocalDateTime.parse(startTime, formatter),
                LocalDateTime.parse(endTime, formatter)).toDays());
    }

    /**
     * Plus Days
     *
     * @param datetime Datetime String
     * @param offset   Days offset
     * @param pattern  e.g. "yyyy-MM-dd HH:mm:ss"
     * @return Datetime String
     */
    public static String plusDays(String datetime, int offset, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(datetime, formatter).plusDays(offset).format(formatter);
    }
}
