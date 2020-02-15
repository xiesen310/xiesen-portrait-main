package top.xiesen.analy.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description 时间工具类
 * @className top.xiesen.analy.util.DateUtils
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:57
 */
public class DateUtils {

    /**
     * 根据年龄获取年代标签
     *
     * @param age 年龄字符串
     * @return
     */
    public static String getYearBaseByAge(String age) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -Integer.valueOf(age));
        Date newDate = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String newDateStr = dateFormat.format(newDate);
        Integer newDateInteger = Integer.valueOf(newDateStr);
        String yearBaseType = "未知";
        if (newDateInteger >= 1940 && newDateInteger < 1950) {
            yearBaseType = "40后";
        } else if (newDateInteger >= 1950 && newDateInteger < 1960) {
            yearBaseType = "50后";
        } else if (newDateInteger >= 1960 && newDateInteger < 1970) {
            yearBaseType = "60后";
        } else if (newDateInteger >= 1970 && newDateInteger < 1980) {
            yearBaseType = "70后";
        } else if (newDateInteger >= 1980 && newDateInteger < 1990) {
            yearBaseType = "80后";
        } else if (newDateInteger >= 1990 && newDateInteger < 2000) {
            yearBaseType = "90后";
        } else if (newDateInteger >= 2000 && newDateInteger < 2010) {
            yearBaseType = "00后";
        } else if (newDateInteger >= 2010) {
            yearBaseType = "10后";
        }
        return yearBaseType;
    }

    /**
     * 获取当前时间戳
     *
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        Date date = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp timestamp = Timestamp.valueOf(nowTime);
        return timestamp;
    }


    public static int getDaysBetweenByStartAndEnd(String startTime, String endTime, String dateFormatString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        Date start = dateFormat.parse(startTime);
        Date end = dateFormat.parse(endTime);
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        endCalendar.setTime(end);
        int days = 0;
        while (startCalendar.before(endCalendar)) {
            startCalendar.add(Calendar.DAY_OF_YEAR, 1);
            days += 1;
        }
        return days;
    }

    public static String getHoursByDate(String timeValue) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");
        Date time = dateFormat.parse(timeValue);
        dateFormat = new SimpleDateFormat("hh");
        String resultHour = dateFormat.format(time);
        return resultHour;
    }
}
