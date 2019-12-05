/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class RegexUtil
/*     */ {
/*     */   public static String parseFirst(String source, String regex, int find) {
/*  52 */     String content = null;
/*  53 */     Pattern pattern = Pattern.compile(regex);
/*  54 */     Matcher matcher = pattern.matcher(source);
/*  55 */     if (matcher.find()) {
/*  56 */       content = matcher.group(find);
/*     */     }
/*  58 */     return content;
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
/*     */   
/*     */   public static List<String> parseAll(String source, String regex, int find) {
/*  73 */     List<String> content = new ArrayList<>();
/*  74 */     Pattern pattern = Pattern.compile(regex);
/*  75 */     Matcher matcher = pattern.matcher(source);
/*  76 */     while (matcher.find()) {
/*  77 */       content.add(matcher.group(find));
/*     */     }
/*  79 */     return content;
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
/*     */   
/*     */   public static String replaceAll(String source, String regex, String newContent) {
/*  94 */     Pattern pattern = Pattern.compile(regex);
/*  95 */     Matcher matcher = pattern.matcher(source);
/*  96 */     while (matcher.find()) {
/*  97 */       source = matcher.replaceAll(Matcher.quoteReplacement(newContent.toString().replace("\\", "/")));
/*     */     }
/*  99 */     return source;
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
/*     */   
/*     */   public static String replaceFirst(String source, String regex, String newContent) {
/* 114 */     Pattern pattern = Pattern.compile(regex);
/* 115 */     Matcher matcher = pattern.matcher(source);
/* 116 */     if (matcher.find()) {
/* 117 */       source = matcher.replaceFirst(Matcher.quoteReplacement(newContent));
/*     */     }
/* 119 */     return source;
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
/*     */   public static int count(String source, String regex) {
/* 132 */     Pattern patternList = Pattern.compile(regex);
/* 133 */     Matcher matcherList = patternList.matcher(source);
/* 134 */     int i = 0;
/* 135 */     while (matcherList.find()) {
/* 136 */       i++;
/*     */     }
/* 138 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> doubleRegex(String globalRegex, String singleRegex, String content) {
/* 149 */     Map<String, String> map = new HashMap<>();
/* 150 */     Pattern patternList = Pattern.compile(globalRegex);
/* 151 */     Matcher matcherList = patternList.matcher(content);
/*     */     
/* 153 */     while (matcherList.find()) {
/* 154 */       Pattern _patternList = Pattern.compile(singleRegex);
/* 155 */       Matcher _matcherList = _patternList.matcher(matcherList.group());
/*     */       
/* 157 */       while (_matcherList.find()) {
/* 158 */         if (!StringUtil.isBlank(_matcherList.group(1)) && !StringUtil.isBlank(_matcherList.group(2))) {
/* 159 */           map.put(_matcherList.group(1), _matcherList.group(2));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     return map;
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
/*     */   public static List<Map<Integer, String>> doubleRegexToMap(String globalRegex, String singleRegex, String content, int find) {
/* 176 */     List<Map<Integer, String>> listAll = new ArrayList<>();
/* 177 */     Pattern patternList = Pattern.compile(globalRegex);
/* 178 */     Matcher matcherList = patternList.matcher(content);
/*     */     
/* 180 */     while (matcherList.find()) {
/* 181 */       Pattern _patternList = Pattern.compile(singleRegex);
/* 182 */       Matcher _matcherList = _patternList.matcher(matcherList.group());
/* 183 */       Map<Integer, String> map = new HashMap<>();
/* 184 */       while (_matcherList.find()) {
/*     */         
/* 186 */         for (int i = 0; i < find; i++) {
/* 187 */           if (!StringUtil.isBlank(_matcherList.group(i + 1))) {
/* 188 */             map.put(Integer.valueOf(i), _matcherList.group(i + 1));
/*     */           }
/*     */         } 
/*     */         
/* 192 */         listAll.add(map);
/*     */       } 
/*     */     } 
/* 195 */     return listAll;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> parseAllToList(String content, String regex, int find) {
/* 206 */     List<String> list = new ArrayList<>();
/* 207 */     Pattern pattern = Pattern.compile(regex);
/* 208 */     Matcher matcher = pattern.matcher(content);
/* 209 */     if (matcher.find()) {
/* 210 */       for (int i = 0; i < find; i++) {
/* 211 */         list.add(matcher.group(i + 1));
/*     */       }
/*     */     }
/* 214 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\RegexUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */