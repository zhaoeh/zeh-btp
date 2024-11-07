package ft.btp.utils.date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ClassName DateUtils
 * @Description 时间工具
 * @Author TJSAustin
 * @Date 2023/5/20 10:00
 * @Version 1.0
 **/
public class DateUtils {

    public static String DATE_FORMAT = "yyyy-MM-dd";

    public static String DATE_FORMAT_SEP = "-";

    public static String DATE_FORMAT_MONTH = "yyyyMMdd";

    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_FORMAT_CHINESE = "yyyy年M月d日";
    public static final String DATE_MILLI_SECONDS_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        datestr = df.format(new Date());
        return datestr;
    }

    public static int getCurrentMonth() {
        return LocalDateTime.now().getMonthValue();
    }


    public static String getSmsDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String result = localDateTime.getHour() + ":" + localDateTime.getMinute() + " " + localDateTime.getMonth() + " " + localDateTime.getDayOfMonth();
        return result;
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getCurrentDateTime() {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        datestr = df.format(new Date());
        return datestr;
    }
    public static String getDateString2(Date time, String s) {
        SimpleDateFormat dfm = new SimpleDateFormat(s);
        return dfm.format(time);
    }
    /**
     * 获取昨天日期时间
     *
     * @return
     */
    public static String getBeforeDateTime(Date date, int num) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.getTime().getDate() == 1) {
            cal.add(Calendar.MONTH, -1);
        }
        cal.add(Calendar.DATE, num);
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        datestr = df.format(cal.getTime());
        return datestr;
    }


    public static String getBeforeDate(Date date, int num) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.getTime().getDate() == 1) {
            cal.add(Calendar.MONTH, -1);
        }
        cal.add(Calendar.DATE, num);
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        datestr = df.format(cal.getTime());
        return datestr;
    }


    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getCurrentDateTime(String Dateformat) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(Dateformat);
        datestr = df.format(new Date());
        return datestr;
    }

    /**
     * date to str
     *
     * @return
     */
    public static String dateToDateTime(Date date) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        datestr = df.format(date);
        return datestr;
    }

    /**
     * 获取 24小时 时间格式  英文缩写月 日
     *
     * @return
     */
    public static String get24hhMMM() {
        SimpleDateFormat dfm = new SimpleDateFormat ("HH:mm MMM dd");
        return dfm.format(new Date());
    }
    /**
     * localDate to str
     *
     * @return
     */
    public static String localDateToString(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return localDate.format(dateTimeFormatter);
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
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            date = DateUtils.stringToDate(datestr, "yyyyMMdd");
        }
        return date;
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
        LocalDate result = LocalDate.parse(datestr, DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT));
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
        LocalDateTime result = LocalDateTime.parse(datestr, DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_FORMAT));
        return result;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static String getFirstDayOfThisMonth() {

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT);

        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        String firstDay = sdf.format(calendar.getTime());

        return firstDay;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static LocalDate getFirstDayOfThisMonthLocalDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT);

        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        LocalDate localDate = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return localDate;
    }

    /**
     * 获取上个月第一天
     *
     * @return
     */
    public static LocalDate getLastMonthFirstDay() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        LocalDate localDate = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return localDate;
    }

    /**
     * 获取上两个月月最后一天
     *
     * @return
     */
    public static LocalDate getLastTwoMonthLastDay() {

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        LocalDate localDate = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return localDate;
    }

    /**
     * 获取上n月最后一天
     *
     * @return
     */
    public static LocalDate getLastNMonthLastDay(Integer n) {

        LocalDate result = null;

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH) - n;
        calendar.set(Calendar.MONTH, month);
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (month == 1) {
            int year = calendar.get(Calendar.YEAR);
            if (year % 4 == 0 || year % 400 == 0) {
                actualMaximum = 29;
            } else {
                actualMaximum = 28;
            }

            calendar.set(Calendar.DAY_OF_MONTH, actualMaximum);
            result = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault()).minusMonths(1);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, actualMaximum);
            result = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        }


        return result;
    }

    /**
     * 获取上n个月前第一天
     *
     * @return
     */
    public static LocalDate getLastTwoMonthFirstDay() {

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - 2);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        LocalDate localDate = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return localDate;
    }

    /**
     * 获取上n个月前第一天
     *
     * @return
     */
    public static LocalDate getLastNMonthFirstDay(Integer n) {

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - n);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        LocalDate localDate = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return localDate;
    }

    /**
     * 上个月的最后一天
     *
     * @return 上个月的最后一天
     */
    public static LocalDate getLastMonthLastDayV1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static LocalDate getLastDayOfThisMonthLocalDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT);

        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        LocalDate localDate = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return localDate;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static String getLastDayOfThisMonth() {

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT);

        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        String lastDay = sdf.format(calendar.getTime());

        return lastDay;
    }


    /**
     * 将字符串日期转换为日期格式
     *
     * @param datestr
     * @return
     */
    public static LocalDate stringToLocalMonthAppend(String datestr) {

        if (datestr == null || datestr.equals("")) {
            return null;
        }
        datestr = datestr + "01";
        LocalDate result = LocalDate.parse(datestr, DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT_MONTH));
        return result;
    }

    /**
     * 将字符串日期转换为日期格式
     *
     * @param datestr
     * @return
     */
    public static String dateFormatChangeToNormal(String datestr) {

        if (datestr == null || datestr.equals("")) {
            return null;
        }

        String result = datestr.substring(0, 7);
        return result;
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
     * 将日期格式日期转换为字符串格式
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        datestr = df.format(date);
        return datestr;
    }

    /**
     * 将日期格式日期转换为字符串格式 自定義格式
     *
     * @param date
     * @param dateformat
     * @return
     */
    public static String dateToString(Date date, String dateformat) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        datestr = df.format(date);
        return datestr;
    }

    /**
     * 获取日期的DAY值
     *
     * @param date 输入日期
     * @return
     */
    public static int getDayOfDate(Date date) {
        int d = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        d = cd.get(Calendar.DAY_OF_MONTH);
        return d;
    }

    /**
     * 获取日期的MONTH值
     *
     * @param date 输入日期
     * @return
     */
    public static int getMonthOfDate(Date date) {
        int m = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        m = cd.get(Calendar.MONTH) + 1;
        return m;
    }

    /**
     * 获取日期的YEAR值
     *
     * @param date 输入日期
     * @return
     */
    public static int getYearOfDate(Date date) {
        int y = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        y = cd.get(Calendar.YEAR);
        return y;
    }

    /**
     * 获取星期几
     *
     * @param date 输入日期
     * @return
     */
    public static int getWeekOfDate(Date date) {
        int wd = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        wd = cd.get(Calendar.DAY_OF_WEEK) - 1;
        return wd;
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
     * 获得输入日期的当月最后一天
     *
     * @param date
     */
    public static Date getLastDayOfMonth(Date date) {
        return DateUtils.addDay(DateUtils.getFirstDayOfMonth(DateUtils.addMonth(date, 1)), -1);
    }

    /**
     * 判断是否是闰年
     *
     * @param date 输入日期
     * @return 是true 否false
     */
    public static boolean isLeapYEAR(Date date) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int year = cd.get(Calendar.YEAR);
        if (year % 4 == 0 && year % 100 != 0 | year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据整型数表示的年月日，生成日期类型格式
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    public static Date getDateByYMD(int year, int month, int day) {
        Calendar cd = Calendar.getInstance();
        cd.set(year, month - 1, day);
        return cd.getTime();
    }

    /**
     * 获取年周期对应日
     *
     * @param date  输入日期
     * @param iyear 年数   負數表示之前
     * @return
     */
    public static Date getYearCycleOfDate(Date date, int iyear) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.YEAR, iyear);
        return cd.getTime();
    }

    /**
     * 获取月周期对应日
     *
     * @param date 输入日期
     * @param i
     * @return
     */
    public static Date getMonthCycleOfDate(Date date, int i) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MONTH, i);
        return cd.getTime();
    }

    /**
     * 计算 fromDate 到 toDate 相差多少年
     *
     * @param fromDate
     * @param toDate
     * @return 年数
     */
    public static int getYearByMinusDate(Date fromDate, Date toDate) {
        Calendar df = Calendar.getInstance();
        df.setTime(fromDate);
        Calendar dt = Calendar.getInstance();
        dt.setTime(toDate);
        return dt.get(Calendar.YEAR) - df.get(Calendar.YEAR);
    }

    /**
     * 计算 fromDate 到 toDate 相差多少个月
     *
     * @param fromDate
     * @param toDate
     * @return 月数
     */
    public static int getMonthByMinusDate(Date fromDate, Date toDate) {
        Calendar df = Calendar.getInstance();
        df.setTime(fromDate);
        Calendar dt = Calendar.getInstance();
        dt.setTime(toDate);
        return dt.get(Calendar.YEAR) * 12 + dt.get(Calendar.MONTH) -
                (df.get(Calendar.YEAR) * 12 + df.get(Calendar.MONTH));
    }

    /**
     * 计算 fromDate 到 toDate 相差多少天
     *
     * @param fromDate
     * @param toDate
     * @return 天数
     */
    public static long getDayByMinusDate(Object fromDate, Object toDate) {

        Date f = DateUtils.chgObject(fromDate);
        Date t = DateUtils.chgObject(toDate);
        long fd = f.getTime();
        long td = t.getTime();
        return (td - fd) / (24L * 60L * 60L * 1000L);
    }

    /**
     * 计算年龄
     *
     * @param birthday 生日日期
     * @param calcDate 要计算的日期点
     * @return
     */
    public static int calcAge(Date birthday, Date calcDate) {

        int cYear = DateUtils.getYearOfDate(calcDate);
        int cMonth = DateUtils.getMonthOfDate(calcDate);
        int cDay = DateUtils.getDayOfDate(calcDate);
        int bYear = DateUtils.getYearOfDate(birthday);
        int bMonth = DateUtils.getMonthOfDate(birthday);
        int bDay = DateUtils.getDayOfDate(birthday);
        if (cMonth > bMonth || (cMonth == bMonth && cDay > bDay)) {
            return cYear - bYear;
        } else {
            return cYear - 1 - bYear;
        }
    }

    /**
     * 根据生日计算年龄*
     * @param birthday
     * @return
     */
    public static Integer calcAgeByBirthday(String birthday) {
        // 校验用户年龄是否超过21岁
        if (StringUtils.isNotBlank(birthday)) {
            Date b = stringToDate(birthday, DATE_FORMAT);
            LocalDate birthDate = b.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthDate, currentDate);
            return period.getYears();
        }
        return null;
    }

    /**
     * 从身份证中获取出生日期
     *
     * @param idno 身份证号码
     * @return
     */
    public static String getBirthDayFromIDCard(String idno) {
        Calendar cd = Calendar.getInstance();
        if (idno.length() == 15) {
            cd.set(Calendar.YEAR, Integer.valueOf("19" + idno.substring(6, 8))
                    .intValue());
            cd.set(Calendar.MONTH, Integer.valueOf(idno.substring(8, 10))
                    .intValue() - 1);
            cd.set(Calendar.DAY_OF_MONTH,
                    Integer.valueOf(idno.substring(10, 12)).intValue());
        } else if (idno.length() == 18) {
            cd.set(Calendar.YEAR, Integer.valueOf(idno.substring(6, 10))
                    .intValue());
            cd.set(Calendar.MONTH, Integer.valueOf(idno.substring(10, 12))
                    .intValue() - 1);
            cd.set(Calendar.DAY_OF_MONTH,
                    Integer.valueOf(idno.substring(12, 14)).intValue());
        }
        return DateUtils.dateToString(cd.getTime());
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
     * description: 对当前日期减一年
     *
     * @param: [date, imonth]
     * @return: java.lang.String
     * @Date: 2023/8/29 12:05
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static String addYearWithStr(Date date, int year) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.YEAR, year);
        cd.add(Calendar.MONTH, -1);
        return dateToDateTime(cd.getTime());
    }


    /*public static void main(String[] args) {
        Calendar cd = Calendar.getInstance();
        cd.set(2023,8,26,23,59,59);

        System.out.println(addYearWithStr(cd.getTime(),0));
        System.out.println(addYearWithStr(cd.getTime(),-1));
    }*/

    /**
     * 在输入日期上增加（+）或减去（-）年份
     *
     * @param date  输入日期
     * @param iyear 要增加或减少的年数
     */
    public static Date addYear(Date date, int iyear) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.YEAR, iyear);
        return cd.getTime();
    }

    /**
     * 將OBJECT類型轉換為Date
     *
     * @param date
     * @return
     */
    public static Date chgObject(Object date) {

        if (date != null && date instanceof Date) {
            return (Date) date;
        }
        if (date != null && date instanceof String) {
            return DateUtils.stringToDate((String) date);
        }
        return null;
    }

    public static long getAgeByBirthday(String date) {

        Date birthday = stringToDate(date, "yyyy-MM-dd");
        long sec = new Date().getTime() - birthday.getTime();
        long age = sec / (1000 * 60 * 60 * 24) / 365;
        return age;
    }

    public static String formatToDayEnding(Date date) {
        return format(date, "yyyy-MM-dd") + " " + "23:59:59";
    }

    public static String format(Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }

    public static FastDateFormat getDateFormat(String pattern) {
        return FastDateFormat.getInstance(pattern);
    }

    public static Date getConversionTime(String dateTimeStr) {
        Date date;
        try {
            SimpleDateFormat dfm = new SimpleDateFormat(DATE_TIME_FORMAT);
            date = dfm.parse(dateTimeStr);
        } catch (Exception e) {
//            log.error("{}时间格式解析异常 {}",dateTimeStr,e.getMessage());
            return null;
        }
        return date;
    }


    public static Integer getMonthDayNum(LocalDate localDate) {

        Month month = localDate.getMonth();
        int i = month.maxLength();
        return i;
    }
    // 将日期从 yyyy/MM/dd 转换为 yyyy-MM-dd 格式
    public static String convertDateFormat(String date) throws ParseException {
        // 检查是否为 yyyy/MM/dd 格式
        if (date.contains("/")) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_FORMAT);
            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        }
        // 如果已经是 yyyy-MM-dd 格式，直接返回
        return date;
    }
}
