 package net.mingsoft.basic.constant;

 import net.mingsoft.base.constant.e.BaseEnum;























 public enum ModelCode
   implements BaseEnum
 {
   ADMIN_LOGIN("00000000"),



   ROLE("01010000"),




   CITY("10990000"),




   ROLE_MANAGER("01020000"),




   APP("02010000"),




   COLUMN("00990000"),




   SCHOOL("10980000");



   private String code;



   ModelCode(String code) { this.code = code; }











   public String toString() { return this.code; }








   public int toInt() { return Integer.parseInt(this.code); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\constant\ModelCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */