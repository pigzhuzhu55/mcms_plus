 package net.mingsoft.people.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;





















 public enum PeopleEnum
   implements BaseEnum
 {
   STATE_CHECK(Integer.valueOf(1)),



   STATE_NOT_CHECK(Integer.valueOf(0)),




   PHONE_CHECK(Integer.valueOf(1)),




   PHONE_NO_CHECK(Integer.valueOf(0)),



   MAIL_CHECK(Integer.valueOf(1)),



   MAIL_NO_CHECK(Integer.valueOf(0)),



   REGISTER_NAME(Integer.valueOf(1)),



   REGISTER_PHONE(Integer.valueOf(2)),



   REGISTER_EMAIL(Integer.valueOf(3));
   private Object code;

   PeopleEnum(Object code) { this.code = code; }






   public String toString() { return this.code.toString(); }



   public int toInt() { return Integer.parseInt(this.code.toString()); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\constant\e\PeopleEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */