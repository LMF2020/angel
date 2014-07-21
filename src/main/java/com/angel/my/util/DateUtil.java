package com.angel.my.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间函数
 */
public class DateUtil {

    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat df2 = new SimpleDateFormat("yyyyMMdd");
    public static DateFormat df3 = new SimpleDateFormat("yyyyMM");
    public static DateFormat df4 = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 获取上个月的某一天
     * @return
     */
    public static String  getLastMonDate(int day){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date strDateTo = calendar.getTime();
        return  df4.format(strDateTo)+" 00:00:00";
    }

    public  static String getToday(){
        Date d = new Date();
        return  df4.format(d)+" 23:59:59";
    }

    public  static String getPrintDate(){
        Date d = new Date();
        return  df2.format(d);
    }

    public static String getYearMonth(){
        Date d = new Date();
        return df3.format(d);
    }

    public static String getCountDate(){
        Date d = new Date();
        return df4.format(d);
    }

    public static void main(String[] args) {
        System.out.println(getLastMonDate(28));
    }
}
