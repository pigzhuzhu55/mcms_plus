/*    */ package com.mingsoft.util;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MD5Util
/*    */ {
/*    */   private static String byteArrayToHexString(byte[] b) {
/* 41 */     StringBuffer resultSb = new StringBuffer();
/* 42 */     for (int i = 0; i < b.length; i++)
/* 43 */       resultSb.append(byteToHexString(b[i])); 
/* 44 */     return resultSb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String byteToHexString(byte b) {
/* 53 */     int n = b;
/* 54 */     if (n < 0)
/* 55 */       n += 256; 
/* 56 */     int d1 = n / 16;
/* 57 */     int d2 = n % 16;
/* 58 */     return String.valueOf(hexDigits[d1]) + hexDigits[d2];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String MD5Encode(String origin, String charsetname) {
/* 68 */     String resultString = null;
/*    */     try {
/* 70 */       resultString = new String(origin);
/* 71 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 72 */       if (charsetname == null || "".equals(charsetname))
/* 73 */       { resultString = byteArrayToHexString(md.digest(resultString.getBytes())); }
/*    */       else
/* 75 */       { resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname))); } 
/* 76 */     } catch (Exception exception) {}
/*    */     
/* 78 */     return resultString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   private static final String[] hexDigits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\MD5Util.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */