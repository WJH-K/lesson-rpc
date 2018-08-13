package com.lesson.commons;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by huangguoping on 15/4/24.
 */
public class DateHelper {


    /**
     * 往前/后增加月份
     * @param months
     * @return
     */
    public static String addMonths(int months){
        Date dNow = new Date(); //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, months);
        dBefore = calendar.getTime();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore); //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        return defaultStartDate;
    }

    /**
     * 往前/后增加天
     * @param days
     * @return
     */
    public static Date addDays(int days){
        Date dNow = new Date(); //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.DAY_OF_YEAR, days);
        dBefore = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore); //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        return dBefore;
    }

    /**
     * 往前/后增加天
     * @param days
     * @return
     */
    public static Date addDays(Date dNow,int days){
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.DAY_OF_YEAR, days);
        dBefore = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore); //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        return dBefore;
    }

    /**
     * 往前/后增加小时
     * @param hours
     * @return
     */
    public static Date addHours(int hours){
        Date dNow = new Date(); //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.HOUR, hours);
        dBefore = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore); //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        return dBefore;
    }

    /**
     * 往前/后增加分钟
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date dNow , int minutes){
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.MINUTE, minutes);
        dBefore = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore); //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        return dBefore;
    }

    /**
     * 往前/后增加分钟
     * @param minutes
     * @return
     */
    public static Date addMinutes(int minutes){
        Date dNow = new Date(); //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.MINUTE, minutes);
        dBefore = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore); //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        return dBefore;
    }

    /**
     * 格式化日期
     * @param date
     * @param partten
     * @return
     */
    public static String formatDate(Date date , String partten){
        SimpleDateFormat sdf = new SimpleDateFormat(partten);
        return sdf.format(date);
    }

    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return gc;
    }

    public static Date convertToDate(XMLGregorianCalendar cal) throws Exception{
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }

    public static LocalDateTime dateConvertLocalDateTime(Date date){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return res;
    }

    /**
     * 计算当月最后一天
     * @param date
     * @return
     */
    public static Date LastDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }
}
