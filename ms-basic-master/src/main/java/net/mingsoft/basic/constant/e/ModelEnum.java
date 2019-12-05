 package net.mingsoft.basic.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;
































 public enum ModelEnum
   implements BaseEnum
 {
   MENU(Integer.valueOf(0)),




   NOTMENU(Integer.valueOf(1));





   private Object code;






   ModelEnum(Object code) { this.code = code; }







   public int toInt() { return Integer.valueOf(this.code.toString()).intValue(); }
 }


