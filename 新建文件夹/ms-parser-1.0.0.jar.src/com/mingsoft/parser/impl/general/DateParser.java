/*     */ package com.mingsoft.parser.impl.general;
/*     */ 
/*     */ import com.mingsoft.parser.IParser;
/*     */ import com.mingsoft.util.StringUtil;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class DateParser
/*     */   extends IParser
/*     */ {
/*  64 */   private String dateReg = "\\[field.date\\s{0,}(fmt=(.*?))?/]";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Date date;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateParser(String htmlContent, Date date) {
/*  75 */     this.htmlCotent = htmlContent;
/*  76 */     this.date = date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateParser(String htmlContent, Date date, String dateReg) {
/*  87 */     this.htmlCotent = htmlContent;
/*  88 */     this.date = date;
/*  89 */     this.dateReg = dateReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateParser(String htmlContent, String newContent) {
/* 100 */     this.htmlCotent = htmlContent;
/* 101 */     this.newCotent = newContent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String parse() {
/* 106 */     Pattern pattern = Pattern.compile(this.dateReg);
/* 107 */     Matcher matcher = pattern.matcher(this.htmlCotent);
/* 108 */     while (matcher.find()) {
/* 109 */       String date = matcher.group();
/* 110 */       this.htmlCotent = this.htmlCotent.replace(date, date(date));
/*     */     } 
/* 112 */     return this.htmlCotent;
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
/*     */   private String date(String reg) {
/* 126 */     String typeDate = "yyyy-MM-dd hh:mm:ss";
/* 127 */     String fmt = parseFirst(this.htmlCotent, this.dateReg, 2);
/* 128 */     if (!StringUtil.isBlank(fmt)) {
/* 129 */       typeDate = fmt;
/*     */     }
/*     */     
/* 132 */     String srtDate = "时间读取失败";
/* 133 */     if (this.date != null) {
/*     */       try {
/* 135 */         SimpleDateFormat forDate = new SimpleDateFormat(typeDate);
/* 136 */         srtDate = forDate.format(this.date);
/* 137 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 141 */     return srtDate;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\impl\general\DateParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */