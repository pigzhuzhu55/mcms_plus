/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomUtil
/*     */ {
/*     */   public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*     */   public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*     */   public static final String numberChar = "0123456789";
/*     */   
/*     */   public static String generateNumber(int length) {
/*  48 */     StringBuffer sb = new StringBuffer();
/*  49 */     Random random = new Random();
/*  50 */     for (int i = 0; i < length; i++) {
/*  51 */       sb.append("0123456789".charAt(random.nextInt("0123456789".length())));
/*     */     }
/*  53 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateString(int length) {
/*  64 */     StringBuffer sb = new StringBuffer();
/*  65 */     Random random = new Random();
/*  66 */     for (int i = 0; i < length; i++) {
/*  67 */       sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
/*     */     }
/*  69 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateMixString(int length) {
/*  80 */     StringBuffer sb = new StringBuffer();
/*  81 */     Random random = new Random();
/*  82 */     for (int i = 0; i < length; i++) {
/*  83 */       sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
/*     */     }
/*  85 */     return sb.toString();
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
/*  96 */   public static String generateLowerString(int length) { return generateMixString(length).toLowerCase(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static String generateUpperString(int length) { return generateMixString(length).toUpperCase(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateZeroString(int length) {
/* 118 */     StringBuffer sb = new StringBuffer();
/* 119 */     for (int i = 0; i < length; i++) {
/* 120 */       sb.append('0');
/*     */     }
/* 122 */     return sb.toString();
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
/*     */   public static String toFixdLengthString(long num, int fixdlenth) {
/* 135 */     StringBuffer sb = new StringBuffer();
/* 136 */     String strNum = String.valueOf(num);
/* 137 */     if (fixdlenth - strNum.length() >= 0) {
/* 138 */       sb.append(generateZeroString(fixdlenth - strNum.length()));
/*     */     } else {
/* 140 */       throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
/*     */     } 
/* 142 */     sb.append(strNum);
/* 143 */     return sb.toString();
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
/*     */   public static String toFixdLengthString(int num, int fixdlenth) {
/* 156 */     StringBuffer sb = new StringBuffer();
/* 157 */     String strNum = String.valueOf(num);
/* 158 */     if (fixdlenth - strNum.length() >= 0) {
/* 159 */       sb.append(generateZeroString(fixdlenth - strNum.length()));
/*     */     } else {
/* 161 */       throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
/*     */     } 
/* 163 */     sb.append(strNum);
/* 164 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int rondomOneNum(int numLength) {
/* 173 */     Random random = new Random();
/* 174 */     return random.nextInt(numLength);
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\RandomUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */