 package net.mingsoft.mdiy.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;







 public enum FieldSearchEnum
   implements BaseEnum
 {
   NOT("0"),



   IS("1");
   private Object code;

   FieldSearchEnum(Object code) { this.code = code; }






   public int toInt() { return Integer.valueOf(this.code + "").intValue(); }
 }


