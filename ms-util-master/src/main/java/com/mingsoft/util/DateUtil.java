 package com.mingsoft.util;

 import java.sql.Date;
 import java.sql.Timestamp;
 import java.text.DateFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;

























































 public class DateUtil
 {
   private int year;
   private int month;
   private int day;
   private int hour;
   private int minute;
   private int second;
   private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };





   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");





   public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");





   public static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");





   public static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");





   public static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");





   public static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");





   public static final SimpleDateFormat CHN_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");





   public static final SimpleDateFormat CHN_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");





   public static final SimpleDateFormat CHN_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



   public static final String dtLong = "yyyyMMddHHmmss";



   public static final String simple = "yyyy-MM-dd HH:mm:ss";



   public static final String dtShort = "yyyyMMdd";



   public static String getOrderNum() {
     java.util.Date date = new java.util.Date();
     DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
     return df.format(date);
   }





   public DateUtil() { today(); }









   DateUtil(String inValue) { SetDate(inValue); }








   public DateUtil(long mills) { setTimeInMillis(mills); }












   public DateUtil(int year, int month, int day, int hour, int minute, int second) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(year, month - 1, day, hour, minute, second);
     this.year = calendar.get(1);
     this.month = calendar.get(2) + 1;
     this.day = calendar.get(5);
     this.hour = calendar.get(11);
     this.minute = calendar.get(12);
     this.second = calendar.get(13);
   }







   private void SetDate(String inValue) {
     if (inValue.length() != 14) {
       for (int i = inValue.length(); i < 14; i++) {
         inValue = String.valueOf(inValue) + "0";
       }
       System.out.println(inValue);
     }

     try {
       int year = Integer.parseInt(inValue.substring(0, 4));
       int month = Integer.parseInt(inValue.substring(4, 6));
       int day = Integer.parseInt(inValue.substring(6, 8));
       int hour = Integer.parseInt(inValue.substring(8, 10));
       int minute = Integer.parseInt(inValue.substring(10, 12));
       int second = Integer.parseInt(inValue.substring(12));

       Calendar calendar = Calendar.getInstance();
       calendar.set(year, month - 1, day, hour, minute, second);
       this.year = calendar.get(1);
       this.month = calendar.get(2) + 1;
       this.day = calendar.get(5);
       this.hour = calendar.get(11);
       this.minute = calendar.get(12);
       this.second = calendar.get(13);
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
     }
   }




   private void today() {
     Calendar calendar = Calendar.getInstance();
     this.year = calendar.get(1);
     this.month = calendar.get(2) + 1;
     this.day = calendar.get(5);
     this.hour = calendar.get(11);
     this.minute = calendar.get(12);
     this.second = calendar.get(13);
   }








   public String format(SimpleDateFormat DateFormat) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
     return DateFormat.format(calendar.getTime());
   }






   public String toString() { return format(CHN_DATE_TIME_EXTENDED_FORMAT); }







   public java.util.Date getDate() {
     Calendar date = Calendar.getInstance();
     date.set(5, getDay());
     date.set(2, getMonth() - 1);
     date.set(1, getYear());
     date.set(11, getHour());
     date.set(12, getMinute());
     date.set(13, getSecond());
     return date.getTime();
   }







   public long getTimeInMillis() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
     return calendar.getTime().getTime();
   }






   public void setTimeInMillis(long mills) {
     Date dd = new Date(mills);
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(dd);
     this.year = calendar.get(1);
     this.month = calendar.get(2) + 1;
     this.day = calendar.get(5);
     this.hour = calendar.get(11);
     this.minute = calendar.get(12);
     this.second = calendar.get(13);
   }








   public boolean isLeapYear() { return isLeapYear(this.year); }








   public boolean isLeapYear(int year) {
     if (year % 400 == 0)
       return true;
     if (year % 4 == 0) {
       if (year % 100 == 0) {
         return false;
       }
       return true;
     }
     return false;
   }











   public void setDateTime(int years, int months, int days, int hours, int minutes, int seconds) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(this.year + years, this.month - 1 + months, this.day + days, this.hour + hours, this.minute + minutes, this.second + seconds);
     setTimeInMillis(calendar.getTime().getTime());
   }






   public void addYear(int years) {
     if (this.month == 2 && this.day == 29)

     { if (isLeapYear(this.year + years)) {
         setDateTime(years, 0, 0, 0, 0, 0);
       } else {
         setDateTime(years, 0, -1, 0, 0, 0);
       }  }
     else { setDateTime(years, 0, 0, 0, 0, 0); }

   }





   public void addMonth(int months) {
     int this_day_end = daysOfMonth();
     int that_day_end = dayOfMonth(months);
     if (this.day == this_day_end) {
       this.day = that_day_end;
     } else if (this.day > that_day_end) {
       this.day = that_day_end;
     }
     setDateTime(0, months, 0, 0, 0, 0);
   }







   public void addDay(int days) { setDateTime(0, 0, days, 0, 0, 0); }








   public void addHour(int hours) { setDateTime(0, 0, 0, hours, 0, 0); }








   public void addMinute(int minutes) { setDateTime(0, 0, 0, 0, minutes, 0); }








   public void addSecond(int seconds) { setDateTime(0, 0, 0, 0, 0, seconds); }







   public int daysOfMonth() {
     if (this.month > 12 || this.month < 0)
       return 0;
     if (this.month == 2 && isLeapYear()) {
       return 29;
     }
     return dayArray[this.month - 1];
   }







   public int dayOfMonth(int monthNumber) {
     int yy = monthNumber / 12;
     int mm = monthNumber % 12;
     int year = this.year + yy;
     int month = this.month + mm;

     if (month > 12) {
       month -= 12;
       year++;
     }
     if (month < 1) {
       month += 12;
       year--;
     }

     if (month == 2 && isLeapYear(year)) {
       return 29;
     }
     return dayArray[month - 1];
   }









   public static long diffSec(DateUtil firstDate, DateUtil Lastdate) { return (firstDate.getTimeInMillis() - Lastdate.getTimeInMillis()) / 1000L; }









   public static int diffMonth(Date firstDate, Date Lastdate) {
     if (firstDate.after(Lastdate)) {
       Date t = firstDate;
       firstDate = Lastdate;
       Lastdate = t;
     }
     Calendar startCalendar = Calendar.getInstance();
     startCalendar.setTime(firstDate);
     Calendar endCalendar = Calendar.getInstance();
     endCalendar.setTime(Lastdate);
     Calendar temp = Calendar.getInstance();
     temp.setTime(Lastdate);
     temp.add(5, 1);

     int year = endCalendar.get(1) - startCalendar.get(1);
     int month = endCalendar.get(2) - startCalendar.get(2);

     if (startCalendar.get(5) == 1 && temp.get(5) == 1)
       return year * 12 + month + 1;
     if (startCalendar.get(5) != 1 && temp.get(5) == 1)
       return year * 12 + month;
     if (startCalendar.get(5) == 1 && temp.get(5) != 1) {
       return year * 12 + month;
     }
     return (year * 12 + month - 1 < 0) ? 0 : (year * 12 + month);
   }










   public static int diffDay(DateUtil firstDate, DateUtil Lastdate) { return (int)(firstDate.getTimeInMillis() - Lastdate.getTimeInMillis()) / 1000 / 86400; }








   public static int diffDay(java.util.Date firstDate, java.util.Date Lastdate) {
     firstDate = stringToDate(dateFmtToString(firstDate, "yyyy-MM-dd"), "yyyy-MM-dd");
     Lastdate = stringToDate(dateFmtToString(Lastdate, "yyyy-MM-dd"), "yyyy-MM-dd");
     int _day = (int)((firstDate.getTime() - Lastdate.getTime()) / 1000L / 86400L);
     return _day;
   }








   public static int diffDays(Calendar firstDate, Calendar Lastdate) {
     if (firstDate.after(Lastdate)) {
       Calendar swap = firstDate;
       firstDate = Lastdate;
       Lastdate = swap;
     }
     int days = Lastdate.get(6) - firstDate.get(6);
     int y2 = Lastdate.get(1);
     if (firstDate.get(1) != y2) {
       firstDate = (Calendar)firstDate.clone();
       do {
         days += firstDate.getActualMaximum(6);
         firstDate.add(1, 1);
       } while (firstDate.get(1) != y2);
     }
     return days;
   }








   public static java.util.Date addDays(Date date, int day) {
     if (date == null)
       return null;
     Calendar c = Calendar.getInstance();
     c.setTime(date);
     c.set(5, c.get(5) + day);
     return c.getTime();
   }








   public static java.util.Date removeDays(Date date, int day) {
     if (date == null)
       return null;
     Calendar c = Calendar.getInstance();
     c.setTime(date);
     c.set(5, c.get(5) - day);
     return c.getTime();
   }








   public static java.util.Date addMonths(Date date, int month) {
     if (date == null)
       return null;
     Calendar c = Calendar.getInstance();
     c.setTime(date);
     c.set(2, c.get(2) + month);
     return c.getTime();
   }








   public static java.util.Date removeMonths(java.util.Date date, int month) {
     if (date == null)
       return null;
     Calendar c = Calendar.getInstance();
     c.setTime(date);
     c.set(2, c.get(2) - month);
     return c.getTime();
   }









   public static String dateFmtToString(java.util.Date date, SimpleDateFormat fmt) { return fmt.format(date); }









   public static String dateFmtToString(java.util.Date date) { return (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(date); }











   public static String dateFmtToString(java.util.Date date, String fmt) { return (new SimpleDateFormat(fmt)).format(date); }









   public static java.util.Date stringToDate(String date) { return Date.valueOf(date); }









   public static java.util.Date stringToDate(String date, String ftm) {
     SimpleDateFormat sdf = new SimpleDateFormat(ftm);
     try {
       return sdf.parse(date);
     } catch (ParseException e) {

       e.printStackTrace();

       return null;
     }
   }






   public static java.util.Date stringFmtToDate(String date, String dataFmt) {
     SimpleDateFormat df = new SimpleDateFormat(dataFmt);
     return Date.valueOf(df.format(Date.valueOf(date)));
   }







   public static Timestamp dateToTimestamp(java.util.Date date) {
     String temp = CHN_DATE_TIME_EXTENDED_FORMAT.format(date);
     return Timestamp.valueOf(temp);
   }







   public int getDay() { return this.day; }








   public void setDay(int day) { this.day = day; }








   public int getHour() { return this.hour; }








   public void setHour(int hour) { this.hour = hour; }








   public int getMinute() { return this.minute; }








   public void setMinute(int minute) { this.minute = minute; }








   public int getMonth() { return this.month; }








   public void setMonth(int month) { this.month = month; }








   public int getSecond() { return this.second; }








   public void setSecond(int second) { this.second = second; }








   public int getYear() { return this.year; }








   public void setYear(int year) { this.year = year; }










   public boolean hasCommon(java.util.Date start1, java.util.Date end1, java.util.Date start2, java.util.Date end2) {
     if (end1.before(start2) || end2.before(start1)) {
       return false;
     }
     return true;
   }






   public static boolean judgeDateMsg(String date) {
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
     try {
       Date.valueOf(df.format(Date.valueOf(date)));
     } catch (NumberFormatException numberFormatException) {
       return false;
     }
     return true;
   }







   public static int daysBetween(String beginDate, String endDate) {
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     Calendar cal = Calendar.getInstance();
     Calendar cal2 = Calendar.getInstance();
     try {
       cal.setTime(sdf.parse(beginDate));
       cal2.setTime(sdf.parse(endDate));
       long time1 = cal.getTimeInMillis();
       long time2 = cal2.getTimeInMillis();
       long between_days = (time2 - time1) / 86400000L;
       return (int)between_days;
     } catch (ParseException e) {

       e.printStackTrace();

       return 1;
     }
   }






   public static int secondBetween(String date) {
     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     try {
       long presentDate = sdfSecond.parse(sdfSecond.format(new java.util.Date())).getTime();
       long enterDate = sdfSecond.parse(date).getTime();
       return (int)((presentDate - enterDate) / 1000L);
     } catch (ParseException e) {

       e.printStackTrace();

       return 1;
     }
   }






   public static int secondBetween(java.util.Date date) {
     if (date == null) {
       return 0;
     }
     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     try {
       long presentDate = sdfSecond.parse(sdfSecond.format(new java.util.Date())).getTime();
       long enterDate = date.getTime();
       return (int)((presentDate - enterDate) / 1000L);
     } catch (ParseException e) {

       e.printStackTrace();

       return 1;
     }
   }





   public static String pastTime(Date date) {
     int second = secondBetween(date);
     if (second < 60)
       return String.valueOf(second) + "秒前";
     if (second > 60 && second < 1800)
       return String.valueOf(second / 60) + "分钟前";
     if (second > 1800 && second < 3600)
       return "半小时前";
     if (second > 3600 && second < 86400) {
       return String.valueOf(second / 60 / 60) + "小时前";
     }
     return dateFmtToString(date, "yyyy-MM-dd HH:mm:ss");
   }











   public static int secondBetween(String beginDate, String endDate) {
     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     try {
       long _enginDate = sdfSecond.parse(beginDate).getTime();
       long _endDate = sdfSecond.parse(endDate).getTime();
       return (int)((_enginDate - _endDate) / 1000L);
     } catch (ParseException e) {

       e.printStackTrace();

       return 1;
     }
   }







   public static java.util.Date[] beginEndStringToDate(String date, String split, String fmt) {
     if (StringUtil.isBlank(date) || StringUtil.isBlank(split)) {
       return null;
     }
     String[] _date = date.split(split);
     if (_date.length == 2) {
       java.util.Date[] d = new java.util.Date[2];
       d[0] = stringFmtToDate(_date[0], fmt);
       d[1] = stringFmtToDate(_date[1], fmt);
       return d;
     }
     return null;
   }






   public static Calendar date2Calendar(java.util.Date date) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(date);
     return calendar;
   }
 }


