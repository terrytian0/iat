package com.terry.iat.service.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getDate(){
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }
    public static Date getDate(String date, String formart) throws ParseException {
        DateFormat df = new SimpleDateFormat(formart);
        return df.parse(date);
    }

    public static String getDate(Date date, String formart) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(formart);
        return df.format(date);
    }

    public static int getMinute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static int getSecond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }

    public static int getHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR);
    }

    public static String dateFormart(Date date, String formart) {
        DateFormat df = new SimpleDateFormat(formart);
        return df.format(date);
    }

    public static Date dateFormart(String date, String formart) throws ParseException{
        SimpleDateFormat  df = new SimpleDateFormat(formart);
        return df.parse(date);
    }

    public static Date addHour(Date date,int hour){
        return add(date,Calendar.HOUR,hour);
    }


    public static Date addMinute(Date date,int minute){
        return add(date,Calendar.MINUTE,minute);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static Date addDay(Date date,int day){
        return add(date,Calendar.DATE,day);
    }

    public static Date addWeek(Date date,int week){
        return add(date,Calendar.DATE,week*7);
    }
    public static int getWeekDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return  cal.get(Calendar.DAY_OF_WEEK);
    }

    public static Date getFirstDayOfWeek(Date date)throws ParseException{
        int i = getWeekDay(date);
        if(i==1){
            i=7;
        }else{
            i=i-1;
        }
        return dateFormart(dateFormart(addDay(date,~i+2),"yyyy-MM-dd"),"yyyy-MM-dd");
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(getWeekDay(addDay(new Date(),-3)));
    }
}