/*    */ package com.baidu.ueditor;
/*    */ 
/*    */ 
/*    */ public class Encoder
/*    */ {
/*    */   public static String toUnicode(String input) {
/*  7 */     StringBuilder builder = new StringBuilder();
/*  8 */     char[] chars = input.toCharArray(); byte b; int i;
/*    */     char[] arrayOfChar;
/* 10 */     for (i = (arrayOfChar = chars).length, b = 0; b < i; ) { char ch = arrayOfChar[b];
/*    */       
/* 12 */       if (ch < 'Ā') {
/* 13 */         builder.append(ch);
/*    */       } else {
/* 15 */         builder.append("\\u" + Integer.toHexString(ch & Character.MAX_VALUE));
/*    */       } 
/*    */       
/*    */       b++; }
/*    */     
/* 20 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\Encoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */