 package net.mingsoft.mdiy.constant.e;

 import java.util.HashMap;
 import java.util.Map;
 import net.mingsoft.base.constant.e.BaseEnum;








 public enum ContentModelFieldEnum
   implements BaseEnum
 {
   TEXT(1, "单行"), TEXTAREA(2, "多行"), HTML(3, "html"), INT(4, "整型"), FLOAT(5, "小数"), DATE(6, "日期"), IMAGE(7, "图片"),
   ATTACH(8, "附件"), OPTION(9, "下拉框"), RADIO(10, "单选"), CHECKBOX(11, "多选");


   private int id;

   private String text;


   ContentModelFieldEnum(int id, String text) {
     this.id = id;
     this.text = text;
   }











   public int toInt() { return this.id; }





   public String toString() { return this.text; }


   public static Map toMap() {
     Map<String, String> map = new HashMap<>();
     for (ContentModelFieldEnum e : values()) {
       map.put(e.toInt() + "", e.toString());
     }
     return map;
   }

   public static ContentModelFieldEnum get(int id) {
     for (ContentModelFieldEnum e : values()) {
       if (e.toInt() == id) {
         return e;
       }
     }
     return null;
   }

   public static ContentModelFieldEnum get(String str) {
     for (ContentModelFieldEnum e : values()) {
       if (e.toString().equals(str)) {
         return e;
       }
     }
     return null;
   }
 }


