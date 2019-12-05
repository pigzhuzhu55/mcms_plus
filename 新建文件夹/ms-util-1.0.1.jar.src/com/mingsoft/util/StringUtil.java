/*      */ package com.mingsoft.util;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URLDecoder;
/*      */ import java.net.URLEncoder;
/*      */ import java.security.MessageDigest;
/*      */ import java.sql.Date;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.TreeMap;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StringUtil
/*      */ {
/*   63 */   private static StringBuilder sb = new StringBuilder();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean checkEmail(String email) {
/*   72 */     String regex = "^[a-zA-Z][a-zA-Z0-9._-]*\\@\\w+(\\.)*\\w+\\.\\w+$";
/*   73 */     Pattern p = Pattern.compile(regex);
/*   74 */     Matcher matcher = p.matcher(email);
/*   75 */     return matcher.matches();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatHTMLIn(String html) {
/*   85 */     html = html.replaceAll("&", "&amp;");
/*   86 */     html = html.replaceAll("<", "&lt;");
/*   87 */     html = html.replaceAll(">", "&gt;");
/*   88 */     html = html.replaceAll("\"", "&quot;");
/*   89 */     return html;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatHTMLOut(String html) {
/*   99 */     html = html.replaceAll("&amp;", "&");
/*  100 */     html = html.replaceAll("&lt;", "<");
/*  101 */     html = html.replaceAll("&gt;", ">");
/*  102 */     html = html.replaceAll("&quot;", "\"");
/*  103 */     return html;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String subString(String str, int length) {
/*  114 */     if (isBlank(str))
/*  115 */       return ""; 
/*  116 */     if ((str.getBytes()).length <= length)
/*  117 */       return str; 
/*  118 */     char[] ch = null;
/*  119 */     if (str.length() >= length) {
/*  120 */       ch = str.substring(0, length).toCharArray();
/*      */     } else {
/*  122 */       ch = str.toCharArray();
/*  123 */     }  int readLen = 0;
/*  124 */     StringBuffer sb = new StringBuffer("");
/*  125 */     for (int i = 0; i < ch.length; i++) {
/*  126 */       String c = String.valueOf(ch[i]);
/*  127 */       readLen += (c.getBytes()).length;
/*  128 */       if (readLen > length)
/*  129 */         return sb.toString(); 
/*  130 */       sb.append(c);
/*      */     } 
/*  132 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean checkLength(String str, int minLength, int maxLength) {
/*  144 */     if (str != null) {
/*  145 */       int len = str.length();
/*  146 */       if (minLength == 0)
/*  147 */         return (len <= maxLength); 
/*  148 */       if (maxLength == 0) {
/*  149 */         return (len >= minLength);
/*      */       }
/*  151 */       return (len >= minLength && len <= maxLength);
/*      */     } 
/*  153 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String decodeStringByUTF8(String str) {
/*  163 */     if (isBlank(str))
/*  164 */       return ""; 
/*      */     try {
/*  166 */       return URLDecoder.decode(str, "utf-8");
/*  167 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */       
/*  169 */       return "";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeStringByUTF8(String str) {
/*  179 */     if (isBlank(str))
/*  180 */       return ""; 
/*      */     try {
/*  182 */       return URLEncoder.encode(str, "utf-8");
/*  183 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */       
/*  185 */       return "";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String isoToUTF8(String str) {
/*  195 */     if (isBlank(str))
/*  196 */       return ""; 
/*      */     try {
/*  198 */       return new String(str.getBytes("ISO-8859-1"), "UTF-8");
/*  199 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */       
/*  201 */       return "";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String utf8ToISO(String str) {
/*  211 */     if (isBlank(str))
/*  212 */       return ""; 
/*      */     try {
/*  214 */       return new String(str.getBytes("UTF-8"), "ISO-8859-1");
/*  215 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */       
/*  217 */       return "";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String utf8Togb2312(String str) {
/*  226 */     StringBuffer sb = new StringBuffer();
/*  227 */     for (int i = 0; i < str.length(); i++) {
/*  228 */       char c = str.charAt(i);
/*  229 */       switch (c) {
/*      */         case '+':
/*  231 */           sb.append(' ');
/*      */           break;
/*      */         case '%':
/*      */           try {
/*  235 */             sb.append((char)Integer.parseInt(str.substring(i + 1, i + 3), 16));
/*  236 */           } catch (NumberFormatException numberFormatException) {
/*  237 */             throw new IllegalArgumentException();
/*      */           } 
/*  239 */           i += 2;
/*      */           break;
/*      */         default:
/*  242 */           sb.append(c);
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/*  247 */     String result = sb.toString();
/*  248 */     String res = null;
/*      */     try {
/*  250 */       byte[] inputBytes = result.getBytes("8859_1");
/*  251 */       res = new String(inputBytes, "UTF-8");
/*  252 */     } catch (Exception exception) {}
/*      */     
/*  254 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFormatDateStr(Date date, String pattern) {
/*  265 */     SimpleDateFormat format = new SimpleDateFormat(pattern);
/*  266 */     return format.format(date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  276 */   public static boolean isBlank(String str) { return !(str != null && !str.trim().equals("") && str.length() >= 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  286 */   public static boolean isBlank(Object str) { return !(str != null && !str.toString().trim().equals("") && str.toString().length() >= 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  296 */   public static boolean isBlank(String[] args) { return (args == null || args.length == 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInteger(String str) {
/*  307 */     if (isBlank(str))
/*  308 */       return false; 
/*      */     try {
/*  310 */       Integer.parseInt(str);
/*  311 */       return true;
/*  312 */     } catch (Exception exception) {
/*  313 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInteger(Object str) {
/*  324 */     Object object = str;
/*  325 */     if (isBlank(str))
/*  326 */       return false; 
/*      */     try {
/*  328 */       Integer.parseInt((String)object);
/*  329 */       return true;
/*  330 */     } catch (Exception exception) {
/*  331 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String null2String(String str) {
/*  342 */     if (str == null || str.equals("") || str.trim().length() == 0) {
/*  343 */       return str = "";
/*      */     }
/*  345 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int string2Int(String str) {
/*  355 */     int valueInt = 0;
/*  356 */     if (!isBlank(str)) {
/*  357 */       valueInt = Integer.parseInt(str);
/*      */     }
/*  359 */     return valueInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String int2String(int comment) {
/*  369 */     String srt = "";
/*  370 */     srt = Integer.toString(comment);
/*  371 */     return srt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMaxZeroInteger(Object str) {
/*  381 */     if (isBlank(str))
/*  382 */       return false; 
/*      */     try {
/*  384 */       int temp = Integer.parseInt(str.toString());
/*  385 */       return (temp > 0);
/*  386 */     } catch (Exception exception) {
/*  387 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLong(String str) {
/*  398 */     if (isBlank(str))
/*  399 */       return false; 
/*      */     try {
/*  401 */       Long.parseLong(str);
/*  402 */       return true;
/*  403 */     } catch (Exception exception) {
/*  404 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLongs(String[] str) {
/*      */     try {
/*  416 */       for (int i = 0; i < str.length; i++)
/*  417 */         Long.parseLong(str[i]); 
/*  418 */       return true;
/*  419 */     } catch (Exception exception) {
/*  420 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIntegers(String[] str) {
/*      */     try {
/*  432 */       for (int i = 0; i < str.length; i++)
/*  433 */         Integer.parseInt(str[i]); 
/*  434 */       return true;
/*  435 */     } catch (Exception exception) {
/*  436 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDoubles(String[] str) {
/*      */     try {
/*  448 */       for (int i = 0; i < str.length; i++)
/*  449 */         Double.parseDouble(str[i]); 
/*  450 */       return true;
/*  451 */     } catch (Exception exception) {
/*  452 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String Md5(String plainText) {
/*  464 */     StringBuffer buf = new StringBuffer("");
/*      */     try {
/*  466 */       MessageDigest md = MessageDigest.getInstance("MD5");
/*  467 */       md.update(plainText.getBytes());
/*  468 */       byte[] b = md.digest();
/*  469 */       int i = 0;
/*  470 */       for (int offset = 0; offset < b.length; offset++) {
/*  471 */         i = b[offset];
/*  472 */         if (i < 0)
/*  473 */           i += 256; 
/*  474 */         if (i < 16)
/*  475 */           buf.append("0"); 
/*  476 */         buf.append(Integer.toHexString(i));
/*      */       } 
/*  478 */     } catch (Exception e) {
/*  479 */       e.printStackTrace();
/*      */     } 
/*  481 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String Md5(String plainText, String coding) {
/*  491 */     StringBuffer buf = new StringBuffer("");
/*      */     try {
/*  493 */       MessageDigest md = MessageDigest.getInstance("MD5");
/*  494 */       md.update(plainText.getBytes(coding));
/*  495 */       byte[] b = md.digest();
/*  496 */       int i = 0;
/*  497 */       for (int offset = 0; offset < b.length; offset++) {
/*  498 */         i = b[offset];
/*  499 */         if (i < 0)
/*  500 */           i += 256; 
/*  501 */         if (i < 16)
/*  502 */           buf.append("0"); 
/*  503 */         buf.append(Integer.toHexString(i));
/*      */       } 
/*  505 */     } catch (Exception e) {
/*  506 */       e.printStackTrace();
/*      */     } 
/*  508 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] stringsToLongs(String[] str) {
/*  518 */     long[] lon = new long[str.length];
/*  519 */     for (int i = 0; i < lon.length; i++)
/*  520 */       lon[i] = Long.parseLong(str[i]); 
/*  521 */     return lon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer[] stringsToIntegers(String[] str) {
/*  531 */     Integer[] array = new Integer[str.length];
/*  532 */     for (int i = 0; i < array.length; i++)
/*  533 */       array[i] = Integer.valueOf(Integer.parseInt(str[i])); 
/*  534 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] stringsToInts(String[] str) {
/*  544 */     int[] array = new int[str.length];
/*  545 */     for (int i = 0; i < array.length; i++)
/*  546 */       array[i] = Integer.parseInt(str[i]); 
/*  547 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] stringsToDoubles(String[] str) {
/*  558 */     double[] array = new double[str.length];
/*  559 */     for (int i = 0; i < array.length; i++)
/*  560 */       array[i] = Double.parseDouble(str[i]); 
/*  561 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] delLopStrings(String[] str) {
/*  573 */     ArrayList<String> list = new ArrayList();
/*  574 */     for (int i = 0; i < str.length; i++) {
/*  575 */       if (!list.contains(str[i]))
/*  576 */         list.add(str[i]); 
/*      */     } 
/*  578 */     String[] array = new String[list.size()];
/*  579 */     for (int i = 0; i < list.size(); i++) {
/*  580 */       array[i] = list.get(i);
/*      */     }
/*  582 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] stringsToBooleans(String[] str) {
/*  592 */     boolean[] array = new boolean[str.length];
/*  593 */     for (int i = 0; i < array.length; i++)
/*  594 */       array[i] = Boolean.parseBoolean(str[i]); 
/*  595 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTimestamp(String str) {
/*      */     try {
/*  607 */       Date.valueOf(str);
/*  608 */       return true;
/*  609 */     } catch (Exception exception) {
/*      */       
/*  611 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getPageStart(String pageNo) {
/*  621 */     int istart = 1;
/*  622 */     if (isBlank(pageNo)) {
/*  623 */       return istart;
/*      */     }
/*      */     try {
/*  626 */       istart = (Integer.parseInt(pageNo) < 0) ? istart : Integer.parseInt(pageNo);
/*  627 */     } catch (NumberFormatException numberFormatException) {}
/*      */     
/*  629 */     return istart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  638 */   public static String getDateSimpleStr() { return String.valueOf(System.currentTimeMillis()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Long[] StrToLong(String[] args) {
/*  648 */     if (args == null)
/*  649 */       return null; 
/*  650 */     Long[] _ref = new Long[args.length];
/*  651 */     for (int i = 0; i < args.length; i++) {
/*  652 */       _ref[i] = new Long(args[i]);
/*      */     }
/*  654 */     return _ref;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer[] StrToInteger(String[] args) {
/*  664 */     if (args == null)
/*  665 */       return null; 
/*  666 */     Integer[] _ref = new Integer[args.length];
/*  667 */     for (int i = 0; i < args.length; i++) {
/*  668 */       _ref[i] = new Integer(args[i]);
/*      */     }
/*  670 */     return _ref;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleDateStr(Date day, String fomStr) {
/*  681 */     SimpleDateFormat format = new SimpleDateFormat(fomStr);
/*  682 */     return format.format(day);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDateForStr(String str) {
/*  692 */     Date sqlDate = Date.valueOf(str);
/*  693 */     return sqlDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addDays(Date time, int day) {
/*  705 */     if (time == null)
/*  706 */       return null; 
/*  707 */     Calendar c = Calendar.getInstance();
/*  708 */     c.setTime(time);
/*  709 */     c.set(5, c.get(5) + day);
/*  710 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMonths(Date time, int month) {
/*  721 */     if (time == null)
/*  722 */       return null; 
/*  723 */     Calendar c = Calendar.getInstance();
/*  724 */     c.setTime(time);
/*  725 */     c.set(2, c.get(2) + month);
/*  726 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getIpStringFromBytes(byte[] ip) {
/*  736 */     sb.delete(0, sb.length());
/*  737 */     sb.append(ip[0] & 0xFF);
/*  738 */     sb.append('.');
/*  739 */     sb.append(ip[1] & 0xFF);
/*  740 */     sb.append('.');
/*  741 */     sb.append(ip[2] & 0xFF);
/*  742 */     sb.append('.');
/*  743 */     sb.append(ip[3] & 0xFF);
/*  744 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  755 */   public static boolean isIpEquals(byte[] ip1, byte[] ip2) { return (ip1[0] == ip2[0] && ip1[1] == ip2[1] && ip1[2] == ip2[2] && ip1[3] == ip2[3]); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getString(byte[] b, int offset, int len, String encoding) {
/*      */     try {
/*  773 */       return new String(b, offset, len, encoding);
/*  774 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  775 */       return new String(b, offset, len);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String stringToBinary(byte[] src) {
/*  786 */     StringBuffer sb = new StringBuffer();
/*  787 */     byte[][] des = new byte[src.length][16];
/*  788 */     for (int i = 0; i < src.length; i++) {
/*  789 */       for (int j = 0; j < 16; j++)
/*  790 */         des[i][j] = (byte)(src[i] >> j & 0x1); 
/*      */     } 
/*  792 */     for (int i = 0; i < src.length; i++) {
/*  793 */       for (int j = 0; j < 16; j++)
/*  794 */         sb.append(des[i][j]); 
/*      */     } 
/*  796 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String randomNumber(int len) {
/*  806 */     StringBuffer sb = new StringBuffer();
/*  807 */     Random random = new Random();
/*  808 */     for (int i = 0; i < len; i++) {
/*  809 */       sb.append(Math.abs(random.nextInt()) % 10);
/*      */     }
/*  811 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String timeForString() {
/*  820 */     Long l = Long.valueOf(System.currentTimeMillis());
/*  821 */     return String.valueOf(Math.abs(l.intValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getParString(String str) {
/*  831 */     if (isBlank(str))
/*  832 */       return ""; 
/*  833 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isChinese(char chChar) {
/*  843 */     Character.UnicodeBlock ub = Character.UnicodeBlock.of(chChar);
/*  844 */     if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
/*  845 */       return true;
/*      */     }
/*  847 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMobile(String phoneNumber) {
/*  857 */     phoneNumber = phoneNumber.trim();
/*  858 */     String pattern = "^[1][1-8][0-9]{9}";
/*  859 */     return phoneNumber.matches(pattern);
/*      */   }
/*      */   
/*      */   public static String formatResource(Object[] info, String require) {
/*  863 */     require = require.replaceAll("'", "\"");
/*  864 */     String result = MessageFormat.format(require, info);
/*  865 */     return result.replaceAll("\"", "'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getDaysBetween(Calendar beginDate, Calendar endDate) {
/*  876 */     if (beginDate.after(endDate)) {
/*  877 */       Calendar swap = beginDate;
/*  878 */       beginDate = endDate;
/*  879 */       endDate = swap;
/*      */     } 
/*  881 */     int days = endDate.get(6) - beginDate.get(6);
/*  882 */     int y2 = endDate.get(1);
/*  883 */     if (beginDate.get(1) != y2) {
/*  884 */       beginDate = (Calendar)beginDate.clone();
/*      */       do {
/*  886 */         days += beginDate.getActualMaximum(6);
/*  887 */         beginDate.add(1, 1);
/*  888 */       } while (beginDate.get(1) != y2);
/*      */     } 
/*  890 */     return days;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFileFix(String filePath) {
/*  901 */     String temp = "";
/*  902 */     if (filePath != null) {
/*  903 */       temp = filePath.substring(filePath.indexOf("."), filePath.length());
/*      */     }
/*  905 */     return temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String convertStreamToString(InputStream dataFlow) {
/*  917 */     BufferedReader reader = new BufferedReader(new InputStreamReader(dataFlow));
/*  918 */     StringBuilder sb = new StringBuilder();
/*  919 */     String line = null;
/*      */ 
/*      */     
/*      */     try {
/*  923 */       while ((line = reader.readLine()) != null) {
/*  924 */         sb.append(line);
/*      */       }
/*  926 */     } catch (IOException e) {
/*  927 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/*  930 */         dataFlow.close();
/*  931 */       } catch (IOException e) {
/*  932 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*  935 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String checkStr(String str) {
/*  946 */     String s = null;
/*  947 */     char[] cc = str.toCharArray();
/*  948 */     for (int i = 0; i < cc.length; i++) {
/*  949 */       boolean b = isValidChar(cc[i]);
/*  950 */       if (!b)
/*  951 */         cc[i] = ' '; 
/*      */     } 
/*  953 */     s = String.valueOf(cc);
/*  954 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isValidChar(char ch) {
/*  966 */     if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
/*  967 */       return true; 
/*  968 */     if ((ch >= '一' && ch <= '翿') || (ch >= '耀' && ch <= '锯'))
/*  969 */       return true; 
/*  970 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String removeRepeatStr(String content, String target) {
/*  985 */     StringBuffer sb = new StringBuffer(content);
/*  986 */     for (int i = 0; i < sb.length() - 1; i++) {
/*      */       
/*  988 */       if (sb.substring(i, i + target.length()).equals(target) && sb.substring(i, i + target.length()).equals(sb.substring(i + 1, i + target.length() + 1))) {
/*  989 */         sb.delete(i, i + target.length());
/*  990 */         if (i + target.length() + 1 > sb.length()) {
/*      */           break;
/*      */         }
/*  993 */         i--;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  998 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Boolean isEmail(String email) {
/* 1009 */     boolean tag = true;
/*      */     
/* 1011 */     Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
/* 1012 */     Matcher mat = pattern.matcher(email);
/* 1013 */     if (!mat.find()) {
/* 1014 */       tag = false;
/*      */     }
/* 1016 */     return Boolean.valueOf(tag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String buildUrl(String url, String parm) {
/* 1029 */     if (url.indexOf("?") > 0) {
/* 1030 */       return url = String.valueOf(url) + "&" + parm;
/*      */     }
/* 1032 */     return url = String.valueOf(url) + "?" + parm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String buildPath(Object... params) {
/* 1042 */     String temp = ""; byte b; int i; Object[] arrayOfObject;
/* 1043 */     for (i = (arrayOfObject = params).length, b = 0; b < i; ) { Object o = arrayOfObject[b];
/* 1044 */       temp = String.valueOf(temp) + File.separator + o; b++; }
/*      */     
/* 1046 */     return temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String buildUrl(String url, Map parms) {
/* 1060 */     Iterator key = parms.keySet().iterator();
/* 1061 */     String paramsStr = "";
/* 1062 */     while (key.hasNext()) {
/* 1063 */       Object temp = key.next();
/* 1064 */       if (isBlank(parms.get(temp))) {
/*      */         continue;
/*      */       }
/* 1067 */       if (paramsStr != "") {
/* 1068 */         paramsStr = String.valueOf(paramsStr) + "&";
/*      */       }
/* 1070 */       paramsStr = String.valueOf(paramsStr) + temp + "=" + parms.get(temp);
/*      */     } 
/*      */     
/* 1073 */     if (paramsStr != "") {
/* 1074 */       if (url.indexOf("?") > 0) {
/* 1075 */         return url = String.valueOf(url) + "&" + paramsStr;
/*      */       }
/* 1077 */       return url = String.valueOf(url) + "?" + paramsStr;
/*      */     } 
/*      */     
/* 1080 */     return url;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String javaProperty2DatabaseCloumn(String property) {
/* 1089 */     String[] ss = property.split("(?<!^)(?=[A-Z])");
/* 1090 */     StringBuffer sb = new StringBuffer();
/* 1091 */     for (int i = 0; i < ss.length; i++) {
/* 1092 */       sb.append(ss[i]);
/* 1093 */       if (i < ss.length - 1) {
/* 1094 */         sb.append("_");
/*      */       }
/*      */     } 
/* 1097 */     if (!isBlank(sb)) {
/* 1098 */       return sb.toString().toUpperCase();
/*      */     }
/* 1100 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, String> sortMapByKey(Map<String, String> map) {
/* 1111 */     if (map == null || map.isEmpty()) {
/* 1112 */       return null;
/*      */     }
/* 1114 */     Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
/* 1115 */     sortMap.putAll(map);
/* 1116 */     return sortMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, String> sortMapByValue(Map<String, String> map) {
/* 1125 */     if (map == null || map.isEmpty()) {
/* 1126 */       return null;
/*      */     }
/* 1128 */     Map<String, String> sortedMap = new LinkedHashMap<>();
/* 1129 */     List<Map.Entry<String, String>> entryList = new ArrayList<>(map.entrySet());
/* 1130 */     Collections.sort(entryList, new MapValueComparator());
/* 1131 */     Iterator<Map.Entry<String, String>> iter = entryList.iterator();
/* 1132 */     Map.Entry<String, String> tmpEntry = null;
/* 1133 */     while (iter.hasNext()) {
/* 1134 */       tmpEntry = iter.next();
/* 1135 */       sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
/*      */     } 
/* 1137 */     return sortedMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isExpressNo(String str) {
/* 1157 */     if (isBlank(str)) {
/* 1158 */       return false;
/*      */     }
/*      */     
/* 1161 */     if (str.length() == 13)
/* 1162 */       return true; 
/* 1163 */     if (str.length() == 12) {
/* 1164 */       return true;
/*      */     }
/* 1166 */     return true;
/*      */   }
/*      */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\StringUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */