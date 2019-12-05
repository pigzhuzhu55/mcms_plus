/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import java.sql.Date;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateUtil
/*     */ {
/*     */   private int year;
/*     */   private int month;
/*     */   private int day;
/*     */   private int hour;
/*     */   private int minute;
/*     */   private int second;
/*  75 */   private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final SimpleDateFormat CHN_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static final SimpleDateFormat CHN_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public static final SimpleDateFormat CHN_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String dtLong = "yyyyMMddHHmmss";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String simple = "yyyy-MM-dd HH:mm:ss";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String dtShort = "yyyyMMdd";
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOrderNum() {
/* 146 */     Date date = new Date();
/* 147 */     DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/* 148 */     return df.format(date);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public DateUtil() { today(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   DateUtil(String inValue) { SetDate(inValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public DateUtil(long mills) { setTimeInMillis(mills); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateUtil(int year, int month, int day, int hour, int minute, int second) {
/* 188 */     Calendar calendar = Calendar.getInstance();
/* 189 */     calendar.set(year, month - 1, day, hour, minute, second);
/* 190 */     this.year = calendar.get(1);
/* 191 */     this.month = calendar.get(2) + 1;
/* 192 */     this.day = calendar.get(5);
/* 193 */     this.hour = calendar.get(11);
/* 194 */     this.minute = calendar.get(12);
/* 195 */     this.second = calendar.get(13);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void SetDate(String inValue) {
/* 205 */     if (inValue.length() != 14) {
/* 206 */       for (int i = inValue.length(); i < 14; i++) {
/* 207 */         inValue = String.valueOf(inValue) + "0";
/*     */       }
/* 209 */       System.out.println(inValue);
/*     */     } 
/*     */     
/*     */     try {
/* 213 */       int year = Integer.parseInt(inValue.substring(0, 4));
/* 214 */       int month = Integer.parseInt(inValue.substring(4, 6));
/* 215 */       int day = Integer.parseInt(inValue.substring(6, 8));
/* 216 */       int hour = Integer.parseInt(inValue.substring(8, 10));
/* 217 */       int minute = Integer.parseInt(inValue.substring(10, 12));
/* 218 */       int second = Integer.parseInt(inValue.substring(12));
/*     */       
/* 220 */       Calendar calendar = Calendar.getInstance();
/* 221 */       calendar.set(year, month - 1, day, hour, minute, second);
/* 222 */       this.year = calendar.get(1);
/* 223 */       this.month = calendar.get(2) + 1;
/* 224 */       this.day = calendar.get(5);
/* 225 */       this.hour = calendar.get(11);
/* 226 */       this.minute = calendar.get(12);
/* 227 */       this.second = calendar.get(13);
/*     */     }
/* 229 */     catch (Exception e) {
/* 230 */       System.out.println(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void today() {
/* 238 */     Calendar calendar = Calendar.getInstance();
/* 239 */     this.year = calendar.get(1);
/* 240 */     this.month = calendar.get(2) + 1;
/* 241 */     this.day = calendar.get(5);
/* 242 */     this.hour = calendar.get(11);
/* 243 */     this.minute = calendar.get(12);
/* 244 */     this.second = calendar.get(13);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String format(SimpleDateFormat DateFormat) {
/* 255 */     Calendar calendar = Calendar.getInstance();
/* 256 */     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
/* 257 */     return DateFormat.format(calendar.getTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   public String toString() { return format(CHN_DATE_TIME_EXTENDED_FORMAT); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate() {
/* 274 */     Calendar date = Calendar.getInstance();
/* 275 */     date.set(5, getDay());
/* 276 */     date.set(2, getMonth() - 1);
/* 277 */     date.set(1, getYear());
/* 278 */     date.set(11, getHour());
/* 279 */     date.set(12, getMinute());
/* 280 */     date.set(13, getSecond());
/* 281 */     return date.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimeInMillis() {
/* 291 */     Calendar calendar = Calendar.getInstance();
/* 292 */     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
/* 293 */     return calendar.getTime().getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeInMillis(long mills) {
/* 302 */     Date dd = new Date(mills);
/* 303 */     Calendar calendar = Calendar.getInstance();
/* 304 */     calendar.setTime(dd);
/* 305 */     this.year = calendar.get(1);
/* 306 */     this.month = calendar.get(2) + 1;
/* 307 */     this.day = calendar.get(5);
/* 308 */     this.hour = calendar.get(11);
/* 309 */     this.minute = calendar.get(12);
/* 310 */     this.second = calendar.get(13);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public boolean isLeapYear() { return isLeapYear(this.year); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeapYear(int year) {
/* 330 */     if (year % 400 == 0)
/* 331 */       return true; 
/* 332 */     if (year % 4 == 0) {
/* 333 */       if (year % 100 == 0) {
/* 334 */         return false;
/*     */       }
/* 336 */       return true;
/*     */     } 
/* 338 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDateTime(int years, int months, int days, int hours, int minutes, int seconds) {
/* 352 */     Calendar calendar = Calendar.getInstance();
/* 353 */     calendar.set(this.year + years, this.month - 1 + months, this.day + days, this.hour + hours, this.minute + minutes, this.second + seconds);
/* 354 */     setTimeInMillis(calendar.getTime().getTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addYear(int years) {
/* 363 */     if (this.month == 2 && this.day == 29)
/*     */     
/* 365 */     { if (isLeapYear(this.year + years)) {
/* 366 */         setDateTime(years, 0, 0, 0, 0, 0);
/*     */       } else {
/* 368 */         setDateTime(years, 0, -1, 0, 0, 0);
/*     */       }  }
/* 370 */     else { setDateTime(years, 0, 0, 0, 0, 0); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMonth(int months) {
/* 379 */     int this_day_end = daysOfMonth();
/* 380 */     int that_day_end = dayOfMonth(months);
/* 381 */     if (this.day == this_day_end) {
/* 382 */       this.day = that_day_end;
/* 383 */     } else if (this.day > that_day_end) {
/* 384 */       this.day = that_day_end;
/*     */     } 
/* 386 */     setDateTime(0, months, 0, 0, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 395 */   public void addDay(int days) { setDateTime(0, 0, days, 0, 0, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 404 */   public void addHour(int hours) { setDateTime(0, 0, 0, hours, 0, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 413 */   public void addMinute(int minutes) { setDateTime(0, 0, 0, 0, minutes, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 422 */   public void addSecond(int seconds) { setDateTime(0, 0, 0, 0, 0, seconds); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int daysOfMonth() {
/* 431 */     if (this.month > 12 || this.month < 0)
/* 432 */       return 0; 
/* 433 */     if (this.month == 2 && isLeapYear()) {
/* 434 */       return 29;
/*     */     }
/* 436 */     return dayArray[this.month - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int dayOfMonth(int monthNumber) {
/* 446 */     int yy = monthNumber / 12;
/* 447 */     int mm = monthNumber % 12;
/* 448 */     int year = this.year + yy;
/* 449 */     int month = this.month + mm;
/*     */     
/* 451 */     if (month > 12) {
/* 452 */       month -= 12;
/* 453 */       year++;
/*     */     } 
/* 455 */     if (month < 1) {
/* 456 */       month += 12;
/* 457 */       year--;
/*     */     } 
/*     */     
/* 460 */     if (month == 2 && isLeapYear(year)) {
/* 461 */       return 29;
/*     */     }
/* 463 */     return dayArray[month - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 474 */   public static long diffSec(DateUtil firstDate, DateUtil Lastdate) { return (firstDate.getTimeInMillis() - Lastdate.getTimeInMillis()) / 1000L; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int diffMonth(Date firstDate, Date Lastdate) {
/* 485 */     if (firstDate.after(Lastdate)) {
/* 486 */       Date t = firstDate;
/* 487 */       firstDate = Lastdate;
/* 488 */       Lastdate = t;
/*     */     } 
/* 490 */     Calendar startCalendar = Calendar.getInstance();
/* 491 */     startCalendar.setTime(firstDate);
/* 492 */     Calendar endCalendar = Calendar.getInstance();
/* 493 */     endCalendar.setTime(Lastdate);
/* 494 */     Calendar temp = Calendar.getInstance();
/* 495 */     temp.setTime(Lastdate);
/* 496 */     temp.add(5, 1);
/*     */     
/* 498 */     int year = endCalendar.get(1) - startCalendar.get(1);
/* 499 */     int month = endCalendar.get(2) - startCalendar.get(2);
/*     */     
/* 501 */     if (startCalendar.get(5) == 1 && temp.get(5) == 1)
/* 502 */       return year * 12 + month + 1; 
/* 503 */     if (startCalendar.get(5) != 1 && temp.get(5) == 1)
/* 504 */       return year * 12 + month; 
/* 505 */     if (startCalendar.get(5) == 1 && temp.get(5) != 1) {
/* 506 */       return year * 12 + month;
/*     */     }
/* 508 */     return (year * 12 + month - 1 < 0) ? 0 : (year * 12 + month);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 520 */   public static int diffDay(DateUtil firstDate, DateUtil Lastdate) { return (int)(firstDate.getTimeInMillis() - Lastdate.getTimeInMillis()) / 1000 / 86400; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int diffDay(Date firstDate, Date Lastdate) {
/* 530 */     firstDate = stringToDate(dateFmtToString(firstDate, "yyyy-MM-dd"), "yyyy-MM-dd");
/* 531 */     Lastdate = stringToDate(dateFmtToString(Lastdate, "yyyy-MM-dd"), "yyyy-MM-dd");
/* 532 */     int _day = (int)((firstDate.getTime() - Lastdate.getTime()) / 1000L / 86400L);
/* 533 */     return _day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int diffDays(Calendar firstDate, Calendar Lastdate) {
/* 544 */     if (firstDate.after(Lastdate)) {
/* 545 */       Calendar swap = firstDate;
/* 546 */       firstDate = Lastdate;
/* 547 */       Lastdate = swap;
/*     */     } 
/* 549 */     int days = Lastdate.get(6) - firstDate.get(6);
/* 550 */     int y2 = Lastdate.get(1);
/* 551 */     if (firstDate.get(1) != y2) {
/* 552 */       firstDate = (Calendar)firstDate.clone();
/*     */       do {
/* 554 */         days += firstDate.getActualMaximum(6);
/* 555 */         firstDate.add(1, 1);
/* 556 */       } while (firstDate.get(1) != y2);
/*     */     } 
/* 558 */     return days;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date addDays(Date date, int day) {
/* 569 */     if (date == null)
/* 570 */       return null; 
/* 571 */     Calendar c = Calendar.getInstance();
/* 572 */     c.setTime(date);
/* 573 */     c.set(5, c.get(5) + day);
/* 574 */     return c.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date removeDays(Date date, int day) {
/* 585 */     if (date == null)
/* 586 */       return null; 
/* 587 */     Calendar c = Calendar.getInstance();
/* 588 */     c.setTime(date);
/* 589 */     c.set(5, c.get(5) - day);
/* 590 */     return c.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date addMonths(Date date, int month) {
/* 601 */     if (date == null)
/* 602 */       return null; 
/* 603 */     Calendar c = Calendar.getInstance();
/* 604 */     c.setTime(date);
/* 605 */     c.set(2, c.get(2) + month);
/* 606 */     return c.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date removeMonths(Date date, int month) {
/* 617 */     if (date == null)
/* 618 */       return null; 
/* 619 */     Calendar c = Calendar.getInstance();
/* 620 */     c.setTime(date);
/* 621 */     c.set(2, c.get(2) - month);
/* 622 */     return c.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 633 */   public static String dateFmtToString(Date date, SimpleDateFormat fmt) { return fmt.format(date); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 643 */   public static String dateFmtToString(Date date) { return (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(date); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 655 */   public static String dateFmtToString(Date date, String fmt) { return (new SimpleDateFormat(fmt)).format(date); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 665 */   public static Date stringToDate(String date) { return Date.valueOf(date); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date stringToDate(String date, String ftm) {
/* 676 */     SimpleDateFormat sdf = new SimpleDateFormat(ftm);
/*     */     try {
/* 678 */       return sdf.parse(date);
/* 679 */     } catch (ParseException e) {
/*     */       
/* 681 */       e.printStackTrace();
/*     */       
/* 683 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date stringFmtToDate(String date, String dataFmt) {
/* 693 */     SimpleDateFormat df = new SimpleDateFormat(dataFmt);
/* 694 */     return Date.valueOf(df.format(Date.valueOf(date)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Timestamp dateToTimestamp(Date date) {
/* 704 */     String temp = CHN_DATE_TIME_EXTENDED_FORMAT.format(date);
/* 705 */     return Timestamp.valueOf(temp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 714 */   public int getDay() { return this.day; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 723 */   public void setDay(int day) { this.day = day; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 732 */   public int getHour() { return this.hour; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 741 */   public void setHour(int hour) { this.hour = hour; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 750 */   public int getMinute() { return this.minute; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 759 */   public void setMinute(int minute) { this.minute = minute; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 768 */   public int getMonth() { return this.month; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 777 */   public void setMonth(int month) { this.month = month; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 786 */   public int getSecond() { return this.second; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 795 */   public void setSecond(int second) { this.second = second; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 804 */   public int getYear() { return this.year; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 813 */   public void setYear(int year) { this.year = year; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCommon(Date start1, Date end1, Date start2, Date end2) {
/* 825 */     if (end1.before(start2) || end2.before(start1)) {
/* 826 */       return false;
/*     */     }
/* 828 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean judgeDateMsg(String date) {
/* 837 */     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
/*     */     try {
/* 839 */       Date.valueOf(df.format(Date.valueOf(date)));
/* 840 */     } catch (NumberFormatException numberFormatException) {
/* 841 */       return false;
/*     */     } 
/* 843 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int daysBetween(String beginDate, String endDate) {
/* 853 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 854 */     Calendar cal = Calendar.getInstance();
/* 855 */     Calendar cal2 = Calendar.getInstance();
/*     */     try {
/* 857 */       cal.setTime(sdf.parse(beginDate));
/* 858 */       cal2.setTime(sdf.parse(endDate));
/* 859 */       long time1 = cal.getTimeInMillis();
/* 860 */       long time2 = cal2.getTimeInMillis();
/* 861 */       long between_days = (time2 - time1) / 86400000L;
/* 862 */       return (int)between_days;
/* 863 */     } catch (ParseException e) {
/*     */       
/* 865 */       e.printStackTrace();
/*     */       
/* 867 */       return 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int secondBetween(String date) {
/* 877 */     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 879 */       long presentDate = sdfSecond.parse(sdfSecond.format(new Date())).getTime();
/* 880 */       long enterDate = sdfSecond.parse(date).getTime();
/* 881 */       return (int)((presentDate - enterDate) / 1000L);
/* 882 */     } catch (ParseException e) {
/*     */       
/* 884 */       e.printStackTrace();
/*     */       
/* 886 */       return 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int secondBetween(Date date) {
/* 896 */     if (date == null) {
/* 897 */       return 0;
/*     */     }
/* 899 */     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 901 */       long presentDate = sdfSecond.parse(sdfSecond.format(new Date())).getTime();
/* 902 */       long enterDate = date.getTime();
/* 903 */       return (int)((presentDate - enterDate) / 1000L);
/* 904 */     } catch (ParseException e) {
/*     */       
/* 906 */       e.printStackTrace();
/*     */       
/* 908 */       return 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String pastTime(Date date) {
/* 917 */     int second = secondBetween(date);
/* 918 */     if (second < 60)
/* 919 */       return String.valueOf(second) + "秒前"; 
/* 920 */     if (second > 60 && second < 1800)
/* 921 */       return String.valueOf(second / 60) + "分钟前"; 
/* 922 */     if (second > 1800 && second < 3600)
/* 923 */       return "半小时前"; 
/* 924 */     if (second > 3600 && second < 86400) {
/* 925 */       return String.valueOf(second / 60 / 60) + "小时前";
/*     */     }
/* 927 */     return dateFmtToString(date, "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int secondBetween(String beginDate, String endDate) {
/* 941 */     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 943 */       long _enginDate = sdfSecond.parse(beginDate).getTime();
/* 944 */       long _endDate = sdfSecond.parse(endDate).getTime();
/* 945 */       return (int)((_enginDate - _endDate) / 1000L);
/* 946 */     } catch (ParseException e) {
/*     */       
/* 948 */       e.printStackTrace();
/*     */       
/* 950 */       return 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date[] beginEndStringToDate(String date, String split, String fmt) {
/* 961 */     if (StringUtil.isBlank(date) || StringUtil.isBlank(split)) {
/* 962 */       return null;
/*     */     }
/* 964 */     String[] _date = date.split(split);
/* 965 */     if (_date.length == 2) {
/* 966 */       Date[] d = new Date[2];
/* 967 */       d[0] = stringFmtToDate(_date[0], fmt);
/* 968 */       d[1] = stringFmtToDate(_date[1], fmt);
/* 969 */       return d;
/*     */     } 
/* 971 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Calendar date2Calendar(Date date) {
/* 980 */     Calendar calendar = Calendar.getInstance();
/* 981 */     calendar.setTime(date);
/* 982 */     return calendar;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\DateUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */