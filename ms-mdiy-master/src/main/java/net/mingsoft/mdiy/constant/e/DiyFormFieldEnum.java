 package net.mingsoft.mdiy.constant.e;

 import java.util.HashMap;
 import java.util.Map;
 import net.mingsoft.base.constant.e.BaseEnum;








 public enum DiyFormFieldEnum
   implements BaseEnum
 {
   TEXT(1, "字符"),
   DATE(2, "日期"),




   TEXTAREA(3, "文本"),




   INT(4, "整型"),




   FLOAT(5, "小数");


   private Object code;

   private int id;


   DiyFormFieldEnum(int id, Object code) {
     this.code = code;
     this.id = id;
   }











   public int toInt() { return this.id; }






   public String toString() { return this.code.toString(); }



   public static Map toMap() {
     Map<String, String> map = new HashMap<>();
     for (DiyFormFieldEnum e : values()) {
       map.put(e.toInt() + "", e.toString());
     }
     return map;
   }

   public static DiyFormFieldEnum get(int id) {
     for (DiyFormFieldEnum e : values()) {
       if (e.toInt() == id) {
         return e;
       }
     }
     return null;
   }

   public static DiyFormFieldEnum get(String str) {
     for (DiyFormFieldEnum e : values()) {
       if (e.toString().equals(str)) {
         return e;
       }
     }
     return null;
   }
 }


