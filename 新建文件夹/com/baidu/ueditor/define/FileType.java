/*    */ package com.baidu.ueditor.define;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class FileType
/*    */ {
/*    */   public static final String JPG = "JPG";
/* 10 */   private static final Map<String, String> types = new HashMap<String, String>()
/*    */     {
/*    */     
/*    */     };
/*    */ 
/*    */ 
/*    */   
/* 17 */   public static String getSuffix(String key) { return types.get(key); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public static String getSuffixByFilename(String filename) { return filename.substring(filename.lastIndexOf(".")).toLowerCase(); }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\define\FileType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */