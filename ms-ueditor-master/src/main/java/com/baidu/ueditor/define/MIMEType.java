 package com.baidu.ueditor.define;

 import java.util.HashMap;
 import java.util.Map;

 public class MIMEType
 {
   public static final Map<String, String> types = new HashMap<String, String>()
     {

     };





   public static String getSuffix(String mime) { return types.get(mime); }
 }


