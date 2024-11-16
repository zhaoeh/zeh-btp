package zeh.btp.utils.date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description: 日期工具类
 * @author: ErHu.Zhao
 * @create: 2024-11-07
 **/
public class DateUtils1 {

    public static String DATE_FORMAT = "yyyy-MM-dd";

    public static String DATE_FORMAT_SEP = "-";

    public static String DATE_FORMAT_MONTH = "yyyyMMdd";

    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_FORMAT_CHINESE = "yyyy年M月d日";
    public static final String DATE_MILLI_SECONDS_FORMAT = "yyyyMMddHHmmssSSS";

    public static String MONTH_FORMAT = "yyyy-MM";

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils1.DATE_FORMAT);
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
        SimpleDateFormat df = new SimpleDateFormat(DateUtils1.DATE_TIME_FORMAT);
        datestr = df.format(new Date());
        return datestr;
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
        SimpleDateFormat df = new SimpleDateFormat(DateUtils1.DATE_TIME_FORMAT);
        datestr = df.format(cal.getTime());
        return datestr;
    }

    public static String getBeforeDateTime2(Date date, int num) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.DAY_OF_MONTH, num);
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils1.DATE_FORMAT);
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
        SimpleDateFormat df = new SimpleDateFormat(DateUtils1.DATE_FORMAT);
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
        SimpleDateFormat df = new SimpleDateFormat(DateUtils1.DATE_TIME_FORMAT);
        datestr = df.format(date);
        return datestr;
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
        SimpleDateFormat df = new SimpleDateFormat(DateUtils1.DATE_FORMAT);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            date = DateUtils1.stringToDate(datestr, "yyyyMMdd");
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
        LocalDate result = LocalDate.parse(datestr, DateTimeFormatter.ofPattern(DateUtils1.DATE_FORMAT));
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
        LocalDateTime result = LocalDateTime.parse(datestr, DateTimeFormatter.ofPattern(DateUtils1.DATE_TIME_FORMAT));
        return result;
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
     * description: 获取上n个月最后一天
     *
     * @param: [n]
     * @return: java.time.LocalDate
     * @Date: 2023/10/30 10:13
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static LocalDate getLastNMonthLastDay(int n) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算目标月份
        YearMonth yearMonth = YearMonth.from(currentDate).minusMonths(n);
        // 获取目标月份的最后一天
        LocalDate lastDay = yearMonth.atEndOfMonth();
        return lastDay;
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
     * 当前月最后一天
     *
     * @return 当前月的最后一天
     */
    public static LocalDate getCurrentMonthLastDayV1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }


    /**
     * description: 根据偏移量获取格式化的月份字符串 0:当前月，1：上个月，2：上上个月
     *
     * @param: [monthOffset]
     * @return: java.lang.String
     * @Date: 2024/2/1 16:42
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static String getFormattedMonth(int monthOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -monthOffset);

        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(MONTH_FORMAT);
        return dateFormat.format(date);
    }

    /**
     * description: 获取某两个日期之间的月份List
     *
     * @param: [startDateStr, endDateStr]
     * @return: java.util.List<java.lang.String>
     * @Date: 2024/2/5 18:37
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static List<String> getMonthRange(LocalDate startDate, LocalDate endDate) {
        List<String> monthList = new ArrayList<>();
        LocalDate currentMonth = startDate;

        while (!currentMonth.isAfter(endDate)) {
            monthList.add(YearMonth.from(currentMonth).toString());
            currentMonth = currentMonth.plusMonths(1);
        }

        return monthList;
    }

    /**
     * description: 获取某两个日期之间的月份List
     *
     * @param: [startDateStr, endDateStr]
     * @return: java.util.List<java.lang.String>
     * @Date: 2024/2/5 18:37
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static List<String> getMonthRangeForStr(String startDateStr, String endDateStr) {
        List<String> monthList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        LocalDate tempDate = startDate.withDayOfMonth(1);
        while (!tempDate.isAfter(endDate)) {
            String month = tempDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            monthList.add(month);
            tempDate = tempDate.plusMonths(1);
        }

        return monthList;
    }

    /**
     * description: 通过YYYY-MM类型的月份字符串获取该月份第一天
     *
     * @param: [monthString]
     * @return: java.time.LocalDate
     * @Date: 2024/2/6 10:34
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static LocalDate getFirstDayOfMonth(String monthString) {
        // 解析月份字符串
        YearMonth yearMonth = YearMonth.parse(monthString, DateTimeFormatter.ofPattern("yyyy-MM"));

        // 获取月份的第一天日期
        return yearMonth.atDay(1);
    }

    public static LocalDate getLastDayOfMonth(String monthString) {
        // 解析月份字符串
        YearMonth yearMonth = YearMonth.parse(monthString, DateTimeFormatter.ofPattern("yyyy-MM"));

        // 获取月份的第一天日期
        return yearMonth.atEndOfMonth();
    }

    /**
     * @return n周前的周一
     */
    public static LocalDate getNWeeksAgoMonday(int n) {
        LocalDate today = LocalDate.now();
        LocalDate nWeeksAgo = today.minusWeeks(n);

        // 计算n周前的周一日期
        LocalDate monday = nWeeksAgo.with(DayOfWeek.MONDAY);

        return monday;
    }

    /**
     * @return n周前的周日
     */
    public static LocalDate getNWeeksAgoSunday(int n) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 计算目标日期
        LocalDate targetDate = currentDate.minusWeeks(n).with(DayOfWeek.SUNDAY);

        return targetDate;
    }

    /**
     * 获取n天前的日期
     *
     * @param n
     * @return
     */
    public static LocalDate getNDaysAgo(int n) {
        LocalDate today = LocalDate.now();

        LocalDate nDaysAgo = today.minusDays(n);

        return nDaysAgo;
    }

    /**
     * description: 获取当天距离月底还有多少天
     *
     * @param: []
     * @return: java.lang.Long
     * @Date: 2023/12/7 15:45
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static Long getDaysLeftForCurMonth() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取本月月底日期
        LocalDate lastDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.lengthOfMonth());

        // 计算距离月底还有多少天
        long daysUntilMonthEnd = ChronoUnit.DAYS.between(currentDate, lastDayOfMonth);

        return daysUntilMonthEnd;
    }

    /**
     * description: 获取开始日期到结束日期之间的日期list
     *
     * @param: [start, end]
     * @return: java.util.List<java.time.LocalDate>
     * @Date: 2023/12/7 15:53
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static List<LocalDate> getDateRange(LocalDate start, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate current = start;

        while (!current.isAfter(end)) {
            dateList.add(current);
            current = current.plusDays(1);
        }

        return dateList;
    }

    public static List<String> getDateRangeAsStr(LocalDate start, LocalDate end) {
        List<String> dateList = new ArrayList<>();
        LocalDate current = start;

        while (!current.isAfter(end)) {
            dateList.add(current.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            current = current.plusDays(1);
        }

        return dateList;
    }

    /**
     * description: 传入某个日期，构建上个月的日期列表
     *
     * @param: [currentDate]
     * @return: java.util.List<java.time.LocalDate>
     * @Date: 2023/12/7 11:48
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static List<LocalDate> getLastMonthDatesList(LocalDate currentDate) {

        // 获取上个月的日期
        LocalDate lastMonth = currentDate.minusMonths(1);

        // 获取上个月的天数
        int lastMonthLength = lastMonth.lengthOfMonth();

        // 构建上个月的日期列表
        List<LocalDate> lastMonthDates = new ArrayList<>();
        for (int day = 1; day <= lastMonthLength; day++) {
            lastMonthDates.add(LocalDate.of(lastMonth.getYear(), lastMonth.getMonth(), day));
        }

        return lastMonthDates;
    }

    /**
     * 获取n个月前的昨天
     *
     * @param n
     * @return
     */
    public static LocalDate getNMonthAgoDate(int n) {
        LocalDate yestoday = getNDaysAgo(1);
        LocalDate nMonthsAgo = yestoday.minusMonths(n);
        return nMonthsAgo;
    }

    /**
     * description: 返回String
     *
     * @param: [currentDate]
     * @return: java.util.List<java.lang.String>
     * @Date: 2023/12/7 11:54
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static List<String> getLastMonthDatesStrList(LocalDate currentDate) {
        // 获取上个月的日期
        LocalDate lastMonth = currentDate.minusMonths(1);

        // 获取上个月的天数
        int lastMonthLength = lastMonth.lengthOfMonth();

        // 构建上个月的日期列表
        List<String> lastMonthDates = new ArrayList<>();
        for (int day = 1; day <= lastMonthLength; day++) {
            String formattedDate = LocalDate.of(lastMonth.getYear(), lastMonth.getMonth(), day)
                    .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
            lastMonthDates.add(formattedDate);
        }

        return lastMonthDates;
    }


    /**
     * description: 判断时间区间是否属于当月之前，跨月属于当月
     *
     * @param: [startDate, endDate]
     * @return: boolean true表示当月之前，false表示当月
     * @Date: 2023/11/17 17:43
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static boolean isBeforeCurrentMonth(String startDate, String endDate) {

        LocalDate startLocalDate = stringToLocalDate(startDate);
        LocalDate endLocalDate = stringToLocalDate(endDate);

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取当前月份的第一天
        LocalDate firstDayOfCurrentMonth = currentDate.withDayOfMonth(1);
        // 判断结束日期是否在当前月份之前
        if (endLocalDate.isBefore(firstDayOfCurrentMonth)) {
            return true;
        }

        // 判断开始日期是否在当前月份之内
        if (!startLocalDate.isBefore(firstDayOfCurrentMonth)) {
            return false;
        }

        //跨月按照当月处理
        return true;

    }

    /**
     * description: 判断两个日期是否是同一月
     *
     * @param: [startLocalDate, endLocalDate]
     * @return: boolean
     * @Date: 2023/12/26 15:42
     * @Version: 1.0.0
     * @Author: Lucian
     */
    public static boolean isSameMonth(LocalDate startLocalDate, LocalDate endLocalDate) {

        // 获取YearMonth对象
        YearMonth yearMonth1 = YearMonth.from(startLocalDate);
        YearMonth yearMonth2 = YearMonth.from(endLocalDate);

        // 判断是否在同一个月份
        boolean isSameMonth = yearMonth1.equals(yearMonth2);

        return isSameMonth;
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
        LocalDate result = LocalDate.parse(datestr, DateTimeFormatter.ofPattern(DateUtils1.DATE_FORMAT_MONTH));
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
        SimpleDateFormat df = new SimpleDateFormat(DateUtils1.DATE_FORMAT);
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
        return DateUtils1.addDay(DateUtils1.getFirstDayOfMonth(DateUtils1.addMonth(date, 1)), -1);
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

        Date f = DateUtils1.chgObject(fromDate);
        Date t = DateUtils1.chgObject(toDate);
        long fd = (f != null) ? f.getTime() : System.currentTimeMillis();
        long td = (t != null) ? t.getTime() : System.currentTimeMillis();
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

        int cYear = DateUtils1.getYearOfDate(calcDate);
        int cMonth = DateUtils1.getMonthOfDate(calcDate);
        int cDay = DateUtils1.getDayOfDate(calcDate);
        int bYear = DateUtils1.getYearOfDate(birthday);
        int bMonth = DateUtils1.getMonthOfDate(birthday);
        int bDay = DateUtils1.getDayOfDate(birthday);
        if (cMonth > bMonth || (cMonth == bMonth && cDay > bDay)) {
            return cYear - bYear;
        } else {
            return cYear - 1 - bYear;
        }
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
        return DateUtils1.dateToString(cd.getTime());
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
            return DateUtils1.stringToDate((String) date);
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
}
