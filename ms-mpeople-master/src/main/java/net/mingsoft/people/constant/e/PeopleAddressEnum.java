 package net.mingsoft.people.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;






























 public enum PeopleAddressEnum
   implements BaseEnum
 {
   ADDRESS_DEFAULT(Integer.valueOf(0)),




   ADDRESS_NOT_DEFAULT(Integer.valueOf(1));
   private Object code;

   PeopleAddressEnum(Object code) { this.code = code; }






   public String toString() { return this.code.toString(); }



   public int toInt() { return Integer.parseInt(this.code.toString()); }
 }


