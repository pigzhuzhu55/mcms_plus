/*     */ package com.mingsoft.parser;
/*     */ 
/*     */ import com.mingsoft.util.RegexUtil;
/*     */ import com.mingsoft.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public abstract class IParser
/*     */ {
/*     */   public static final String DO_SUFFIX = ".do";
/*     */   public static final String HTM_SUFFIX = ".htm";
/*     */   public static final String HTML_SUFFIX = ".html";
/*     */   protected static final String PROPERTY_VALUE = "=\\s*(\\w*)";
/*     */   protected static final String PRORETY_NAME = "(\\w*)\\s*=";
/*     */   
/*  94 */   protected static int count(String source, String regex) { return RegexUtil.count(source, regex); }
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
/* 110 */   protected static List<String> parseAll(String source, String regex, int find) { return RegexUtil.parseAll(source, regex, find); }
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
/*     */   protected static String parseFirst(String source, String regex, int find) {
/* 124 */     String temp = RegexUtil.parseFirst(source, regex, find);
/* 125 */     return (temp == null) ? "" : temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   protected String htmlCotent = null;
/*     */   
/* 133 */   protected String mobilePath = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   protected String newCotent = null;
/*     */ 
/*     */   
/* 141 */   public String getNewCotent() { return this.newCotent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getProperty(String regex) {
/* 152 */     Map<String, String> listPropertyMap = new HashMap<>();
/* 153 */     String listProperty = parseFirst(this.htmlCotent, regex, 1);
/* 154 */     if (listProperty == null) {
/* 155 */       return listPropertyMap;
/*     */     }
/* 157 */     List<String> listPropertyName = parseAll(listProperty, "(\\w*)\\s*=", 1);
/* 158 */     List<String> listPropertyValue = parseAll(listProperty, "=\\s*(\\w*)", 1);
/* 159 */     for (int i = 0; i < listPropertyName.size(); i++) {
/* 160 */       listPropertyMap.put(((String)listPropertyName.get(i)).toString(), ((String)listPropertyValue.get(i)).toString());
/*     */     }
/* 162 */     return listPropertyMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String parse();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String replaceAll(String regex) {
/* 180 */     if (StringUtil.isBlank(this.newCotent)) {
/* 181 */       this.newCotent = "<!--未找到该标签内容-->";
/*     */     }
/* 183 */     return RegexUtil.replaceAll(this.htmlCotent, regex, this.newCotent);
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
/* 196 */   public String replaceAll(String content, String regex) { return RegexUtil.replaceAll(this.htmlCotent, regex, content); }
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
/*     */   public String replaceFirst(String regex) {
/* 211 */     if (StringUtil.isBlank(this.newCotent)) {
/* 212 */       this.newCotent = "<!--未找到该标签内容-->";
/*     */     }
/* 214 */     return RegexUtil.replaceFirst(this.htmlCotent, regex, this.newCotent);
/*     */   }
/*     */ 
/*     */   
/* 218 */   public void setNewCotent(String newCotent) { this.newCotent = newCotent; }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\IParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */