 package net.mingsoft.basic.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;









 public enum RoleDataEnum
   implements BaseEnum
 {
   ALL(1, "所有数据"),
   SELF(1, "仅本人数据");

   private int id;

   private String code;


   RoleDataEnum(int id, String code) {
     this.id = id;
     this.code = code;
   }












   public String toString() { return this.code; }





   public int toInt() { return this.id; }
 }


