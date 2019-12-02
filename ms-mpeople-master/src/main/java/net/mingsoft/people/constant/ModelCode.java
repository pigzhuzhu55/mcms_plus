 package net.mingsoft.people.constant;

 import net.mingsoft.base.constant.e.BaseEnum;































 public enum ModelCode
   implements BaseEnum
 {
   PEOPLE("07000000"),




   PEOPLE_REGISTER("07010100"),




   PEOPLE_LOGIN("07010200"),




   PEOPLE_USER("07020100");



   private String code;




   ModelCode(String code) { this.code = code; }











   public String toString() { return this.code; }








   public int toInt() { return Integer.parseInt(this.code); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\constant\ModelCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */