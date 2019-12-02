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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\constant\e\GlobalModelCodelEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */