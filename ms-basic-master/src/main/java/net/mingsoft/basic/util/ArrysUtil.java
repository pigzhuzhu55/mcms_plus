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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basi\\util\ArrysUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */