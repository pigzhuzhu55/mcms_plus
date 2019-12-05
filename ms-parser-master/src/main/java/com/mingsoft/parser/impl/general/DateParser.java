 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;
 import com.mingsoft.util.StringUtil;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;




















































 public class DateParser
   extends IParser
 {
   private String dateReg = "\\[field.date\\s{0,}(fmt=(.*?))?/]";




   private Date date;




   public DateParser(String htmlContent, Date date) {
     this.htmlCotent = htmlContent;
     this.date = date;
   }








   public DateParser(String htmlContent, Date date, String dateReg) {
     this.htmlCotent = htmlContent;
     this.date = date;
     this.dateReg = dateReg;
   }








   public DateParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }


   public String parse() {
     Pattern pattern = Pattern.compile(this.dateReg);
     Matcher matcher = pattern.matcher(this.htmlCotent);
     while (matcher.find()) {
       String date = matcher.group();
       this.htmlCotent = this.htmlCotent.replace(date, date(date));
     }
     return this.htmlCotent;
   }











   private String date(String reg) {
     String typeDate = "yyyy-MM-dd hh:mm:ss";
     String fmt = parseFirst(this.htmlCotent, this.dateReg, 2);
     if (!StringUtil.isBlank(fmt)) {
       typeDate = fmt;
     }

     String srtDate = "时间读取失败";
     if (this.date != null) {
       try {
         SimpleDateFormat forDate = new SimpleDateFormat(typeDate);
         srtDate = forDate.format(this.date);
       } catch (Exception exception) {}
     }


     return srtDate;
   }
 }


