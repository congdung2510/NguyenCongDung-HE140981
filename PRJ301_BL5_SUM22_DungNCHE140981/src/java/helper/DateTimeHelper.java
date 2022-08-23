/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import constant.WorkingTime;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ngo Tung Son
 */
public class DateTimeHelper {
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");


    public static int getDayOfMonth(java.util.Date datetime) {
        java.util.Date date = removeTime(datetime);
        try{
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate.getDayOfMonth();
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    public static Timestamp getTimeStamp(Date date)
    {
        return new Timestamp(date.getTime());
    }

    public static int getDayOfWeek(Date datetime) {
        LocalDate localDate = datetime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate.getDayOfWeek().getValue();
    }

    public static Date addDays(Date d, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, days);
        Date newdate = c.getTime();
        return newdate;
    }
    
    public static Date addMonths(Date d, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, months);
        Date newdate = c.getTime();
        return newdate;
    }

    public static Date removeTime(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date newdate = c.getTime();
        return newdate;
    }
    
    public static Date stringToDate(String dateString){
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not convert String to util.Date: "+ e.getMessage());
        }
    }

    public static Date stringToDateTime(String dateTimeString){
        try {
            return dateTimeFormat.parse(dateTimeString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not convert String to util.Date with time: "+ e.getMessage());
        }
    }
            
    
    public static ArrayList<Date> getDates(Date from, Date to)
    {
        ArrayList<Date> dates = new ArrayList<>();
        int count = 0;
        while(true)
        {
            Date item = addDays(from, count);
            dates.add(item);
            count++;
            if(item.getTime() == to.getTime())
                break;
        }
        return dates;
    }
    
    public static Date getDateFrom(Timestamp ts)
    {
        return new Date(ts.getTime());
    }

    public static int getMonth(Date date) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate.getMonth().getValue();
    }

    public static boolean isLeapYear(Date date) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int year = localDate.getYear();
        if (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0)){
            return true;
        }else {
            return false;
        }
    }

    public static Date toEndDay(Date toEndDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(toEndDay);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        Date newdate = c.getTime();
        return newdate;
    }

    public static Double calculateTimeNotWorking(Date offFrom, Date offTo) {
        LocalDateTime offFromTime = Instant.ofEpochMilli(offFrom.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
        LocalDateTime offToTime = Instant.ofEpochMilli(offTo.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
        Duration workingTime = Duration.between(offFromTime, offFromTime.withHour(WorkingTime.ENT_TIME.getHour()).withMinute(WorkingTime.ENT_TIME.getMinute()).withSecond(WorkingTime.ENT_TIME.getMinute()));
        Duration offTime = Duration.between(offToTime.withHour(WorkingTime.START_TIME.getHour()).withMinute(WorkingTime.START_TIME.getMinute()).withSecond(WorkingTime.START_TIME.getMinute()), offToTime);

        double absentTime = 0;
        if(workingTime.getSeconds()/3600 >= WorkingTime.REQUIRE_WORKING_PART_TIME){
            absentTime = absentTime + 0.5;
        }
        if((8 - offTime.getSeconds()/3600) <= WorkingTime.REQUIRE_WORKING_PART_TIME){
            absentTime = absentTime + 0.5;
        }
        return absentTime;
    }

    public static int timeToEnd(Date offFrom) {
        LocalDateTime offFromTime = Instant.ofEpochMilli(offFrom.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();

        Duration leavingTime = Duration.between(offFromTime, offFromTime.withHour(WorkingTime.ENT_TIME.getHour()).withMinute(WorkingTime.ENT_TIME.getMinute()).withSecond(WorkingTime.ENT_TIME.getSecond()));
        return Math.round(leavingTime.getSeconds()/3600);
    }

    public static  int timeToStart(Date offTo){
        LocalDateTime offToTime = Instant.ofEpochMilli(offTo.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();

        Duration leavingTime = Duration.between(offToTime.withHour(WorkingTime.START_TIME.getHour()).withMinute(WorkingTime.START_TIME.getMinute()).withSecond(WorkingTime.START_TIME.getSecond()), offToTime);
        return Math.round(leavingTime.getSeconds()/3600);
    }

    public static Integer getDayOfWeek(Date thisWeek, Integer day) {
        LocalDateTime weekTime = Instant.ofEpochMilli(thisWeek.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
        LocalDateTime time =  weekTime.withDayOfMonth(day);
        return time.getDayOfWeek().getValue();
    }
}