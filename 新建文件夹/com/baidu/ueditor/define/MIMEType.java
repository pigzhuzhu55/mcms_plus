/*    */ package com.baidu.ueditor.define;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MIMEType
/*    */ {
/*  8 */   public static final Map<String, String> types = new HashMap<String, String>()
/*    */     {
/*    */     
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   public static String getSuffix(String mime) { return types.get(mime); }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\define\MIMEType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */