 package net.mingsoft.basic.util;

 import cn.hutool.core.util.ArrayUtil;
 import java.util.Arrays;







































 public class ArrysUtil
 {
   public static String sort(String str, String delimiter) {
     String[] articleTypeArrays = str.split(delimiter);
     Arrays.sort((Object[])articleTypeArrays);
     return ArrayUtil.join((Object[])articleTypeArrays, delimiter);
   }
 }


