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

    //获取上个月的某一天 yyyy-MM-dd hh:mm:ss
    public static String  getLastMonDate(int day){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date strDateTo = calendar.getTime();
        return  df4.format(strDateTo)+" 00:00:00";
    }
    //获取本月第一天 yyyy-MM-dd 00:00:00
    public static String getFirstDayOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return df4.format(calendar.getTime())+" 00:00:00";
    }
    //获取本月最后一天 yyyy-MM-dd 23:59:59
    public static String getLastDayOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return df4.format(calendar.getTime())+" 23:59:59";
    }
    //获取当前时间 yyyy-MM-dd
    public  static String getToday(){
        Date d = new Date();
        return  df4.format(d)+" 23:59:59";
    }
    //获取当前打印时间 yyyyMMdd
    public  static String getPrintDate(){
        Date d = new Date();
        return  df2.format(d);
    }
    //获取年月 yyyyMM
    public static String getYearMonth(){
        Date d = new Date();
        return df3.format(d);
    }
    //获取计算时间 yyyy-MM-dd
    public static String getCountDate(){
        Date d = new Date();
        return df4.format(d);
    }

    //获取某月第一天
    public static String get1stDayOfDate(String date){
        Calendar c = Calendar.getInstance();
        String first = null;
        try{
            c.setTime(df4.parse(date));
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
            first = df4.format(c.getTime());
        }catch(Exception e){}
        return first;
    }
    //获取某月最后一天
    public static String getLastDayOfDate(String date){
        Calendar c = Calendar.getInstance();
        String last = null;
        try{
            c.setTime(df4.parse(date));
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            last = df4.format(c.getTime());
        }catch(Exception e){}
        return last;
    }
    public static void main(String[] args) {
        //System.out.println(getFirstDayOfMonth());
        //System.out.println(getLastDayOfMonth());
        System.out.println(get1stDayOfDate("2014-11-12"));
        System.out.println(getLastDayOfDate("2014-11-13"));
    }
}
