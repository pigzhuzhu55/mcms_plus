 package net.mingsoft.basic.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;












 public enum LogEnum
   implements BaseEnum
 {
   MOBILE("移动端", 1),
   PC("PC端", 0);

   private String attr;

   private int id;


   LogEnum(String attr, int id) {
     this.attr = attr;
     this.id = id;
   }












   public String toString() { return this.attr; }





   public int toInt() { return this.id; }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\constant\e\LogEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */