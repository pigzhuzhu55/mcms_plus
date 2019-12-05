/*     */ package com.mingsoft.parser.impl.general;
/*     */ 
/*     */ import com.mingsoft.parser.IParser;
/*     */ import com.mingsoft.util.FileUtil;
/*     */ import com.mingsoft.util.StringUtil;
/*     */ import java.io.File;
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
/*     */ public class IncludeParser
/*     */   extends IParser
/*     */ {
/*     */   String path;
/*     */   private static final String INCLUDE = "\\{ms:include filename\\=(.*?)\\s*/}";
/*     */   
/*     */   public IncludeParser(String htmlContent, String path, String mobilePath) {
/*  57 */     this.mobilePath = mobilePath;
/*  58 */     this.htmlCotent = htmlContent;
/*  59 */     this.path = path;
/*  60 */     if (!StringUtil.isBlank(mobilePath)) {
/*  61 */       this.path = String.valueOf(path) + File.separator + this.mobilePath;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IncludeParser(String htmlContent, String path) {
/*  67 */     this.htmlCotent = htmlContent;
/*  68 */     this.path = path;
/*     */   }
/*     */ 
/*     */   
/*     */   public String parse() {
/*  73 */     String html = this.htmlCotent;
/*  74 */     int strNum = includeNum(this.htmlCotent);
/*  75 */     while (strNum != 0) {
/*     */       
/*  77 */       String htmlInclude = includeContentPrase(this.htmlCotent, this.path);
/*  78 */       this.newCotent = htmlInclude;
/*     */       
/*  80 */       this.htmlCotent = replaceFirst("\\{ms:include filename\\=(.*?)\\s*/}");
/*  81 */       html = this.htmlCotent;
/*  82 */       strNum = includeNum(this.htmlCotent);
/*     */     } 
/*     */     
/*  85 */     return html;
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
/*     */   private static String includeContentPrase(String html, String path) {
/*  98 */     String includeContent = "";
/*  99 */     Pattern patternL = Pattern.compile("\\{ms:include filename\\=(.*?)\\s*/}");
/* 100 */     Matcher matcherL = patternL.matcher(html);
/* 101 */     if (matcherL.find()) {
/* 102 */       String includeName = matcherL.group(1);
/*     */       
/* 104 */       File file = new File(String.valueOf(path) + File.separator + includeName);
/* 105 */       if (!file.exists()) {
/* 106 */         includeContent = String.valueOf(includeName) + "不存在，请仔细检查该模版的文件！";
/*     */       } else {
/* 108 */         includeContent = FileUtil.readFile(String.valueOf(path) + File.separator + includeName);
/*     */       } 
/*     */     } 
/* 111 */     return includeContent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int includeNum(String html) {
/* 122 */     int includeNum = count(html, "\\{ms:include filename\\=(.*?)\\s*/}");
/* 123 */     return includeNum;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\impl\general\IncludeParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */