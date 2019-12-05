 package net.mingsoft.basic.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;









 public enum GlobalModelCodelEnum
   implements BaseEnum
 {
   CITY("10990000");



   private String code;



   GlobalModelCodelEnum(String code) { this.code = code; }












   public String toString() { return this.code; }





   public int toInt() { return 0; }
 }


