 package com.baidu.ueditor;


 public class Encoder
 {
   public static String toUnicode(String input) {
     StringBuilder builder = new StringBuilder();
     char[] chars = input.toCharArray(); byte b; int i;
     char[] arrayOfChar;
     for (i = (arrayOfChar = chars).length, b = 0; b < i; ) { char ch = arrayOfChar[b];

       if (ch < 'Ā') {
         builder.append(ch);
       } else {
         builder.append("\\u" + Integer.toHexString(ch & Character.MAX_VALUE));
       }

       b++; }

     return builder.toString();
   }
 }


