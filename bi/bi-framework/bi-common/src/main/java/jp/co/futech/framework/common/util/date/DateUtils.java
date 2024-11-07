package jp.co.futech.framework.common.util.date;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author futech.co.jp
 */
public class DateUtils {

    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    /**
     * 秒转换成毫秒
     */
    public static final long SECOND_MILLIS = 1000;

    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_HOUR_MINUTE_SECOND = "HH:mm:ss";

    /**
     * 将 LocalDateTime 转换成 Date
     *
     * @param date LocalDateTime
     * @return LocalDateTime
     */
    public static Date of(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        // 将此日期时间与时区相结合以创建 ZonedDateTime
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());
        // 本地时间线 LocalDateTime 到即时时间线 Instant 时间戳
        Instant instant = zonedDateTime.toInstant();
        // UTC时间(世界协调时间,UTC + 00:00)转北京(北京,UTC + 8:00)时间
        return Date.from(instant);
    }

    /**
     * 将 Date 转换成 LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime of(Date date) {
        if (date == null) {
            return null;
        }
        // 转为时间戳
        Instant instant = date.toInstant();
        // UTC时间(世界协调时间,UTC + 00:00)转北京(北京,UTC + 8:00)时间
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date addTime(Duration duration) {
        return new Date(System.currentTimeMillis() + duration.toMillis());
    }

    public static boolean isExpired(Date time) {
        return System.currentTimeMillis() > time.getTime();
    }

    public static boolean isExpired(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time);
    }

    public static long diff(Date endTime, Date startTime) {
        return endTime.getTime() - startTime.getTime();
    }

    /**
     * 创建指定时间
     *
     * @param year  年
     * @param mouth 月
     * @param day   日
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day) {
        return buildTime(year, mouth, day, 0, 0, 0);
    }

    /**
     * 创建指定时间
     *
     * @param year   年
     * @param mouth  月
     * @param day    日
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day,
                                 int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mouth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0); // 一般情况下，都是 0 毫秒
        return calendar.getTime();
    }

    public static Date max(Date a, Date b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) > 0 ? a : b;
    }

    public static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.isAfter(b) ? a : b;
    }

    /**
     * 计算当期时间相差的日期
     *
     * @param field  日历字段.<br/>eg:Calendar.MONTH,Calendar.DAY_OF_MONTH,<br/>Calendar.HOUR_OF_DAY等.
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(int field, int amount) {
        return addDate(null, field, amount);
    }

    /**
     * 计算当期时间相差的日期
     *
     * @param date   设置时间
     * @param field  日历字段 例如说，{@link Calendar#DAY_OF_MONTH} 等
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(Date date, int field, int amount) {
        if (amount == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * 是否今天
     *
     * @param date 日期
     * @return 是否
     */
    public static boolean isToday(LocalDateTime date) {
        return LocalDateTimeUtil.isSameDay(date, LocalDateTime.now());
    }

    /**
     * 是否昨天
     *
     * @param date 日期
     * @return 是否
     */
    public static boolean isYesterday(LocalDateTime date) {
        return LocalDateTimeUtil.isSameDay(date, LocalDateTime.now().minusDays(1));
    }

    /**
     * 按照指定pattern格式化当前日期
     *
     * @return 指定pattern格式化后的当前日期串
     */
    public static String getCurrentDateTime() {
        String dates;
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        dates = df.format(new Date());
        return dates;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.FORMAT_YEAR_MONTH_DAY);
        datestr = df.format(new Date());
        return datestr;
    }

    /**
     * 将字符串日期转换为日期格式
     *
     * @param datestr
     * @return
     */
    public static Date stringToDate(String datestr) {

        if (datestr == null || datestr.equals("")) {
            return null;
        }
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.FORMAT_YEAR_MONTH_DAY);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            date = DateUtils.stringToDate(datestr, "yyyyMMdd");
        }
        return date;
    }

    /**
     * 将字符串日期转换为日期格式
     * 自定義格式
     *
     * @param datestr
     * @return
     */
    public static Date stringToDate(String datestr, String dateformat) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获得输入日期的当月最后一天
     *
     * @param date
     */
    public static Date getLastDayOfMonth(Date date) {
        return DateUtils.addDay(DateUtils.getFirstDayOfMonth(DateUtils.addMonth(date, 1)), -1);
    }

    /**
     * 在输入日期上增加（+）或减去（-）天数
     *
     * @param date 输入日期
     * @param iday 要增加或减少的天数
     */
    public static Date addDay(Date date, int iday) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DAY_OF_MONTH, iday);
        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）月份
     *
     * @param date   输入日期
     * @param imonth 要增加或减少的月分数
     */
    public static Date addMonth(Date date, int imonth) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MONTH, imonth);
        return cd.getTime();
    }

    /**
     * 获取输入日期的当月第一天
     *
     * @param date 输入日期
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.set(Calendar.DAY_OF_MONTH, 1);
        return cd.getTime();
    }


    /**
     * 将日期格式日期转换为字符串格式
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.FORMAT_YEAR_MONTH_DAY);
        datestr = df.format(date);
        return datestr;
    }

    /**
     * 将字符串日期转换为日期格式
     *
     * @param datestr
     * @return
     */
    public static LocalDate stringToLocalDate(String datestr) {

        if (datestr == null || datestr.equals("")) {
            return null;
        }
        LocalDate result = LocalDate.parse(datestr, DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY));
        return result;
    }

    /**
     * 将字符串日期转换为日期格式
     *
     * @param datestr
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String datestr) {

        if (datestr == null || datestr.equals("")) {
            return null;
        }
        LocalDateTime result = LocalDateTime.parse(datestr, DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
        return result;
    }

    /**
     * 判断left是否在right之前
     *
     * @param left  左侧日期
     * @param right 右侧日期
     * @return true：left在right之前 false：left不在right之前
     */
    public static boolean isBefore(String left, String right) {
        if (StringUtils.isBlank(left) || StringUtils.isBlank(right)) {
            return false;
        }
        return stringToLocalDate(left).isBefore(stringToLocalDate(right));
    }

    /**
     * 判断left是否在right之后
     *
     * @param left  左侧日期
     * @param right 右侧日期
     * @return true：left在right之后 false：left不在right之后
     */
    public static boolean isAfter(String left, String right) {
        if (StringUtils.isBlank(left) || StringUtils.isBlank(right)) {
            return false;
        }
        return stringToLocalDate(left).isAfter(stringToLocalDate(right));
    }

}
