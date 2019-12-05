 package net.mingsoft.basic.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;












 public enum LogEnum
   implements BaseEnum
 {
   MOBILE("移动端", 1),
   PC("PC端", 0);

   private String attr;

   private int id;


   LogEnum(String attr, int id) {
     this.attr = attr;
     this.id = id;
   }












   public String toString() { return this.attr; }





   public int toInt() { return this.id; }
 }


