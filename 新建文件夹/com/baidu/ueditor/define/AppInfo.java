/*    */ package com.baidu.ueditor.define;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AppInfo
/*    */ {
/*    */   public static final int SUCCESS = 0;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public static final int PERMISSION_DENIED = 2;
/*    */   public static final int FAILED_CREATE_FILE = 3;
/*    */   public static final int IO_ERROR = 4;
/*    */   public static final int NOT_MULTIPART_CONTENT = 5;
/*    */   public static final int PARSE_REQUEST_ERROR = 6;
/*    */   public static final int NOTFOUND_UPLOAD_DATA = 7;
/*    */   public static final int NOT_ALLOW_FILE_TYPE = 8;
/*    */   public static final int INVALID_ACTION = 101;
/*    */   public static final int CONFIG_ERROR = 102;
/*    */   public static final int PREVENT_HOST = 201;
/*    */   public static final int CONNECTION_ERROR = 202;
/*    */   public static final int REMOTE_FAIL = 203;
/*    */   public static final int NOT_DIRECTORY = 301;
/*    */   public static final int NOT_EXIST = 302;
/*    */   public static final int ILLEGAL = 401;
/* 30 */   public static Map<Integer, String> info = new HashMap<Integer, String>()
/*    */     {
/*    */     
/*    */     };
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   public static String getStateInfo(int key) { return info.get(Integer.valueOf(key)); }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\define\AppInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */