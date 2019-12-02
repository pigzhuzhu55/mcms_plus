 package net.mingsoft.basic.util;

 import cn.hutool.core.util.ArrayUtil;
 import java.io.File;
 import java.io.UnsupportedEncodingException;
 import java.util.Arrays;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Random;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import org.apache.commons.lang3.StringUtils;






























 public class StringUtil
 {
   public static boolean checkLength(String str, int minLength, int maxLength) {
     if (str != null) {
       int len = str.length();
       if (minLength == 0)
         return (len <= maxLength);
       if (maxLength == 0) {
         return (len >= minLength);
       }
       return (len >= minLength && len <= maxLength);
     }
     return false;
   }






   public static boolean isIntegers(String[] str) {
     try {
       for (int i = 0; i < str.length; i++)
         Integer.parseInt(str[i]);
       return true;
     } catch (Exception e) {
       return false;
     }
   }






   public static Integer[] stringsToIntegers(String[] str) {
     Integer[] array = new Integer[str.length];
     for (int i = 0; i < array.length; i++)
       array[i] = Integer.valueOf(Integer.parseInt(str[i]));
     return array;
   }






   public static int[] stringsToInts(String[] str) {
     int[] array = new int[str.length];
     for (int i = 0; i < array.length; i++)
       array[i] = Integer.parseInt(str[i]);
     return array;
   }







   public static String isoToUTF8(String str) {
     if (StringUtils.isEmpty(str))
       return "";
     try {
       return new String(str.getBytes("ISO-8859-1"), "UTF-8");
     } catch (UnsupportedEncodingException unsupportedEncodingException) {

       return "";
     }
   }






   public static double[] stringsToDoubles(String[] str) {
     double[] array = new double[str.length];
     for (int i = 0; i < array.length; i++)
       array[i] = Double.parseDouble(str[i]);
     return array;
   }






   public static boolean isDoubles(String[] str) {
     try {
       for (int i = 0; i < str.length; i++)
         Double.parseDouble(str[i]);
       return true;
     } catch (Exception e) {
       return false;
     }
   }






   public static String getDateSimpleStr() { return String.valueOf(System.currentTimeMillis()); }







   public static String randomNumber(int len) {
     StringBuffer sb = new StringBuffer();
     Random random = new Random();
     for (int i = 0; i < len; i++) {
       sb.append(Math.abs(random.nextInt()) % 10);
     }
     return sb.toString();
   }






   public static String int2String(int comment) {
     String srt = "";
     srt = Integer.toString(comment);
     return srt;
   }











   public static String removeRepeatStr(String content, String target) {
     StringBuffer sb = new StringBuffer(content);
     for (int i = 0; i < sb.length() - 1; i++) {

       if (sb.substring(i, i + target.length()).equals(target) && sb.substring(i, i + target.length()).equals(sb.substring(i + 1, i + target.length() + 1))) {
         sb.delete(i, i + target.length());
         if (i + target.length() + 1 > sb.length()) {
           break;
         }
         i--;
       }
     }


     return sb.toString();
   }







   public static String sort(String str, String delimiter) {
     String[] articleTypeArrays = str.split(delimiter);
     Arrays.sort((Object[])articleTypeArrays);
     return ArrayUtil.join((Object[])articleTypeArrays, delimiter);
   }







   public static boolean isMobile(String phoneNumber) {
     phoneNumber = phoneNumber.trim();
     String pattern = "^[1][1-9][0-9]{9}";
     return phoneNumber.matches(pattern);
   }









   public static Boolean isEmail(String email) {
     boolean tag = true;
     String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
     Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
     Matcher mat = pattern.matcher(email);
     if (!mat.find()) {
       tag = false;
     }
     return Boolean.valueOf(tag);
   }







   public static boolean isInteger(Object str) {
     if (isBlank(str))
       return false;
     try {
       Integer.parseInt(str.toString());
       return true;
     } catch (Exception e) {
       return false;
     }
   }






   public static String buildPath(Object... params) {
     String temp = "";
     for (Object o : params) {
       temp = temp + File.separator + o;
     }
     return temp;
   }











   public static String buildUrl(String url, String parm) {
     if (url.indexOf("?") > 0) {
       return url = url + "&" + parm;
     }
     return url = url + "?" + parm;
   }












   public static String buildUrl(String url, Map parms) {
     Iterator key = parms.keySet().iterator();
     String paramsStr = "";
     while (key.hasNext()) {
       Object temp = key.next();
       if (isBlank(parms.get(temp))) {
         continue;
       }
       if (paramsStr != "") {
         paramsStr = paramsStr + "&";
       }
       paramsStr = paramsStr + temp + "=" + parms.get(temp);
     }

     if (paramsStr != "") {
       if (url.indexOf("?") > 0) {
         return url = url + "&" + paramsStr;
       }
       return url = url + "?" + paramsStr;
     }

     return url;
   }








   public static String getFileFix(String filePath) {
     String temp = "";
     if (filePath != null) {
       temp = filePath.substring(filePath.indexOf("."), filePath.length());
     }
     return temp;
   }








   public static boolean isBlank(Object obj) { return (obj == null || obj.toString().trim().equals("") || obj.toString().length() < 0); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basi\\util\StringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */