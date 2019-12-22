package com.lsy.java.test.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lisiyu on 2016/11/14.
 */
public class DateUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date strToDate(String dateStr) {
        LOG.info("DateUtils.strToDate(String dateStr), dateStr="+dateStr);
        Date returnDate;
        try {
            returnDate = sdf.parse(dateStr);
        } catch (Exception e) {
            returnDate = new Date();
//            LOG.error("DateUtils, strToDate Exception: "+ExceptionUtils.getStackTrace(e));
        }
        LOG.info("DateUtils.strToDate(String dateStr), return="+dateStr);
        return returnDate;
    }

    public static Date strToDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date returnDate = df.parse(dateStr);
        return returnDate;
    }
    public static Date strToDate(String dateStr, String format, Locale locale) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(format, locale);
        Date returnDate = df.parse(dateStr);
        return returnDate;
    }
    public static Date strToDate(String dateStr, String format, String localeLanguage) throws ParseException {
        if("ENGLISH".equals(localeLanguage)){
            SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
            Date returnDate = df.parse(dateStr);
            return returnDate;
        } else {
            throw new IllegalArgumentException(localeLanguage);
        }

    }

    public static String dateToStr(Date date){
        return sdf.format(date);
    }

    public static boolean isDateStr(String dateStr) {
        try {
            if(dateStr.charAt(9) == '0'){
                return false;
            }
            sdf.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String dateToStr(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Timestamp dateToTimestamp(Date date){
        return new Timestamp(date.getTime());
    }

    public static Boolean TimestampCompare(long startDate, long endDate, int compareNum){
        LOG.info("------------TimestampCompare start-------------");
        LOG.info("startDate="+startDate);
        LOG.info("endDate="+endDate);
        boolean b = false;
        int millis = (int)(endDate - startDate) / (1000 * 60) ;
        if(millis >= compareNum){b = true;}else{b = false;}
        return  b;
    }

    public static String formatTimeStampSecond(long ts, String df){
        DateFormat sdf = new SimpleDateFormat(df);
        String dateStr = sdf.format(ts);
        return dateStr;
    }

    public static String getDateDifferentDays(int days, String format){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + days);

        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }

    /**
     * 获取上个小时的59分59秒，时间戳
     */
    public static long getLast5959TimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某月的起始时间 00:00:00  毫秒
     * @param month
     * @param offset 0=当月，1=下月，-1=上月
     * @return
     */
    public static long getStartTimeMonth(int month, int offset){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month+offset);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        long timestamp = cal.getTimeInMillis();
        return timestamp;
    }

    /**
     * 获得天0点时间
     * @param cal
     * @return
     */
    public static long getStartTimeForDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获得天23:59:59时间
     * @param cal
     * @return
     */
    public static long getEndTimeForDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔天数
     * @param date1
     * @param date2
     * @return
     */
    public static int diffDaysByDate(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 判断两个时间戳相差的分钟数 (计算时间戳是当天的第几分钟)
     * @param start
     * @param end
     * @return
     */
    public static int diffMinByTs(long start, long end){
        int min = (int)(end - start)/(60*1000);
        return min;
    }

    /**
     * 获得Cal周天数
     * @param cal
     * @return
     */
    public static int getWeekDayByCal(Calendar cal){
        int weekday = cal.get(Calendar.DAY_OF_WEEK)-1;
        weekday = (weekday==0) ? 7 : weekday;
        return weekday;
    }

    public static void main(String[] args) {
        System.out.println(getStartTimeForDay(Calendar.getInstance()));
        System.out.println(getEndTimeForDay(Calendar.getInstance()));
    }


}
