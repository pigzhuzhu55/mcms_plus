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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\constant\e\ModelEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */